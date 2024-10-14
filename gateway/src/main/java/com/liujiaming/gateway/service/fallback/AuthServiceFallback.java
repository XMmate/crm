package com.liujiaming.gateway.service.fallback;

import com.liujiaming.gateway.service.AuthService;
import com.liujiaming.core.common.Result;
import org.springframework.stereotype.Component;

@Component
 public    class AuthServiceFallback implements AuthService {

        /**
         * 判断是否拥有权限访问
         *
         * @param authentication 用户权限标识
         * @param url            url
         * @param method         方法
         * @return Result
         */
        @Override
        public Result hasPermission(String authentication, String url, String method) {
            return Result.noAuth();
        }
    }