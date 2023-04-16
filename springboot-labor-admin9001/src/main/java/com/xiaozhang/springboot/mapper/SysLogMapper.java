package com.xiaozhang.springboot.mapper;

import com.xiaozhang.springboot.domain.SysLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 系统日志表 Mapper 接口
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-04-16
 */
@Mapper
public interface SysLogMapper extends BaseMapper<SysLog> {

}
