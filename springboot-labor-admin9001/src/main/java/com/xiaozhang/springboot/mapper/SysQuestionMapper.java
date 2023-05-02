package com.xiaozhang.springboot.mapper;

import com.xiaozhang.springboot.domain.SysQuestion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 考试问题表 Mapper 接口
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-05-01
 */
@Mapper
public interface SysQuestionMapper extends BaseMapper<SysQuestion> {

}
