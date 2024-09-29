package com.kakarote.core.feign.crm.service;

import com.kakarote.core.common.Result;
import com.kakarote.core.feign.crm.fallback.CrmAnalysisServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author liujiaming
 * @date 2024/9/16
 */
@FeignClient(name = "crm",contextId = "analysis",fallbackFactory = CrmAnalysisServiceFallback.class)
public interface CrmAnalysisService {

    /**
     * 初始化数据
     * @date 2024/9/16 13:45
     * @return
     **/
    @PostMapping("/crmAnalysis/initCrmData")
    Result<Boolean> initCrmData();

}
