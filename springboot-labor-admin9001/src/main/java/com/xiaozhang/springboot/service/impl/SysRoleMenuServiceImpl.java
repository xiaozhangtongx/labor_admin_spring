package com.xiaozhang.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaozhang.springboot.domain.SysCheck;
import com.xiaozhang.springboot.domain.SysMenu;
import com.xiaozhang.springboot.domain.SysRole;
import com.xiaozhang.springboot.domain.SysRoleMenu;
import com.xiaozhang.springboot.mapper.SysMenuMapper;
import com.xiaozhang.springboot.mapper.SysRoleMenuMapper;
import com.xiaozhang.springboot.service.SysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-03-19
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

    @Autowired(required = false)
    SysMenuMapper sysMenuMapper;

    @Override
    public List<SysMenu> getMenuList(String roleId) {

        List<SysMenu> menus = sysMenuMapper.getMenuList(roleId);

        return menus;
    }

    @Override
    public boolean updateMenu(String roleId, String[] menuIds) {

        List<SysRoleMenu> sysRoleMenus = new ArrayList<SysRoleMenu>();

        Arrays.stream(menuIds).forEach(menuId -> {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setMenuId(menuId);
            sysRoleMenu.setRoleId(roleId);

            sysRoleMenus.add(sysRoleMenu);
        });

        // 先删除原来的记录，再保存新的
        remove(new QueryWrapper<SysRoleMenu>().eq("role_id", roleId));

        return saveBatch(sysRoleMenus);
    }
}
