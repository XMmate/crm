package com.liujiaming.crm.common.log;

import cn.hutool.core.bean.BeanUtil;
import com.liujiaming.core.common.log.Content;
import com.liujiaming.core.servlet.ApplicationContextHolder;
import com.liujiaming.crm.constant.CrmTypeEnum;
import com.liujiaming.crm.entity.PO.CrmMarketing;
import com.liujiaming.crm.service.ICrmMarketingService;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

public class CrmMarketingLog {
    private SysLogUtil sysLogUtil = ApplicationContextHolder.getBean(SysLogUtil.class);

    private ICrmMarketingService crmMarketingService = ApplicationContextHolder.getBean(ICrmMarketingService.class);

    public Content update(CrmMarketing crmMarketing) {
        return sysLogUtil.updateRecord(BeanUtil.beanToMap(crmMarketingService.getById(crmMarketing.getMarketingId())), BeanUtil.beanToMap(crmMarketing), CrmTypeEnum.MARKETING,crmMarketing.getMarketingName());
    }

    public List<Content> updateStatus(@RequestParam("marketingIds") String marketingIds, @RequestParam("status") Integer status) {
        List<Content> contentList = new ArrayList<>();
        for (String id : marketingIds.split(",")) {
            CrmMarketing marketing = crmMarketingService.getById(id);
            String detail;
            if (status == 1) {
                detail = "将活动：" + marketing.getMarketingName() + "启用";
            } else {
                detail = "将活动：" + marketing.getMarketingName() + "停用";
            }
            contentList.add(new Content(marketing.getMarketingName(),detail));
        }
        return contentList;
    }
}
