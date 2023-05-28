package com.xiaozhang.springboot.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaozhang.springboot.domain.*;
import com.xiaozhang.springboot.mapper.SysUserMapper;
import com.xiaozhang.springboot.service.*;
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

    @Autowired
    SysUserTeamService sysUserTeamService;

    @Autowired
    SysTeamService sysTeamService;

    @Autowired
    SysDeptService sysDeptService;

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

    @Override
    public SysUser getInfoById(String id) {

        SysUser sysUser = getById(id);
        Assert.notNull(sysUser, "找不到该用户");
        sysUser.setPassword("");
        List<SysRole> roles = getUserRoles(id);
        sysUser.setRoles(roles);

        return sysUser;
    }

    @Override
    public SysDept getDeptInfoById(String userId) {

        // 首先通过id查询用户所在的小组
        SysUserTeam userTeam = sysUserTeamService.getOne(new QueryWrapper<SysUserTeam>().eq("user_id", userId));
        Assert.notNull(userTeam, "你暂时不在工作小组中");

        // 通过小组查部门
        SysTeam teamInfo = sysTeamService.getById(userTeam.getTeamId());
        Assert.notNull(teamInfo, "你暂时不在工作小组中");

        return sysDeptService.getById(teamInfo.getDeptId());
    }
}
