package com.liujiaming.crm.common.log;
import com.liujiaming.core.common.log.Content;
import com.liujiaming.core.servlet.ApplicationContextHolder;
import com.liujiaming.crm.constant.CrmTypeEnum;
import com.liujiaming.crm.entity.PO.CrmActivity;

public class CrmActivityLog {
    private SysLogUtil sysLogUtil = ApplicationContextHolder.getBean(SysLogUtil.class);


    public Content addCrmActivityRecord(CrmActivity crmActivity) {
        CrmTypeEnum crmTypeEnum = CrmTypeEnum.parse(crmActivity.getActivityType());
        return new Content(crmTypeEnum.getRemarks(),crmTypeEnum.getRemarks(),"新建了跟进记录");
    }
}
