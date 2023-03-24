package com.xiaozhang.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaozhang.springboot.domain.SysMenu;
import com.xiaozhang.springboot.domain.SysRoleMenu;
import com.xiaozhang.springboot.mapper.SysMenuMapper;
import com.xiaozhang.springboot.mapper.SysRoleMenuMapper;
import com.xiaozhang.springboot.service.SysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
