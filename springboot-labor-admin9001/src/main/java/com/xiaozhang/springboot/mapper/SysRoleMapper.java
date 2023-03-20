package com.xiaozhang.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaozhang.springboot.domain.SysRole;
import org.apache.ibatis.annotations.Mapper;

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

}
