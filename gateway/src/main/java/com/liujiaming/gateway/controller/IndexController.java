package com.liujiaming.gateway.controller;

import com.liujiaming.core.common.Const;
import com.liujiaming.core.common.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author Administrator
 */
@RestController
public class IndexController {


//    @RequestMapping("/")
//    public Mono<Void> index(ServerHttpResponse response) {
//        return Mono.fromRunnable(() -> {
//            response.setStatusCode(HttpStatus.FOUND);
//            response.getHeaders().setLocation(URI.create("./index.html"));
//        });
//    }



    //版本号
    @RequestMapping("/version")
    public Result<String> version() {
        return Result.ok(Const.PROJECT_VERSION);
    }
}
