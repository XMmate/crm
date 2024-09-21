package com.kakarote.core.feign.admin.fallback;

import com.kakarote.core.common.Result;
import com.kakarote.core.feign.admin.entity.CallUser;
import com.kakarote.core.feign.admin.service.CallUserService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallUserServiceFallback implements FallbackFactory<CallUserService> {
    @Override
    public CallUserService create(Throwable cause) {
        return new CallUserService() {
            @Override
            public Result<Boolean> authorize(CallUser callUser) {
                log.error(cause.getMessage());
                return Result.ok();
            }

            @Override
            public Result<Integer> checkAuth() {
                log.error(cause.getMessage());
                return Result.ok();
            }
        };
    }
}
