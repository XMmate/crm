package com.kakarote.core.feign.work.fallback;

import com.kakarote.core.common.Result;
import com.kakarote.core.feign.work.service.WorkService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WorkServiceFallback implements FallbackFactory<WorkService> {
    @Override
    public WorkService create(Throwable cause) {
        return new WorkService() {
            @Override
            public Result updateTaskJob() {
                log.error(cause.getMessage());
                return Result.ok();
            }

            @Override
            public Result<Boolean> initWorkData() {
                log.error(cause.getMessage());
                return Result.ok(false);
            }
        };
    }
}
