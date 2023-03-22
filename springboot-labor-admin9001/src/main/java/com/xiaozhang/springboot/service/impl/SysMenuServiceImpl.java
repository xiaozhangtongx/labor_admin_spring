package com.xiaozhang.springboot.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaozhang.springboot.domain.SysMenu;
import com.xiaozhang.springboot.mapper.SysMenuMapper;
import com.xiaozhang.springboot.mapper.SysUserMapper;
import com.xiaozhang.springboot.service.SysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
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

    @Autowired(required = false)
    SysMenuMapper sysMenuMapper;


    @Override
    public List<SysMenu> getCurrentUserNavList(String userId) {

        List<String> navMenuIds = sysUserMapper.getNavMenuIds(userId);
        List<SysMenu> menus = listByIds(navMenuIds);
        List<SysMenu> menuTree = buildTreeMenu(menus);

        return menuTree;
    }

    @Override
    public List<SysMenu> list2tree() {

        List<SysMenu> sysMenus = this.list(new QueryWrapper<SysMenu>().orderByAsc("order_num"));

        return buildTreeMenu(sysMenus);
    }

    @Override
    public Boolean deleteById(String id) {

        Integer lines = sysMenuMapper.deleteById(id);

        return lines != 0;
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
                    if (ObjectUtil.isNull(menu.getChildren())) {
                        menu.setChildren(Arrays.asList(e));
                    } else {
                        menu.getChildren().add(e);
                    }
                }
            }

            if (menu.getParentId().equals("0")) {
                finalMenus.add(menu);
            }
        }

        return finalMenus;
    }
}
