package com.xiaozhang.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaozhang.springboot.domain.RoleInfoView;
import com.xiaozhang.springboot.domain.SysRole;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-03-19
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 通过角色名查询角色
     *
     * @param roleName
     * @return
     */
    SysRole getInfoByName(String roleName);

    /**
     * 通过id批量删除角色
     *
     * @param asList
     * @return
     */
    Boolean deleteByIds(List<String> asList);

    /**
     * 通过视图查询角色信息
     *
     * @param roleName
     * @return
     */
    List<RoleInfoView> selectRoleInfoViewList(String roleName);
}
