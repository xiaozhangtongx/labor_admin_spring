package com.xiaozhang.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaozhang.springboot.domain.SysExamQuestion;
import com.xiaozhang.springboot.domain.SysQuestion;
import com.xiaozhang.springboot.domain.SysQuestionItem;
import com.xiaozhang.springboot.mapper.SysExamQuestionMapper;
import com.xiaozhang.springboot.service.SysExamQuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaozhang.springboot.service.SysQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 试卷问题表 服务实现类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-05-02
 */
@Service
public class SysExamQuestionServiceImpl extends ServiceImpl<SysExamQuestionMapper, SysExamQuestion> implements SysExamQuestionService {

    @Autowired(required = false)
    SysExamQuestionMapper sysExamQuestionMapper;

    @Autowired
    SysQuestionService sysQuestionService;


    @Override
    public List<SysQuestion> getExamQuestions(String examId) {

        // 首先获取所有的试题id
        List<SysExamQuestion> questionIds = sysExamQuestionMapper.selectList(new QueryWrapper<SysExamQuestion>()
                .eq("exam_id", examId).select("question_id").orderByAsc("item_order"));

        List<SysQuestion> sysQuestion = new ArrayList();

        for (SysExamQuestion sysExamQuestion : questionIds) {
            String questionId = sysExamQuestion.getQuestionId();

            SysQuestion questionInfoById = sysQuestionService.getInfoById(questionId);
            questionInfoById.setAnswerId("");
            questionInfoById.setAnswerContent("");

            sysQuestion.add(questionInfoById);
        }

        return sysQuestion;
    }
}
