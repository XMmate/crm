package com.kakarote.auth.service.Impl;

import com.alibaba.fastjson.annotation.JSONField;
import com.kakarote.auth.entity.PO.WkAdminUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserLogin implements UserDetails {
    private WkAdminUser wkAdminUser;

    private List<String> permissions;

    UserLogin(WkAdminUser wkAdminUser,List<String> permissions){
        this.wkAdminUser=wkAdminUser;
        this.permissions = permissions;
    }
    @JSONField(serialize = false)
    private List<GrantedAuthority> authorities;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(authorities!=null){
            return authorities;
        }
        authorities = permissions.stream().
                map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return authorities;
    }

    @Override
    public String getPassword() {
        return wkAdminUser.getPassword();
    }

    @Override
    public String getUsername() {
        return wkAdminUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
