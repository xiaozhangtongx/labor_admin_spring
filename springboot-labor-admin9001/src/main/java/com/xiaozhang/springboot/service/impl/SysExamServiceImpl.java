package com.xiaozhang.springboot.service.impl;

import com.xiaozhang.springboot.domain.SysExam;
import com.xiaozhang.springboot.domain.SysQuestion;
import com.xiaozhang.springboot.mapper.SysExamMapper;
import com.xiaozhang.springboot.service.SysExamQuestionService;
import com.xiaozhang.springboot.service.SysExamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 考试表 服务实现类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-05-02
 */
@Service
public class SysExamServiceImpl extends ServiceImpl<SysExamMapper, SysExam> implements SysExamService {

    @Autowired(required = false)
    SysExamMapper sysExamMapper;

    @Autowired
    SysExamQuestionService sysExamQuestionService;

    @Override
    public SysExam getInfoById(String id) {

        SysExam sysExam = getById(id);

        List<SysQuestion> sysQuestionList = sysExamQuestionService.getExamQuestions(id);

        if (sysQuestionList.size() != 0) {
            sysExam.setQuestions(sysQuestionList);
        }

        return sysExam;
    }
}
