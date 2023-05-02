package com.xiaozhang.springboot.service;

import com.xiaozhang.springboot.domain.SysExamQuestionUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaozhang.springboot.domain.SysUserExam;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * <p>
 * 用户答题表 服务类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-05-02
 */
public interface SysExamQuestionUserService extends IService<SysExamQuestionUser> {

    /**
     * 用户提交答案
     *
     * @param userAnswerList
     * @param userExamId
     * @param userId
     * @return
     */
    SysUserExam submitAnswer(List<SysExamQuestionUser> userAnswerList, @NotBlank(message = "考试序号不能为空") String userExamId, String userId);
}
