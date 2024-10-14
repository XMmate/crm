package com.liujiaming.authorization.service.impl;

import com.liujiaming.authorization.common.AuthException;
import com.liujiaming.authorization.common.AuthorizationCodeEnum;
import com.liujiaming.authorization.common.LoginLogUtil;
import com.liujiaming.authorization.entity.AdminUserStatusBO;
import com.liujiaming.authorization.entity.AuthorizationUser;
import com.liujiaming.authorization.entity.AuthorizationUserInfo;
import com.liujiaming.authorization.entity.VO.LoginVO;
import com.liujiaming.authorization.service.AdminUserService;
import com.liujiaming.authorization.service.LoginService;
import com.liujiaming.core.common.LoginType;
import com.liujiaming.core.common.Result;
import com.liujiaming.core.common.SystemCodeEnum;
import com.liujiaming.core.common.cache.AdminCacheKey;
import com.liujiaming.core.common.cache.CrmCacheKey;
import com.liujiaming.core.entity.UserInfo;
import com.liujiaming.core.exception.CrmException;
import com.liujiaming.core.feign.admin.entity.LoginLogEntity;
import com.liujiaming.core.feign.admin.service.LogService;
import com.liujiaming.core.redis.service.Redis;
import com.liujiaming.core.servlet.ApplicationContextHolder;
import com.liujiaming.core.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
@Service
public class LoginServiceImp implements LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private LoginLogUtil loginLogUtil;

    @Autowired
    @Qualifier("authorizationServerTokenServicesCustom")
    private AuthorizationServerTokenServices tokenServices;

    @Autowired
    private Redis redis;

    @Override
    public Result login(AuthorizationUser user, HttpServletResponse response, HttpServletRequest request,Authentication authentication) {
        OAuth2Request oAuth2Request = new OAuth2Request(null, "crm", authentication.getAuthorities(), true,
                Collections.singleton("all"), null, null, null, null);
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
        OAuth2AccessToken token = tokenServices.createAccessToken(oAuth2Authentication);
        LoginLogEntity logEntity = loginLogUtil.getLogEntity(request);
        UserInfo userInfo = user.getWkAdminUser().toUserInfo();
        logEntity.setUserId(userInfo.getUserId());
        logEntity.setRealname(userInfo.getRealname());
        if (userInfo.getStatus() == 0) {
            logEntity.setAuthResult(2);
            logEntity.setFailResult(AuthorizationCodeEnum.AUTHORIZATION_USER_DISABLE_ERROR.getMsg());
            ApplicationContextHolder.getBean(LogService.class).saveLoginLog(logEntity);
            throw new CrmException(AuthorizationCodeEnum.AUTHORIZATION_USER_DISABLE_ERROR);
        }
        userInfo.setRoles(adminUserService.queryUserRoleIds(userInfo.getUserId()).getData());
        //信息到redis 和cookie
        UserUtil.userToken(token.toString(), userInfo, user.getType());
        if (userInfo.getStatus() == 2) {
            adminUserService.activateUser(AdminUserStatusBO.builder().status(1).ids(Collections.singletonList(userInfo.getUserId())).build());
        }
        //保存日志
        ApplicationContextHolder.getBean(LogService.class).saveLoginLog(logEntity);
        return Result.ok(new LoginVO().setAdminToken(token.toString()));
    }

    @Override
    public Result doLogin(AuthorizationUser user, HttpServletResponse response, HttpServletRequest request) {
        LoginType loginType = LoginType.valueOf(user.getLoginType());
        //看是否在暴力破解密码
        if (loginType.equals(LoginType.PASSWORD) || loginType.equals(LoginType.SMS_CODE)){
            String key = AdminCacheKey.PASSWORD_ERROR_CACHE_KEY + user.getUsername().trim();
            Integer errorNum = redis.get(key);
            if (errorNum != null && errorNum > 2) {
                Integer second = Optional.ofNullable(redis.ttl(key)).orElse(0L).intValue();
                if (second > 0) {
                    String errorTimeDesc = this.getErrorTimeDesc(second);
                    return Result.error(AuthorizationCodeEnum.AUTHORIZATION_LOGIN_PASSWORD_TO_MANY_ERROR, "密码错误次数过多，请在"+errorTimeDesc+"后重试！");
                }
            }
        }
        //todo 其他登陆方式没写
        AbstractAuthenticationToken authenticationToken;
        try {

            switch (loginType){
                case PASSWORD:
                    authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername().trim(), user.getPassword().trim());
                    break;
                default:
                    authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername().trim(), user.getPassword().trim());
                    break;
            }
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            AuthorizationUserInfo userInfo = (AuthorizationUserInfo) authentication.getDetails();
            //密码错误
            if (userInfo.getAuthorizationUserList().size() == 0) {
                //todo 登陆日志写到数据库
                return this.handleLoginPassWordToManyError(user.getUsername().trim());
            }
            return login(userInfo.getAuthorizationUserList().get(0).setType(user.getType()), response, request, authentication );
        } catch (AuthException e) {
            return Result.error(e.getResultCode());
        } catch (BadCredentialsException e) {
            return this.handleLoginPassWordToManyError(user.getUsername().trim());
        }

    }

    @Override
    public Result permission(String authentication, String url, String method) {
        UserInfo userInfo = redis.get(authentication);
        if (userInfo == null) {
            throw new CrmException(SystemCodeEnum.SYSTEM_NOT_LOGIN);
        }
        Long userId = userInfo.getUserId();
        String key = userId.toString() ;
        List<String> noAuthMenuUrls = redis.get(key);
        if (noAuthMenuUrls == null) {
            noAuthMenuUrls = adminUserService.queryNoAuthMenu(userId).getData();
        }
        boolean permission = isHasPermission(noAuthMenuUrls, url);
        return permission ? Result.ok() : Result.noAuth();
    }

    @Override
    public Result logout(String token) {
        Object data = redis.get(token);
        if (data instanceof UserInfo) {
            UserInfo userInfo = (UserInfo) data;
            redis.del(token);
            redis.del(AdminCacheKey.USER_AUTH_CACHE_KET+userInfo.getUserId());
            redis.del(CrmCacheKey.CRM_AUTH_USER_CACHE_KEY +userInfo.getUserId());
        }
        return Result.ok();
    }


    private Result handleLoginPassWordToManyError(String userName){
        String key = AdminCacheKey.PASSWORD_ERROR_CACHE_KEY + userName;
        Integer errorNum = redis.get(key);
        if (errorNum == null) {
            redis.setex(AdminCacheKey.PASSWORD_ERROR_CACHE_KEY + userName, 60 * 3, 1);
        }else if(errorNum < 3){
            Integer defineTime = errorNum == 2 ? 60 * 2 : 60 * 3;
            redis.setex(AdminCacheKey.PASSWORD_ERROR_CACHE_KEY + userName, defineTime, errorNum + 1);
        }
        return Result.error(AuthorizationCodeEnum.AUTHORIZATION_LOGIN_NO_USER);
    }

    private String getErrorTimeDesc(Integer second){
        String errorTimeDesc;
        if (Arrays.asList(300, 240, 180, 120, 60).contains(second)) {
            errorTimeDesc = second / 60 + "分";
        } else if (second < 60) {
            errorTimeDesc = second + "秒";
        } else {
            errorTimeDesc = second / 60 + "分" + second % 60 + "秒";
        }
        return errorTimeDesc;
    }

    private boolean isHasPermission(List<String> noAuthMenuUrls, String url) {
        //用户信息丢失 | 错误
        if (noAuthMenuUrls == null) {
            return false;
        }
        //管理员
        if (noAuthMenuUrls.size() == 0) {
            return true;
        }
        //没有任何权限
        if (noAuthMenuUrls.size() == 1 && "/*/**".equals(noAuthMenuUrls.get(0))) {
            return false;
        }
        boolean permission = true;
        for (String noAuthMenuUrl : noAuthMenuUrls) {
            if (noAuthMenuUrl.contains("*")) {
                if (noAuthMenuUrl.contains(",")) {
                    boolean isNoAuth = false;
                    for (String noAuthUrl : noAuthMenuUrl.split(",")) {
                        if (url.startsWith(noAuthUrl.replace("*", ""))) {
                            isNoAuth = true;
                            break;
                        }
                    }
                    if (isNoAuth) {
                        permission = false;
                        break;
                    }
                } else {
                    if (url.startsWith(noAuthMenuUrl.replace("*", ""))) {
                        permission = false;
                        break;
                    }
                }
            } else {
                if (noAuthMenuUrl.contains(",")) {
                    if (Arrays.asList(noAuthMenuUrl.split(",")).contains(url)) {
                        permission = false;
                        break;
                    }
                } else {
                    if (noAuthMenuUrl.equals(url)) {
                        permission = false;
                        break;
                    }
                }
            }
        }
        return permission;
    }
}
