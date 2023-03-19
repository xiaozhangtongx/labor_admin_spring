package com.xiaozhang.springboot.security;

import com.xiaozhang.springboot.domain.SysUser;
import com.xiaozhang.springboot.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    SysUserService sysUserService;

    @Override
    public AccountUser loadUserByUsername(String username) throws UsernameNotFoundException {

        SysUser sysUser = sysUserService.getByUserName(username);

        if (sysUser == null) {
            throw new UsernameNotFoundException("用户名或密码不正确");
        }

        return new AccountUser(sysUser.getId(), sysUser.getUsername(), sysUser.getPassword(), getUserAuthority(sysUser.getId()));
    }

    /**
     * 获取用户权限信息（角色、菜单权限）
     *
     * @param userId
     * @return
     */
    public List<GrantedAuthority> getUserAuthority(String userId) {

        // user,admin
        String authority = sysUserService.getUserAuthorityInfo(userId);
        return AuthorityUtils.commaSeparatedStringToAuthorityList(authority);
    }

}
