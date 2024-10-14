package com.liujiaming.crm.common.log;

import com.liujiaming.core.common.log.BehaviorEnum;
import com.liujiaming.core.common.log.Content;
import com.liujiaming.core.servlet.ApplicationContextHolder;
import com.liujiaming.crm.entity.PO.CrmPrintTemplate;
import com.liujiaming.crm.service.ICrmPrintTemplateService;

public class CrmPrintTemplateLog {


    private ICrmPrintTemplateService printTemplateService = ApplicationContextHolder.getBean(ICrmPrintTemplateService.class);

    public Content deletePrintTemplate(Integer templateId) {
        CrmPrintTemplate template = printTemplateService.getById(templateId);
        return new Content(template.getTemplateName(),"删除了打印模板:"+template.getTemplateName(), BehaviorEnum.DELETE);
    }

    public Content copyTemplate(Integer templateId) {
        CrmPrintTemplate template = printTemplateService.getById(templateId);
        return new Content(template.getTemplateName(),"复制了打印模板:"+template.getTemplateName(), BehaviorEnum.COPY);
    }
}
