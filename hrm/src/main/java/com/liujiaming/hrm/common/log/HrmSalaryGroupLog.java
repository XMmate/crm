package com.liujiaming.hrm.common.log;

import com.liujiaming.core.common.log.BehaviorEnum;
import com.liujiaming.core.common.log.Content;
import com.liujiaming.core.servlet.ApplicationContextHolder;
import com.liujiaming.hrm.entity.PO.HrmSalaryGroup;
import com.liujiaming.hrm.service.IHrmSalaryGroupService;
import org.springframework.web.bind.annotation.PathVariable;

public class HrmSalaryGroupLog {

    private IHrmSalaryGroupService salaryGroupService = ApplicationContextHolder.getBean(IHrmSalaryGroupService.class);

    public Content deleteSalaryGroup(@PathVariable Integer groupId){
        HrmSalaryGroup salaryGroup = salaryGroupService.getById(groupId);
        return new Content(salaryGroup.getGroupName(),"删除了薪资组:"+salaryGroup.getGroupName(), BehaviorEnum.DELETE);
    }
}
