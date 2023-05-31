package com.xiaozhang.springboot.mapper;

import cn.hutool.core.date.DateTime;
import com.xiaozhang.springboot.domain.SysStudyData;
import com.xiaozhang.springboot.domain.SysUserStudy;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户学习情况表 Mapper 接口
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-05-28
 */
@Mapper
public interface SysUserStudyMapper extends BaseMapper<SysUserStudy> {

    public List<SysStudyData> selectVarietyCount(@Param("userId") String userId, @Param("start") DateTime start, @Param("end") DateTime end);
}
