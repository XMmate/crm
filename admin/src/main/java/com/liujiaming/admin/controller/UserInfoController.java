package com.liujiaming.admin.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.liujiaming.core.common.Result;
@RestController
@RequestMapping("/user")
public class UserInfoController {
    @PostMapping("/info")
    private Result getUserInfo(){
        return Result.ok();
    }
}
