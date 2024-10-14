package com.liujiaming.hrm.common.log;

import com.liujiaming.core.common.log.BehaviorEnum;
import com.liujiaming.core.common.log.Content;
import com.liujiaming.core.servlet.ApplicationContextHolder;
import com.liujiaming.hrm.entity.PO.HrmSalarySlipTemplate;
import com.liujiaming.hrm.service.IHrmSalarySlipTemplateService;
import org.springframework.web.bind.annotation.PathVariable;

public class HrmSalarySlipTemplateLog {

    private IHrmSalarySlipTemplateService salarySlipTemplateService = ApplicationContextHolder.getBean(IHrmSalarySlipTemplateService.class);

    public Content deleteSlipTemplate(@PathVariable Integer templateId){
        HrmSalarySlipTemplate template = salarySlipTemplateService.getById(templateId);
        return new Content(template.getTemplateName(),"删除了"+template.getTemplateName(), BehaviorEnum.DELETE);
    }
}
