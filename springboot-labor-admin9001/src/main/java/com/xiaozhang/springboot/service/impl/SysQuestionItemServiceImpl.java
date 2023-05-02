package com.xiaozhang.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaozhang.springboot.domain.SysQuestionItem;
import com.xiaozhang.springboot.mapper.SysQuestionItemMapper;
import com.xiaozhang.springboot.service.SysQuestionItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 题目选项/内容表 服务实现类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-05-02
 */
@Service
public class SysQuestionItemServiceImpl extends ServiceImpl<SysQuestionItemMapper, SysQuestionItem> implements SysQuestionItemService {

    @Autowired(required = false)
    SysQuestionItemMapper sysQuestionItemMapper;

    @Override
    public List<SysQuestionItem> getQuestionItem(String questionId) {

        List<SysQuestionItem> sysQuestionItems = sysQuestionItemMapper.selectList(new QueryWrapper<SysQuestionItem>()
                .eq("question_id", questionId).orderByAsc("sort"));

        return sysQuestionItems;
    }

    @Override
    public boolean removeByQuestionId(String questionId) {
        int question_id = sysQuestionItemMapper.delete(new QueryWrapper<SysQuestionItem>()
                .eq("question_id", questionId));

        return question_id > 0;
    }
}
