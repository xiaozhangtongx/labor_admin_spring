package com.xiaozhang.springboot.mapper;

import com.xiaozhang.springboot.domain.SysQuestionItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 题目选项/内容表 Mapper 接口
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-04-24
 */
@Mapper
public interface SysQuestionItemMapper extends BaseMapper<SysQuestionItem> {

    List<SysQuestionItem> selectItem(String id);
}
