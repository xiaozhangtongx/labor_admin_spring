package com.xiaozhang.springboot.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author: xiaozhangtx
 * @ClassName: MyAuthenticationProvider
 * @Description: TODO 实现一些逻辑认证
 * @date: 2023/2/28 9:40
 * @Version: 1.0
 */
@Component
@Slf4j
public class MyAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailServiceImpl userDetailsServiceImpl;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取用户输入的用户名和密码
        final String username = authentication.getName();
        final String password = authentication.getCredentials().toString();
        log.info(password + username);
        // 获取封装用户信息的对象
        AccountUser userDetails = userDetailsServiceImpl.loadUserByUsername(username);
        boolean flag = bCryptPasswordEncoder.matches(password, userDetails.getPassword());
        log.info(String.valueOf(flag));
        if (flag) {
            // 将权限信息也封装进去
            return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        }
        throw new AuthenticationException("用户名或密码不正确") {
        };
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
