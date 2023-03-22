package com.xiaozhang.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaozhang.springboot.domain.SysRole;
import com.xiaozhang.springboot.domain.SysUser;
import com.xiaozhang.springboot.mapper.SysUserMapper;
import com.xiaozhang.springboot.service.SysMenuService;
import com.xiaozhang.springboot.service.SysRoleService;
import com.xiaozhang.springboot.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-03-19
 */
@Service
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired(required = false)
    SysUserMapper sysUserMapper;

    @Autowired
    SysMenuService sysMenuService;

    @Autowired
    SysRoleService sysRoleService;

    @Override
    public String getUserAuthorityInfo(String userId) {
        SysUser sysUser = sysUserMapper.selectById(userId);

        //  ROLE_admin,ROLE_normal,sys:user:list,....
        String authority = "";

        List<SysRole> roles = getUserRoles(sysUser.getId());

        if (roles.size() > 0) {
            String roleCodes = roles.stream().map(r -> "ROLE_" + r.getRoleCode()).collect(Collectors.joining(","));
            authority = roleCodes.concat(",");
        }

        return authority;
    }


    @Override
    public SysUser getByPhoneNum(String phoneNum) {
        return getOne(new QueryWrapper<SysUser>().eq("phone_num", phoneNum));
    }

    @Override
    public SysUser getInfoByPhoneNum(String phoneNum) {
        SysUser sysUser = getByPhoneNum(phoneNum);
        if (sysUser != null) {
            List<SysRole> roleList = getUserRoles(sysUser.getId());
            sysUser.setRoles(roleList);
        }
        return sysUser;
    }

    @Override
    public List<SysRole> getUserRoles(String userId) {
        return sysUserMapper.getRoleList(userId);
    }

    @Override
    public void clearUserAuthorityInfo(String phoneNum) {

    }

    @Override
    public Boolean deleteByIds(List<String> idList) {
        Integer lines = sysUserMapper.deleteBatchIds(idList);
        return lines != 0;
    }
}
