package com.xiaozhang.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaozhang.springboot.domain.SysUser;
import com.xiaozhang.springboot.mapper.SysUserMapper;
import com.xiaozhang.springboot.service.SysMenuService;
import com.xiaozhang.springboot.service.SysRoleService;
import com.xiaozhang.springboot.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    SysUserMapper sysUserMapper;

    @Autowired
    SysMenuService sysMenuService;

    @Autowired
    SysRoleService sysRoleService;

    /**
     * 通过id查早用户权限
     *
     * @param userId
     * @return 用户权限
     */
    @Override
    public String getUserAuthorityInfo(String userId) {
        String authority = "";
        // 获取角色编码
        log.info("---------------------------" + userId);
//        List<SysRole> roles = sysUserMapper.getRoleList(userId);

//        log.info("---------------------------" + roles);

//        if (roles.size() > 0) {
//            String roleCodes = roles.stream().map(r -> "ROLE_" + r.getRoleCode()).collect(Collectors.joining(","));
//            authority = roleCodes.concat(",");
//        }

//        // 获取菜单操作编码
//        List<Long> menuIds = sysUserMapper.getNavMenuIds(userId);
//        if (menuIds.size() > 0) {
//
//            List<SysMenu> menus = sysMenuService.listByIds(menuIds);
//            String menuPerms = menus.stream().map(m -> m.getMenuPerms()).collect(Collectors.joining(","));
//
//            authority = authority.concat(menuPerms);
//        }

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
}
