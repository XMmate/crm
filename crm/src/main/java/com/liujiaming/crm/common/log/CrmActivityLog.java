package com.liujiaming.crm.common.log;
import com.liujiaming.core.common.log.Content;
import com.liujiaming.core.servlet.ApplicationContextHolder;
import com.liujiaming.crm.constant.CrmEnum;
import com.liujiaming.crm.entity.PO.CrmActivity;

public class CrmActivityLog {
    private SysLogUtil sysLogUtil = ApplicationContextHolder.getBean(SysLogUtil.class);


    public Content addCrmActivityRecord(CrmActivity crmActivity) {
        CrmEnum crmEnum = CrmEnum.parse(crmActivity.getActivityType());
        return new Content(crmEnum.getRemarks(),crmEnum.getRemarks(),"新建了跟进记录");
    }
}
