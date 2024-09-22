package com.kakarote.auth.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.kakarote.auth.entity.PO.WkAdminUser;
import com.kakarote.auth.mapper.WkAdminUserMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * 实现UserDetailsService接口 重写loadUserByUsername方法
 */
@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
@Autowired
     private WkAdminUserMapper wkAdminUserMapper;

    @Override
    @SuppressWarnings("unchecked")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<WkAdminUser> queryWrapper=new LambdaQueryWrapper();
        queryWrapper.eq(WkAdminUser::getUsername,username);
        WkAdminUser wkAdminUser = wkAdminUserMapper.selectOne(queryWrapper);
        ArrayList<String> list = new ArrayList<>();
        list.add("dev");
        System.out.println(wkAdminUser);
        return new UserLogin(wkAdminUser,list);
    }
}