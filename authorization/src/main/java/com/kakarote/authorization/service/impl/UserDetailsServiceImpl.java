package com.kakarote.authorization.service.impl;

import com.kakarote.authorization.entity.AuthorizationUser;
import com.kakarote.authorization.service.AdminUserService;
import com.kakarote.core.common.Result;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 实现UserDetailsService接口 重写loadUserByUsername方法
 */
@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AdminUserService adminUserService;

    @Override
    @SuppressWarnings("unchecked")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Result result = adminUserService.findByUsername(username);
        if (result.hasSuccess()) {
            return new AuthorizationUser().setUserInfoList((List<Object>) result.getData());

        }
        throw new UsernameNotFoundException(null);
    }
}
