package com.xiaozhang.springboot.mapper;

import com.xiaozhang.springboot.domain.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-03-19
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

}
