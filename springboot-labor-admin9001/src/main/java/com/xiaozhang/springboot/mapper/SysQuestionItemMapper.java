package com.xiaozhang.springboot.mapper;

import com.xiaozhang.springboot.domain.SysQuestionItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 题目选项/内容表 Mapper 接口
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-05-02
 */
@Mapper
public interface SysQuestionItemMapper extends BaseMapper<SysQuestionItem> {

}
