package com.kakarote.core.feign.email;

import com.kakarote.core.common.Result;
import com.kakarote.core.feign.email.fallback.EmailServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "email", contextId = "email",fallbackFactory = EmailServiceFallback.class)
public interface EmailService {

    /**
     * 获取邮箱ID
     * @param userId 用户ID
     * @return 邮箱ID
     */
    @PostMapping("/emailAccount/queryAccountId")
    public Result<Integer> getEmailId(@RequestParam("userId") Long userId);


}
