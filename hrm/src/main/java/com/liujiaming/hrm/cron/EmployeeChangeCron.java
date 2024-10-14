package com.liujiaming.hrm.cron;

import cn.hutool.core.collection.CollUtil;
import com.liujiaming.hrm.constant.EmployeeEntryStatus;
import com.liujiaming.hrm.constant.EmployeeStatusEnum;
import com.liujiaming.hrm.constant.HrmActionBehaviorEnum;
import com.liujiaming.hrm.entity.PO.HrmEmployee;
import com.liujiaming.hrm.entity.PO.HrmEmployeeChangeRecord;
import com.liujiaming.hrm.entity.PO.HrmEmployeeQuitInfo;
import com.liujiaming.hrm.service.IHrmEmployeeChangeRecordService;
import com.liujiaming.hrm.service.IHrmEmployeeQuitInfoService;
import com.liujiaming.hrm.service.IHrmEmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 员工 变动 计划
 */
@Component
@Slf4j
public class EmployeeChangeCron {

    @Autowired
    private IHrmEmployeeChangeRecordService changeRecordService;

    @Autowired
    private IHrmEmployeeService employeeService;

    @Autowired
    private IHrmEmployeeQuitInfoService quitInfoService;

    /**
     * 调岗晋升转正
     * 1.扫描员工更变表
     * 2.扫描员工表，查询判断转正日期
     * 2.把更变信息更新到员工表
     *
     */
    public void employeeChangeRecords() {
        List<HrmEmployee> employeeList = new ArrayList<>();
        List<HrmEmployeeChangeRecord> hrmEmployeeChangeRecords = changeRecordService.lambdaQuery()
                .apply("to_days(effect_time) = to_days(now())")
                .groupBy(HrmEmployeeChangeRecord::getEmployeeId)
                .having("max(create_time)").list();
        hrmEmployeeChangeRecords.forEach(changeRecord -> {
            //把数据添加到员工表
            employeeList.add(employeeChangeRecord(changeRecord));
        });
        List<HrmEmployee> list = employeeService.lambdaQuery()
                .ne(HrmEmployee::getStatus, EmployeeStatusEnum.OFFICIAL.getValue())
                .apply("to_days(become_time) = to_days(now())")
                .list();
        //这样的操作会直接改变原来列表中的元素
        list.forEach(employee -> {
            employee.setBecomeTime(new Date());
            employee.setStatus(EmployeeStatusEnum.OFFICIAL.getValue());
            employee.setUpdateTime(new Date());
        });
        employeeList.addAll(list);
        if (CollUtil.isNotEmpty(employeeList)){
            //更新记录到员工表
            employeeService.saveOrUpdateBatch(employeeList);
        }
    }

    /**
     * 员工离职变更状态
     *
     */
    public void employeeQuit() {
        List<HrmEmployeeQuitInfo> quitInfoList = quitInfoService.lambdaQuery().select(HrmEmployeeQuitInfo::getEmployeeId)
                .apply("to_days(plan_quit_time) = to_days(now())").list();
        List<HrmEmployee> employeeList = quitInfoList.stream().map(quitInfo -> {
            HrmEmployee employee = new HrmEmployee();
            employee.setEmployeeId(quitInfo.getEmployeeId());
            employee.setEntryStatus(EmployeeEntryStatus.ALREADY_LEAVE.getValue());
            return employee;
        }).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(employeeList)){
            employeeService.saveOrUpdateBatch(employeeList);
        }
    }

    /*
    * 员工变动记录
    * */

    public static HrmEmployee employeeChangeRecord(HrmEmployeeChangeRecord changeRecord) {
        Integer changeType = changeRecord.getChangeType();
        HrmEmployee employee = new HrmEmployee();
        employee.setEmployeeId(changeRecord.getEmployeeId());
        employee.setDeptId(changeRecord.getNewDept());
        employee.setPost(changeRecord.getNewPost());
        employee.setPostLevel(changeRecord.getNewPostLevel());
        if (changeType.equals(HrmActionBehaviorEnum.CHANGE_POST.getValue())
                || changeType.equals(HrmActionBehaviorEnum.PROMOTED.getValue())
                || changeType.equals(HrmActionBehaviorEnum.DEGRADE.getValue())
                || changeType.equals(HrmActionBehaviorEnum.CHANGE_FULL_TIME_EMPLOYEE.getValue())) {
            employee.setWorkAddress(changeRecord.getNewWorkAddress());
            if (changeType.equals(HrmActionBehaviorEnum.CHANGE_FULL_TIME_EMPLOYEE.getValue())) {
                Integer probation = changeRecord.getProbation();
                employee.setProbation(probation);
                if (probation == 0) {
                    employee.setBecomeTime(new Date());
                    employee.setStatus(EmployeeStatusEnum.OFFICIAL.getValue());
                } else {
                    employee.setStatus(EmployeeStatusEnum.TRY_OUT.getValue());
                }
            }
        } else if (changeType.equals(HrmActionBehaviorEnum.BECOME.getValue())) {
            employee.setBecomeTime(new Date());
            employee.setStatus(EmployeeStatusEnum.OFFICIAL.getValue());
        }
        employee.setUpdateTime(new Date());
        return employee;
    }
}
