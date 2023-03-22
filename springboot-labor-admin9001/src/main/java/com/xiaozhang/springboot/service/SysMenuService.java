package com.xiaozhang.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaozhang.springboot.domain.SysMenu;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-03-19
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 获取当前用户的菜单
     *
     * @param id
     * @return
     */
    List<SysMenu> getCurrentUserNavList(String id);

    /**
     * 将list转化为tree
     *
     * @return
     */
    List<SysMenu> list2tree();

    /**
     * 删除菜单
     *
     * @param id
     * @return
     */
    Boolean deleteById(String id);
}
