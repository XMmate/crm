package com.kakarote.gateway.service.fallback;

import com.kakarote.gateway.service.AuthService;
import com.kakarote.core.common.Result;
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