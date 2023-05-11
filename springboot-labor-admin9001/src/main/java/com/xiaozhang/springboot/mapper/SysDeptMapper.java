package com.xiaozhang.springboot.mapper;

import com.xiaozhang.springboot.domain.SysDept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 部门表 Mapper 接口
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-05-11
 */
@Mapper
public interface SysDeptMapper extends BaseMapper<SysDept> {

}
