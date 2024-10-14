package com.liujiaming.core.feign.email.fallback;

import com.liujiaming.core.common.Result;
import com.liujiaming.core.feign.email.service.EmailService;
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
