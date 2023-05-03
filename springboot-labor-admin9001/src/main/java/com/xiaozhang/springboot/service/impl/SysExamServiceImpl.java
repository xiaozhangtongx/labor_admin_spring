package com.xiaozhang.springboot.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaozhang.springboot.domain.SysExam;
import com.xiaozhang.springboot.domain.SysExamQuestion;
import com.xiaozhang.springboot.domain.SysQuestion;
import com.xiaozhang.springboot.domain.SysQuestionItem;
import com.xiaozhang.springboot.mapper.SysExamMapper;
import com.xiaozhang.springboot.service.SysExamQuestionService;
import com.xiaozhang.springboot.service.SysExamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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

    @Override
    public void updateExamPaperList(Date now) {
        List<SysExam> examPapers = sysExamMapper.selectList(null);

        for (SysExam examPaper : examPapers) {
            Date startTime = examPaper.getStartTime();
            Date endTime = examPaper.getEndTime();
            int status = startTime == null && endTime == null ? 1 :
                    startTime == null ? (now.before(endTime) ? 1 : 2) :
                            endTime == null ? (now.after(startTime) ? 1 : 0) :
                                    now.before(startTime) ? 0 : now.after(endTime) ? 2 : 1;
            examPaper.setStatus(status);
            sysExamMapper.updateById(examPaper);
        }
    }

    @Override
    public Boolean deleteById(String id) {
        return sysExamMapper.deleteById(id) > 0;
    }

    @Override
    public boolean addExam(SysExam sysExam) {
        String id = IdUtil.simpleUUID();

        List<SysExamQuestion> sysExamQuestionList = getExamQuestionList(sysExam, id);

        boolean b = sysExamQuestionService.saveBatch(sysExamQuestionList);

        sysExam.setId(id);
        sysExam.setCreateTime(new Date());

        boolean save = save(sysExam);

        return b && save;
    }

    @Override
    public boolean editExam(SysExam sysExam) {

        // 首先删除问题选项
        boolean question_id = sysExamQuestionService.removeByExamId(sysExam.getId());

        // 然后插入问题子选项
        List<SysExamQuestion> sysExamQuestionList = getExamQuestionList(sysExam, sysExam.getId());
        boolean b = sysExamQuestionService.saveBatch(sysExamQuestionList);

        // 最后更新
        boolean b1 = updateById(sysExam);

        return question_id && b && b1;
    }

    private List<SysExamQuestion> getExamQuestionList(SysExam sysExam, String id) {
        List<SysExamQuestion> sysExamQuestionList = new ArrayList();
        Integer index = 0;

        for (SysQuestion sysQuestion : sysExam.getQuestions()) {
            SysExamQuestion sysExamQuestion = new SysExamQuestion();

            sysExamQuestion.setExamId(id);
            sysExamQuestion.setQuestionId(sysQuestion.getId());
            sysExamQuestion.setItemOrder(index++);
            sysExamQuestion.setCreateTime(new Date());
            sysExamQuestion.setQuestionScore(sysQuestion.getScore());
            sysExamQuestion.setQuestionType(sysQuestion.getType());

            sysExamQuestionList.add(sysExamQuestion);
        }
        return sysExamQuestionList;
    }
}
