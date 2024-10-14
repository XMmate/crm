package com.liujiaming.job.service.oa;

import com.liujiaming.core.common.cache.OaCacheKey;
import com.liujiaming.core.feign.oa.service.OaService;
import com.liujiaming.core.redis.service.Redis;
import com.liujiaming.core.utils.UserUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class EventNoticeJob {

    @Autowired
    private OaService oaService;

    @Autowired
    private Redis redis;
    /**
     * 定时器日程提醒
     */
    @XxlJob("EventNoticeJob")
    public ReturnT<String> EventNoticeJobHandler(String param) {
        try {
            oaService.eventNoticeCron();
        } finally {
            redis.del(OaCacheKey.EVENT_NOTICE_JOB_KEY);
            UserUtil.removeUser();
        }
        return ReturnT.SUCCESS;
    }
}
