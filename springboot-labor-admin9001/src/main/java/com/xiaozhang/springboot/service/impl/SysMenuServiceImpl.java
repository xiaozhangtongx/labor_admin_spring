package com.xiaozhang.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaozhang.springboot.domain.SysMenu;
import com.xiaozhang.springboot.mapper.SysMenuMapper;
import com.xiaozhang.springboot.mapper.SysUserMapper;
import com.xiaozhang.springboot.service.SysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-03-19
 */
@Service
@Slf4j
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired(required = false)
    SysUserMapper sysUserMapper;

    /**
     * 获取菜单信息
     *
     * @param userId
     * @return List<SysMenu>
     */
    @Override
    public List<SysMenu> getCurrentUserNavList(String userId) {
        List<String> navMenuIds = sysUserMapper.getNavMenuIds(userId);
        List<SysMenu> menus = listByIds(navMenuIds);
        List<SysMenu> menuTree = buildTreeMenu(menus);

        return menuTree;
    }


    /**
     * list转tree
     *
     * @param menus
     * @return
     */
    private List<SysMenu> buildTreeMenu(List<SysMenu> menus) {
        List<SysMenu> finalMenus = new ArrayList<>();

        for (SysMenu menu : menus) {
            for (SysMenu e : menus) {
                if (menu.getId().equals(e.getParentId())) {
                    menu.getChildren().add(e);
                }
            }

            if (menu.getParentId().equals("0")) {
                finalMenus.add(menu);
            }
        }

        return finalMenus;
    }
}
