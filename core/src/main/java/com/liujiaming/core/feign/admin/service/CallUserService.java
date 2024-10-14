package com.liujiaming.core.feign.admin.service;

import com.liujiaming.core.common.Result;
import com.liujiaming.core.feign.admin.entity.CallUser;
import com.liujiaming.core.feign.admin.fallback.CallUserServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "admin",contextId = "call",fallbackFactory = CallUserServiceFallback.class)
public interface CallUserService {

    /**
    * 员工坐席授权
    * @date 2024/8/28 14:19
    * @param callUser
    * @return
    **/
    @PostMapping("/adminUserHisTable/authorize")
    Result<Boolean> authorize(@RequestBody CallUser callUser);


    /**
     * 判断用户是否为坐席
     * @date 2024/8/28 14:19
     * @return
     **/
    @PostMapping("/adminUserHisTable/checkAuth")
    Result<Integer> checkAuth();

}
