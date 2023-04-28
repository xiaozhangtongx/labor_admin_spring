package com.xiaozhang.springboot.mapper;

import com.xiaozhang.springboot.domain.SysQuestion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 考试问题表 Mapper 接口
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-04-24
 */
@Mapper
public interface SysQuestionMapper extends BaseMapper<SysQuestion> {

   List<SysQuestion> selectQuestion(String id);
}
