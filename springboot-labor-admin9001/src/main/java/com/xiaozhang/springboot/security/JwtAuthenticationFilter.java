package com.xiaozhang.springboot.security;

import cn.hutool.core.util.StrUtil;
import com.xiaozhang.springboot.domain.SysUser;
import com.xiaozhang.springboot.service.SysUserService;
import com.xiaozhang.springboot.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserDetailServiceImpl userDetailService;

    @Autowired
    SysUserService sysUserService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String jwt = request.getHeader(jwtUtils.getHeader());
        if (StrUtil.isBlankOrUndefined(jwt)) {
            chain.doFilter(request, response);
            return;
        }

        Claims claim = jwtUtils.getClaimByToken(jwt);
        if (claim == null) {
            throw new JwtException("token 异常");
        }
        if (jwtUtils.isTokenExpired(claim)) {
            throw new JwtException("token已过期");
        }

        String phoneNum = claim.getSubject();
        // 获取用户的权限等信息

        SysUser sysUser = sysUserService.getByPhoneNum(phoneNum);
        UsernamePasswordAuthenticationToken token
                = new UsernamePasswordAuthenticationToken(phoneNum, null, userDetailService.getUserAuthority(sysUser.getId()));

        SecurityContextHolder.getContext().setAuthentication(token);

        chain.doFilter(request, response);
    }
}
