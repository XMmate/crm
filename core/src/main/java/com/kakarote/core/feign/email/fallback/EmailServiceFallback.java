package com.kakarote.core.feign.email.fallback;

import com.kakarote.core.common.Result;
import com.kakarote.core.feign.email.EmailService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailServiceFallback implements FallbackFactory<EmailService> {
    @Override
    public EmailService create(Throwable cause) {
        return new EmailService() {
            @Override
            public Result<Integer> getEmailId(Long userId) {
                log.error(cause.getMessage());
                return Result.ok();
            }
        };
    }
}
