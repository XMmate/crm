package com.liujiaming.crm.common.log;

import com.liujiaming.core.common.log.BehaviorEnum;
import com.liujiaming.core.common.log.Content;
import com.liujiaming.crm.constant.CrmTypeEnum;
import com.liujiaming.crm.entity.BO.CrmFieldBO;

public class CrmFieldLog {

    public Content saveField(CrmFieldBO crmFieldBO) {
        CrmTypeEnum crmTypeEnum = CrmTypeEnum.parse(crmFieldBO.getLabel());
        return new Content(crmTypeEnum.getRemarks(),"保存了自定义字段:"+crmTypeEnum.getRemarks(), BehaviorEnum.SAVE);
    }
}
