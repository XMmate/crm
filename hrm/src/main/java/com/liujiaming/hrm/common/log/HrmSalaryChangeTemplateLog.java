package com.liujiaming.hrm.common.log;

import com.liujiaming.core.common.log.BehaviorEnum;
import com.liujiaming.core.common.log.Content;
import com.liujiaming.core.servlet.ApplicationContextHolder;
import com.liujiaming.hrm.entity.PO.HrmSalaryChangeTemplate;
import com.liujiaming.hrm.service.IHrmSalaryChangeTemplateService;

public class HrmSalaryChangeTemplateLog {

    private IHrmSalaryChangeTemplateService salaryChangeTemplateService = ApplicationContextHolder.getBean(IHrmSalaryChangeTemplateService.class);

    public Content deleteChangeTemplate(Integer id){
        HrmSalaryChangeTemplate template = salaryChangeTemplateService.getById(id);
        salaryChangeTemplateService.deleteChangeTemplate(id);
        return new Content(template.getTemplateName(),"删除了模板"+template.getTemplateName(), BehaviorEnum.DELETE);
    }
}
