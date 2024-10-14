package com.liujiaming.authorization.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.liujiaming.authorization.common.AuthorizationCodeEnum;
import com.liujiaming.authorization.entity.AuthorizationUser;
import com.liujiaming.authorization.service.LoginService;
import com.liujiaming.core.common.Const;
import com.liujiaming.core.common.ParamAspect;
import com.liujiaming.core.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;


@RestController
@Api(tags = "用户登录相关接口")
@Slf4j
public class AuthController {

    @Autowired
    private LoginService loginService;

    /**
     * 判断用户是否有权限访问url,暴露给微服务网关去远程调用
     * @param url
     * @param method
     * @param request
     * @return
     */
    @RequestMapping(value = "/permission")
    @ParamAspect
    public Result permission(@RequestParam("url") String url, @RequestParam("method") String method, HttpServletRequest request) {
        String token = request.getHeader(Const.TOKEN_NAME);
        String proxyHost = request.getHeader("proxyHost");
        return Result.ok(true);
//        return loginService.permission(token, url, proxyHost);

    }

    /**
     * 登录方法，限流由sentinel处理
     */
    @PostMapping(value = "/login")
    @ApiOperation(tags = "用户登录", httpMethod = "POST", value = "/doLogin")
    public Result doLogin(@Valid @RequestBody AuthorizationUser user,HttpServletResponse response,HttpServletRequest request) {
        if (StrUtil.isNotEmpty(user.getUsername())){
            if (StrUtil.trimToNull(user.getUsername()) == null) {
                return Result.error(AuthorizationCodeEnum.AUTHORIZATION_USERNAME_REQUIRED);
            }
            if (StrUtil.trimToNull(user.getPassword()) == null && StrUtil.trimToNull(user.getSmscode()) == null) {
                return Result.error(AuthorizationCodeEnum.AUTHORIZATION_PASSWORD_REQUIRED);
            }
            return loginService.doLogin(user,response,request);
        }
        return Result.error(AuthorizationCodeEnum.AUTHORIZATION_USERNAME_REQUIRED);
    }

    /**
     * 用户注销 服务端删除缓存里面的用户信息，
     * 设置清空浏览器相关cookie
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/logout")
    @ApiOperation(tags = "用户注销", httpMethod = "GET", value = "/logout")
    @ParamAspect
    public Result logout(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader(Const.TOKEN_NAME);
        if (StrUtil.isNotEmpty(token)) {
            loginService.logout(token);
        }
        //清除cookie并设置域
        String serverName = StrUtil.isNotEmpty(request.getHeader("proxyHost")) ? request.getHeader("proxyHost") : request.getServerName();
        int index = serverName.indexOf(".");
        for (String user : Arrays.asList(Const.TOKEN_NAME, "User")) {   //["Admin-Token","User"]
            Cookie cookie = ServletUtil.getCookie(request, user);
            if (cookie != null) {
                cookie.setMaxAge(0);
                cookie.setValue(null);
                cookie.setPath("/");
                cookie.setDomain(index != -1 ? serverName.substring(index) : serverName);
                response.addCookie(cookie);
            }

        }

        return Result.ok();
    }


    //todo
    @GetMapping("/authtest")
    private Result auth(){
        return Result.ok("ok");
    }
}
