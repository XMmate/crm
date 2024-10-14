package com.liujiaming.core.feign.crm.fallback;

import com.liujiaming.core.common.Result;
import com.liujiaming.core.feign.crm.service.CrmAnalysisService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CrmAnalysisServiceFallback implements FallbackFactory<CrmAnalysisService> {
    @Override
    public CrmAnalysisService create(Throwable cause) {
        return new CrmAnalysisService() {
            @Override
            public Result<Boolean> initCrmData() {
                log.error(cause.getMessage());
                return Result.ok(false);
            }
        };
    }
}
