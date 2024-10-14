package com.liujiaming.authorization.config.password;
import com.liujiaming.authorization.common.AuthException;
import com.liujiaming.authorization.common.AuthorizationCodeEnum;
import com.liujiaming.authorization.entity.AuthorizationUser;
import com.liujiaming.authorization.entity.AuthorizationUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
/**
 * @author z
 * 认证相关代码
 */
public class AuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        AuthorizationUser user;
        if (userDetails instanceof AuthorizationUser) {
            user = (AuthorizationUser) userDetails;
        } else {
            logger.debug("身份验证失败：未提供任何凭证");
            throw new AuthException(AuthorizationCodeEnum.AUTHORIZATION_LOGIN_ERR);
        }

        if (authentication.getCredentials() == null) {
            logger.debug("身份验证失败：未提供任何凭证");
            throw new AuthException(AuthorizationCodeEnum.AUTHORIZATION_LOGIN_ERR);
        }
        String presentedPassword = authentication.getCredentials().toString();
        AuthorizationUserInfo userDetailsInfo = new AuthorizationUserInfo();
        if (passwordEncoder.matches(presentedPassword, user.getWkAdminUser().toJSON())) {
            userDetailsInfo.addAuthorizationUser(user);
        }
        authentication.setDetails(userDetailsInfo);
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        try {
            UserDetails loadedUser = userDetailsService.loadUserByUsername(username);
            if (loadedUser == null) {
                throw new InternalAuthenticationServiceException("UserDetailsService 返回 null，这违反了接口契约");
            }
            return loadedUser;
        } catch (UsernameNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
        }
    }
}