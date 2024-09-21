package com.kakarote.admin.controller;


import com.kakarote.core.common.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kakarote.core.common.Result;
@RestController
@RequestMapping("/user")
public class UserInfoController {
    @PostMapping("/info")
    private Result getUserInfo(){
        return Result.ok();
    }
}
