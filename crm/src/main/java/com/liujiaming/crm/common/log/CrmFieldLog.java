package com.liujiaming.crm.common.log;

import com.liujiaming.core.common.log.BehaviorEnum;
import com.liujiaming.core.common.log.Content;
import com.liujiaming.crm.constant.CrmEnum;
import com.liujiaming.crm.entity.BO.CrmFieldBO;

public class CrmFieldLog {

    public Content saveField(CrmFieldBO crmFieldBO) {
        CrmEnum crmEnum = CrmEnum.parse(crmFieldBO.getLabel());
        return new Content(crmEnum.getRemarks(),"保存了自定义字段:"+crmEnum.getRemarks(), BehaviorEnum.SAVE);
    }
}
