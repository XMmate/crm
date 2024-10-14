package com.liujiaming.core.feign.admin.service;

import com.liujiaming.core.common.Result;
import com.liujiaming.core.common.log.SysLogEntity;
import com.liujiaming.core.feign.admin.entity.LoginLogEntity;
import com.liujiaming.core.feign.admin.fallback.LogServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author hmb
 * 远程调用admin服务
 */
@FeignClient(name = "admin",contextId = "log",fallbackFactory = LogServiceFallback.class)
@Component
public interface LogService {

    @PostMapping("/adminSysLog/saveSysLog")
    Result saveSysLog(@RequestBody SysLogEntity sysLogEntity);


    /**
     * 保存登陆日志
     * @param loginLogEntity
     * @return
     */
    @PostMapping("/adminSysLog/saveLoginLog")
    Result saveLoginLog(@RequestBody LoginLogEntity loginLogEntity);
}
