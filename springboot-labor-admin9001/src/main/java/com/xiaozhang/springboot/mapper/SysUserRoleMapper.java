package com.xiaozhang.springboot.mapper;

import com.xiaozhang.springboot.domain.SysUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-02-27
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {
    @Select("select role_id from sys_user_role where user_id = #{userId}")
    List<String> selectById(String userId);
}
