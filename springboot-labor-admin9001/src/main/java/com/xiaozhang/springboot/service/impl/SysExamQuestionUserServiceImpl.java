package com.xiaozhang.springboot.service.impl;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaozhang.springboot.domain.SysExamQuestionUser;
import com.xiaozhang.springboot.domain.SysQuestion;
import com.xiaozhang.springboot.domain.SysRole;
import com.xiaozhang.springboot.domain.SysUserExam;
import com.xiaozhang.springboot.mapper.SysExamQuestionUserMapper;
import com.xiaozhang.springboot.service.SysExamQuestionUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaozhang.springboot.service.SysQuestionService;
import com.xiaozhang.springboot.service.SysUserExamService;
import com.xiaozhang.springboot.utils.MathUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户答题表 服务实现类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-05-02
 */
@Service
@Slf4j
public class SysExamQuestionUserServiceImpl extends ServiceImpl<SysExamQuestionUserMapper, SysExamQuestionUser> implements SysExamQuestionUserService {


    @Autowired
    SysQuestionService sysQuestionService;

    @Autowired
    SysUserExamService sysUserExamService;

    @Autowired
    MathUtils mathUtils;

    @Override
    public SysUserExam submitAnswer(List<SysExamQuestionUser> userAnswerList, String userExamId, String userId) {

        Double grades = 0.0;
        Double totalGrades = 0.0;
        SysUserExam sysUserExam = new SysUserExam();

        List<SysExamQuestionUser> sysExamQuestionUserList = new ArrayList<>();

        for (SysExamQuestionUser sysExamQuestionUser : userAnswerList) {
            sysExamQuestionUser.setUserId(userId);

            // 判断用户是否作对,并统计分数
            SysQuestion questionInfoById = sysQuestionService.getOne(new QueryWrapper<SysQuestion>().eq("id", sysExamQuestionUser.getExamQuestionId()));

            if (sysExamQuestionUser.getUserAnswer().equals(questionInfoById.getAnswerId())) {
                sysExamQuestionUser.setUserScore(questionInfoById.getScore());
                sysExamQuestionUser.setDoRight(1);
            } else {
                sysExamQuestionUser.setUserScore(0.0);
                sysExamQuestionUser.setDoRight(0);
            }

            totalGrades += questionInfoById.getScore();
            grades += sysExamQuestionUser.getUserScore();

            sysExamQuestionUserList.add(sysExamQuestionUser);
        }

        boolean b = saveBatch(sysExamQuestionUserList);

        // 提交成功后更新用户考试数据
        if (b) {
            SysUserExam userExamInfoById = sysUserExamService.getById(userExamId);

            userExamInfoById.setEndTime(new Date());
            userExamInfoById.setGrades(grades);
            userExamInfoById.setExamScore(totalGrades);
            userExamInfoById.setSpendTime(mathUtils.getDuration(new Date(), userExamInfoById.getStartTime()));
            userExamInfoById.setStatus(1);
            // 判断考试是否合格
            if (NumberUtil.div(grades, totalGrades) > 0.6) {
                userExamInfoById.setRes(1);
                userExamInfoById.setDes("合格");
            } else {
                userExamInfoById.setRes(0);
                userExamInfoById.setDes("不合格");
            }

            sysUserExamService.updateById(userExamInfoById);
            sysUserExam = userExamInfoById;
        }

        // 返回做题的结果
        return sysUserExam;
    }
}
