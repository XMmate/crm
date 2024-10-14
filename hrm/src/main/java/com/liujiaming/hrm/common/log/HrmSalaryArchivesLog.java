package com.liujiaming.hrm.common.log;

import com.liujiaming.core.common.log.BehaviorEnum;
import com.liujiaming.core.common.log.Content;
import com.liujiaming.core.servlet.ApplicationContextHolder;
import com.liujiaming.hrm.entity.BO.SetChangeSalaryRecordBO;
import com.liujiaming.hrm.entity.BO.SetFixSalaryRecordBO;
import com.liujiaming.hrm.entity.PO.HrmEmployee;
import com.liujiaming.hrm.service.IHrmEmployeeService;

public class HrmSalaryArchivesLog {

    private IHrmEmployeeService employeeService = ApplicationContextHolder.getBean(IHrmEmployeeService.class);

    public Content setFixSalaryRecord(SetFixSalaryRecordBO setFixSalaryRecordBO) {
        HrmEmployee employee = employeeService.getById(setFixSalaryRecordBO.getEmployeeId());
        return new Content(employee.getEmployeeName(),"给员工"+employee.getEmployeeName()+"定薪", BehaviorEnum.SAVE);
    }

    public Content setChangeSalaryRecord(SetChangeSalaryRecordBO setChangeSalaryRecordBO) {
        HrmEmployee employee = employeeService.getById(setChangeSalaryRecordBO.getEmployeeId());
        return new Content(employee.getEmployeeName(),"给员工"+employee.getEmployeeName()+"调薪", BehaviorEnum.SAVE);
    }
}
