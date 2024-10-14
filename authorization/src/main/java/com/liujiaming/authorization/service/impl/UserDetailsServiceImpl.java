package com.liujiaming.authorization.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liujiaming.authorization.entity.AuthorizationUser;
import com.liujiaming.authorization.entity.PO.WkAdminUser;
import com.liujiaming.authorization.mapper.WkAdminAuthMapper;
import com.liujiaming.authorization.mapper.WkAdminUserMapper;
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
     private WkAdminUserMapper wkAdminUserMapper;  //用户表

    @Autowired
    private WkAdminAuthMapper wkAdminAuthMapper;






    @Override
    @SuppressWarnings("unchecked")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<WkAdminUser> queryWrapper=new LambdaQueryWrapper();
        queryWrapper.eq(WkAdminUser::getUsername,username);
        WkAdminUser wkAdminUser = wkAdminUserMapper.selectOne(queryWrapper);
        Long userId = wkAdminUser.getUserId();
        List<String> list = wkAdminAuthMapper.queryAuthNamesByUserId(userId);
        if (list.size()==0){
            list.add("dev");
        }
        return new AuthorizationUser(wkAdminUser,list);
    }
}