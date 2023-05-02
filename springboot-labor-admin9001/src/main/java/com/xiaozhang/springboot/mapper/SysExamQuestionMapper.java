package com.xiaozhang.springboot.mapper;

import com.xiaozhang.springboot.domain.SysExamQuestion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaozhang.springboot.domain.SysQuestion;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 试卷问题表 Mapper 接口
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-05-02
 */
@Mapper
public interface SysExamQuestionMapper extends BaseMapper<SysExamQuestion> {

}
