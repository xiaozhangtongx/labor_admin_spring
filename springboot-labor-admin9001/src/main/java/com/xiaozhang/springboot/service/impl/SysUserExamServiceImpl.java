package com.xiaozhang.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaozhang.springboot.domain.SysExam;
import com.xiaozhang.springboot.domain.SysQuestion;
import com.xiaozhang.springboot.domain.SysUserExam;
import com.xiaozhang.springboot.domain.postAnswer;
import com.xiaozhang.springboot.mapper.SysExamMapper;
import com.xiaozhang.springboot.mapper.SysQuestionMapper;
import com.xiaozhang.springboot.mapper.SysUserExamMapper;
import com.xiaozhang.springboot.service.SysUserExamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-04-24
 */
@Service
public class SysUserExamServiceImpl extends ServiceImpl<SysUserExamMapper, SysUserExam> implements SysUserExamService {

    @Autowired
    SysQuestionMapper sysQuestionMapper;

    @Override
    public Double getScore(SysUserExam sysUserExam) {
        System.out.println("-----------------"+sysUserExam);
        List<SysQuestion> questions=sysQuestionMapper.selectList(new QueryWrapper<SysQuestion>().eq("exam_id",sysUserExam.getExamId()));
        Double score=0.0;
        for(SysQuestion question : questions)
        {
            for(postAnswer answer:sysUserExam.getAnswers())
            {
                if(answer.getId().equals(question.getId()))
                {
                    score+=answer.getAnswer().equals(question.getAnswerId())?question.getScore():0.0;
                    System.out.println(score);
                }
            }
        }
        return score;
    }
}
