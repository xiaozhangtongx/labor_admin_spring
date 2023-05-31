package com.xiaozhang.springboot.mapper;

import com.xiaozhang.springboot.domain.SysDeptStandard;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

/**
 * <p>
 * 部门标准表 Mapper 接口
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-04-04
 */
@Mapper
public interface SysDeptStandardMapper extends BaseMapper<SysDeptStandard> {
    SysDeptStandard getStandard(@Param("deptId") String deptId);
    String getDept(@Param("userId") String userId);
}
