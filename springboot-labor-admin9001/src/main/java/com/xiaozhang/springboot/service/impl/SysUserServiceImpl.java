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

    /**
     * 通过id查找用户权限
     *
     * @param userId
     * @return 用户权限
     */
    @Override
    public String getUserAuthorityInfo(String userId) {
        SysUser sysUser = sysUserMapper.selectById(userId);

        //  ROLE_admin,ROLE_normal,sys:user:list,....
        String authority = "";

        // 获取角色编码
        List<SysRole> roles = getUserRoles(sysUser.getId());

        if (roles.size() > 0) {
            String roleCodes = roles.stream().map(r -> "ROLE_" + r.getRoleCode()).collect(Collectors.joining(","));
            authority = roleCodes.concat(",");
        }

        return authority;
    }

    /**
     * 通过手机号码查找用户
     *
     * @param phoneNum
     * @return 用户信息
     */
    @Override
    public SysUser getByPhoneNum(String phoneNum) {
        return getOne(new QueryWrapper<SysUser>().eq("phone_num", phoneNum));
    }

    /**
     * 获取用户信息
     *
     * @param phoneNum
     * @return
     */
    @Override
    public SysUser getInfoByPhoneNum(String phoneNum) {
        SysUser sysUser = getByPhoneNum(phoneNum);
        if (sysUser != null) {
            List<SysRole> roleList = getUserRoles(sysUser.getId());
            sysUser.setRoles(roleList);
        }
        return sysUser;
    }

    /**
     * 获取用户角色
     *
     * @param userId
     * @return
     */
    @Override
    public List<SysRole> getUserRoles(String userId) {
        return sysUserMapper.getRoleList(userId);
    }

    /**
     * 清除用户权限信息
     *
     * @param phoneNum
     */
    @Override
    public void clearUserAuthorityInfo(String phoneNum) {

    }

    /**
     * 批量逻辑删除用户
     *
     * @param idList
     */
    @Override
    public void deleteByIds(List<String> idList) {
        sysUserMapper.deleteBatchIds(idList);
    }
}
