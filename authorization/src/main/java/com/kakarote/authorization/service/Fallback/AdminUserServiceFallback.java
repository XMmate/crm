package com.kakarote.authorization.service.Fallback;

import com.kakarote.authorization.entity.AdminUserStatusBO;
import com.kakarote.authorization.service.AdminUserService;
import com.kakarote.core.common.Result;
import com.kakarote.core.common.SystemCodeEnum;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * AdminUserService熔断的处理逻辑
 */
@Controller
@Slf4j
public class AdminUserServiceFallback implements FallbackFactory<AdminUserService> {
    @Override
    public AdminUserService create(Throwable cause) {
        log.error(cause.getMessage());
        return new AdminUserService() {
            @Override
            public Result findByUsername(String username) {
                return Result.error(SystemCodeEnum.SYSTEM_NO_FOUND);
            }

            @Override
            public Result<List<Integer>> queryUserRoleIds(Long userId) {
                return Result.ok(new ArrayList<>());
            }

            @Override
            public Result setUserStatus(AdminUserStatusBO adminUserStatusBO) {
                return Result.ok();
            }

            @Override
            public Result activateUser(AdminUserStatusBO adminUserStatusBO) {
                return Result.ok();
            }

            @Override
            public Result<List<String>> queryNoAuthMenu(Long userId) {
                return Result.error(SystemCodeEnum.SYSTEM_NO_FOUND);
            }
        };
    }
}
