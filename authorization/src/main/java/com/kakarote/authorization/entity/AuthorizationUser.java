package com.kakarote.authorization.entity;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.kakarote.authorization.entity.PO.WkAdminUser;
import com.kakarote.core.entity.UserInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liujiaming
 * UserDetails是一个接口，我们要实现它才能内返回 UserDetails对象
 */

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "权限认证对象")
public class AuthorizationUser extends UserInfo implements UserDetails {
   private WkAdminUser wkAdminUser;
    /**
     * 短信验证码
     */
    @ApiModelProperty("短信验证码")
    private String smscode;

    @ApiModelProperty("用户密码")
    private String password;

    @ApiModelProperty("salt")
    private String salt;

    /**
     * 登录类型 1 密码登录 2 验证码登录 3 企业微信登录 4 企业微信扫码登录
     */
    @ApiModelProperty(value = "登录类型", allowableValues = "1,2,3,4")
    private Integer loginType = 1;

    @ApiModelProperty(value = "类型 1 pc 2 ", allowableValues = "1,2")
    private Integer type = 1;

    private List<UserInfo> userInfoList = new ArrayList<>();
    //存储权限信息
    private List<String> permissions;
   public AuthorizationUser(WkAdminUser wkAdminUser, List<String> permissions){
       this.wkAdminUser =wkAdminUser;
       this.permissions = permissions;

   }
    @JSONField(serialize = false)
    private List<GrantedAuthority> authorities;

    public AuthorizationUser setUserInfoList(List<Object> objList) {
        objList.forEach(obj -> {
            this.userInfoList.add(BeanUtil.copyProperties(obj, AuthorizationUser.class));
        });
        return this;
    }


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

    public static AuthorizationUser toAuthorizationUser(UserInfo systemUser) {
        return BeanUtil.copyProperties(systemUser, AuthorizationUser.class);
    }

    public UserInfo toUserInfo() {
        return BeanUtil.copyProperties(this, UserInfo.class);
    }

    public String toJSON() {
        return JSON.toJSONString(this);
    }
}