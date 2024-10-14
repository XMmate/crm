package com.liujiaming.job.service.crm;

import com.liujiaming.core.common.Const;
import com.liujiaming.core.common.Result;
import com.liujiaming.core.common.cache.CrmCacheKey;
import com.liujiaming.core.entity.UserInfo;
import com.liujiaming.core.feign.admin.service.AdminService;
import com.liujiaming.core.feign.crm.service.CrmService;
import com.liujiaming.core.redis.service.Redis;
import com.liujiaming.core.utils.UserUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 客户定时放入公海
 */
@Component
@Slf4j
public class CrmCustomerJob {

    @Autowired
    private CrmService crmService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private Redis redis;

    @XxlJob("CrmCustomerJob")
    public ReturnT<String> crmCustomerJobHandler(String param) {

        try {
            Long userId = UserUtil.getSuperUser();
            UserInfo userInfo = UserUtil.setUser(userId);
            redis.setex(CrmCacheKey.CRM_CUSTOMER_JOB_CACHE_KEY, Const.MAX_USER_EXIST_TIME, userInfo);
            Result result = crmService.putInInternational();
            crmService.updateReceivablesPlan();
            if (!result.hasSuccess()) {
                ReturnT<String> fail = ReturnT.FAIL;
                fail.setMsg(result.getMsg());
                return fail;
            }
        } finally {
            redis.del(CrmCacheKey.CRM_CUSTOMER_JOB_CACHE_KEY);
            UserUtil.removeUser();
        }
        XxlJobLogger.log("客户定时放入公海执行完成");
        return ReturnT.SUCCESS;
    }
}
