package com.liujiaming.hrm.service;

import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.hrm.constant.AbnormalChangeType;
import com.liujiaming.hrm.entity.PO.HrmEmployeeAbnormalChangeRecord;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 员工异常表动记录表（薪资列表统计需要） 服务类
 * </p>
 *
 * @author huangmingbo
 * @since 2024-06-08
 */
public interface IHrmEmployeeAbnormalChangeRecordService extends BaseService<HrmEmployeeAbnormalChangeRecord> {


    /**
     * 添加异动记录
     * @param employeeId 员工id
     * @param abnormalChangeType 异动类型
     */
    void addAbnormalChangeRecord(Integer employeeId, AbnormalChangeType abnormalChangeType, Date changeTime);

    /**
     * 按年和月查询员工异动记录
     * @return
     */
    List<HrmEmployeeAbnormalChangeRecord> queryListByDate(Date startTime, Date endTime, Collection<Integer> employeeIds, Integer type);

    /**
     * 按年和月查询员工异动记录
     * @return
     */
    List<HrmEmployeeAbnormalChangeRecord> queryListByDate1(int year, int monthValue, Integer type, Collection<Integer> employeeIds);
}
