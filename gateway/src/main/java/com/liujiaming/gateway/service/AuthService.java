package com.liujiaming.gateway.service;

import com.liujiaming.core.common.Const;
import com.liujiaming.core.common.Result;
import com.liujiaming.gateway.service.fallback.AuthServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 远程调用鉴权接口
 *
 * @author liujiaming
 */
@FeignClient(name = "authorization", fallback = AuthServiceFallback.class)
public interface AuthService {
    /**
     * 判断是否拥有权限访问
     *
     * @param authentication 用户权限标识
     * @param url            url
     * @param method         方法
     * @return Result
     */
    @RequestMapping(value = "/permission")
    Result hasPermission(@RequestHeader(Const.TOKEN_NAME) String authentication, @RequestParam("url") String url, @RequestParam("method") String method);


}
