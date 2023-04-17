package com.xiaozhang.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaozhang.springboot.domain.RoleInfoView;
import com.xiaozhang.springboot.domain.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-03-19
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 通过视图查询角色信息
     *
     * @param roleName
     * @return
     */
    List<RoleInfoView> selectUserInfoViewList(String roleName);
}
