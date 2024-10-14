package com.liujiaming.core.feign.admin.fallback;

import com.liujiaming.core.common.Result;
import com.liujiaming.core.common.log.SysLogEntity;
import com.liujiaming.core.feign.admin.entity.LoginLogEntity;
import com.liujiaming.core.feign.admin.service.LogService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LogServiceFallback implements FallbackFactory<LogService> {
    private static final Logger logger = LoggerFactory.getLogger( LogServiceFallback.class);
    @Override
    public LogService create(Throwable cause) {

        return new LogService() {
            @Override
            public Result saveSysLog(SysLogEntity sysLogEntity) {
                logger.error(cause.getMessage());
                return Result.ok();
            }

            @Override
            public Result saveLoginLog(LoginLogEntity loginLogEntity) {
                logger.error(cause.getMessage());
                return Result.ok();
            }
        };
    }
}
