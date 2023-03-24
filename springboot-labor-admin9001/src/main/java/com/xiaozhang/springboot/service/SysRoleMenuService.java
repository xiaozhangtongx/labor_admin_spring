package com.xiaozhang.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaozhang.springboot.domain.SysMenu;
import com.xiaozhang.springboot.domain.SysRoleMenu;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-03-19
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {

    /**
     * 查询角色下的所有menu
     *
     * @param id
     * @return
     */
    List<SysMenu> getMenuList(String id);
}
