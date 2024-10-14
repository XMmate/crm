package com.liujiaming.authorization.common;

import cn.hutool.core.util.StrUtil;
import com.liujiaming.authorization.entity.AuthorizationUser;
import com.liujiaming.core.common.Const;
import com.liujiaming.core.entity.UserInfo;
import com.liujiaming.core.redis.service.Redis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Nonnull;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author liujiaming
 * 负责在每个请求处理之前对请求进行认证或其他处理逻辑
 */
public class AuthenticationTokenFilter extends OncePerRequestFilter implements Ordered {

    @Autowired
    private Redis redis;


    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull FilterChain chain) throws ServletException, IOException {
        //获取header中的验证信息
        String token = request.getHeader(Const.TOKEN_NAME);
        logger.info(request.getRequestURI());
        response.setContentType(Const.DEFAULT_CONTENT_TYPE);
        if (StrUtil.isNotEmpty(token)) {
            Object user = redis.get(token);
            if(user instanceof UserInfo){
                AuthorizationUser authorizationUser = AuthorizationUser.toAuthorizationUser((UserInfo) user);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(authorizationUser, null, new ArrayList<>());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }


    //Ordered 接口来自 Spring Framework，允许通过实现 getOrder() 方法来定义过滤器在过滤器链中的顺序。值越小，优先级越高
    @Override
    public int getOrder() {
        return -1;
    }
}
