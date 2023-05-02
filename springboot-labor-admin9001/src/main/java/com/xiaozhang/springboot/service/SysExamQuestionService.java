package com.xiaozhang.springboot.service;

import com.xiaozhang.springboot.domain.SysExamQuestion;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaozhang.springboot.domain.SysQuestion;

import java.util.List;

/**
 * <p>
 * 试卷问题表 服务类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-05-02
 */
public interface SysExamQuestionService extends IService<SysExamQuestion> {

    /**
     * 通过考试id获取试题
     *
     * @param examId
     * @return
     */
    List<SysQuestion> getExamQuestions(String examId);
}
