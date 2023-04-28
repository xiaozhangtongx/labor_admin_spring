package com.xiaozhang.springboot.mapper;

import com.xiaozhang.springboot.domain.SysExam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 考试表 Mapper 接口
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-04-24
 */
@Mapper
public interface SysExamMapper extends BaseMapper<SysExam> {

    public List<SysExam> selectMyList();
}
