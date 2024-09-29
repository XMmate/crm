package com.kakarote.core.feign.crm.fallback;

import com.kakarote.core.common.Result;
import com.kakarote.core.feign.crm.service.CrmAnalysisService;
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
