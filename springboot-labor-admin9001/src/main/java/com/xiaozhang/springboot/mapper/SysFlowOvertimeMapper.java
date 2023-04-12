package com.xiaozhang.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaozhang.springboot.domain.SysFlowOvertime;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 加班申请表 Mapper 接口
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-04-12
 */
@Mapper
public interface SysFlowOvertimeMapper extends BaseMapper<SysFlowOvertime> {

}
