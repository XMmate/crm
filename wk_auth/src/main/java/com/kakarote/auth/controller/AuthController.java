package com.kakarote.auth.controller;



import com.kakarote.core.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;




@RestController
@Slf4j
public class AuthController {

    @GetMapping("/login-success")
    private Result auth(){
        return Result.ok("ok");
    }
}
