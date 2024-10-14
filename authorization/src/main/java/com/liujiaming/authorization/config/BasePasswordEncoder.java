package com.liujiaming.authorization.config;


import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.liujiaming.authorization.common.AuthPasswordUtil;
import com.liujiaming.authorization.entity.PO.WkAdminUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author z
 * 自定义密码校验
 */
@Slf4j
public class BasePasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence password) {
        return password.toString();
    }

    @Override
    public boolean matches(CharSequence password, String userJson) {
        if (StrUtil.isEmpty(userJson)) {
            return false;
        }
        try {
            WkAdminUser user = JSON.parseObject(userJson, WkAdminUser.class);
            return AuthPasswordUtil.verify(user.getUsername() + password.toString(), user.getSalt(), user.getPassword());
        } catch (Exception e) {
            log.warn("JSON转换错误，str:{}", userJson);
        }
        return false;
    }

    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        return false;
    }
}
