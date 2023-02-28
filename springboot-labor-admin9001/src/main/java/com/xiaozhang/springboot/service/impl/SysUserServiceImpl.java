package com.xiaozhang.springboot.service.impl;

import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaozhang.springboot.domain.SysRole;
import com.xiaozhang.springboot.domain.SysUser;
import com.xiaozhang.springboot.domain.SysUserRole;
import com.xiaozhang.springboot.mapper.SysUserMapper;
import com.xiaozhang.springboot.mapper.SysUserRoleMapper;
import com.xiaozhang.springboot.service.SysRoleService;
import com.xiaozhang.springboot.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-02-27
 */
@Service
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Autowired
    SysRoleService sysRoleService;

    SysUserRoleMapper sysUserRoleMapper;

    @Override
    public String getUserAuthorityInfo(String userId) {
        String authority = "";

        List<SysRole> roles = sysRoleService.list(new QueryWrapper<SysRole>()
                .inSql("role_id", "select role_id from sys_user_role where user_id = " + "\"" + userId + "\""));

        if (roles.size() > 0) {
            String roleCodes = roles.stream().map(r -> "ROLE_" + r.getRoleCode()).collect(Collectors.joining(","));
            log.info(roleCodes);
            authority = roleCodes.concat(",");
        }
        return authority;
    }

    @Override
    public SysUser getByUserName(String username) {
        return getOne(new QueryWrapper<SysUser>().eq("username", username));
    }
}
