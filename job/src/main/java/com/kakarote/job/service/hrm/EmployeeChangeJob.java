package com.kakarote.job.service.hrm;

import com.kakarote.core.feign.admin.service.AdminService;
import com.kakarote.core.feign.hrm.service.HrmService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 人事调动
 * 员工变动记录  远程调用hrmd服务 ，扫描员工调用表，把需要更新的内容更新到员工表
 */
@Component
public class EmployeeChangeJob {

    @Autowired
    private HrmService hrmService;

    @Autowired
    private AdminService adminService;

    @XxlJob("EmployeeChangeJob")
    public ReturnT<String> employeeChangeJobHandler(String param) {
        hrmService.employeeChangeRecords();
        return ReturnT.SUCCESS;
    }
}
