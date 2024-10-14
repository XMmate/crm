package com.liujiaming.core.feign.admin.fallback;

import com.liujiaming.core.common.Result;
import com.liujiaming.core.feign.admin.entity.AdminMessage;
import com.liujiaming.core.feign.admin.entity.AdminMessageBO;
import com.liujiaming.core.feign.admin.service.AdminMessageService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AdminMessageServiceFallback implements FallbackFactory<AdminMessageService> {
    @Override
    public AdminMessageService create(Throwable cause) {
        return new AdminMessageService() {
            @Override
            public Result<AdminMessage> save(AdminMessage adminMessage) {
                log.error(cause.getMessage());
                return Result.ok();
            }

            @Override
            public Result<AdminMessage> update(AdminMessage adminMessage) {
                log.error(cause.getMessage());
                return Result.ok();
            }

            @Override
            public Result<AdminMessage> getById(Long messageId) {
                log.error(cause.getMessage());
                return Result.ok();
            }

            @Override
            public Result<AdminMessage> sendMessage(AdminMessageBO adminMessageBO) {
                log.error(cause.getMessage());
                return Result.ok();
            }

            @Override
            public Result deleteEventMessage(Integer eventId) {
                log.error(cause.getMessage());
                return Result.ok();
            }

            @Override
            public Result deleteByLabel(Integer label) {
                log.error(cause.getMessage());
                return Result.ok();
            }
        };
    }
}
