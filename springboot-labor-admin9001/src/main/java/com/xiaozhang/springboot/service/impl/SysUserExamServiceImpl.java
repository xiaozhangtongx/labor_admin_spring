package com.xiaozhang.springboot.service.impl;

import com.xiaozhang.springboot.domain.SysExam;
import com.xiaozhang.springboot.domain.SysExamQuestionUser;
import com.xiaozhang.springboot.domain.SysUserExam;
import com.xiaozhang.springboot.mapper.SysUserExamMapper;
import com.xiaozhang.springboot.service.SysExamQuestionUserService;
import com.xiaozhang.springboot.service.SysExamService;
import com.xiaozhang.springboot.service.SysUserExamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-05-02
 */
@Service
@Slf4j
public class SysUserExamServiceImpl extends ServiceImpl<SysUserExamMapper, SysUserExam> implements SysUserExamService {

    @Autowired
    SysExamService sysExamService;

    @Autowired
    SysExamQuestionUserService sysExamQuestionUserService;

    /**
     * 判断当前时间是否可以答题
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static boolean canAnswer(Date startTime, Date endTime) {
        LocalDateTime now = LocalDateTime.now();
        if (startTime == null) {
            return true;
        } else if (endTime == null) {
            return now.isAfter(startTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        } else {
            LocalDateTime start = startTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime end = endTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            return now.isAfter(start) && now.isBefore(end);
        }
    }

    @Override
    public SysUserExam addExamAnswer(SysUserExam sysUserExam) {
        // 首先判断当前时间是否可以进入考场
        String examId = sysUserExam.getExamId();
        SysExam examInfoById = sysExamService.getInfoById(examId);

        boolean canAnswer = canAnswer(examInfoById.getStartTime(), examInfoById.getEndTime());

        log.info("-------1--------" + sysUserExam.getUserAnswerList().size());

        if (canAnswer) {
            SysUserExam sysUserExamRes = sysExamQuestionUserService.submitAnswer(sysUserExam.getUserAnswerList(), sysUserExam.getId(), sysUserExam.getUserId());
            return sysUserExamRes;
        }

        return null;
    }

    @Override
    public boolean enterExam(SysUserExam sysUserExam) {

        // 首先判断当前时间是否可以进入考场
        String examId = sysUserExam.getExamId();
        SysExam examInfoById = sysExamService.getInfoById(examId);

        boolean canAnswer = canAnswer(examInfoById.getStartTime(), examInfoById.getEndTime());
        boolean save = true;

        if (canAnswer) {
            sysUserExam.setStartTime(new Date());
            sysUserExam.setStatus(2);
            sysUserExam.setDes("答题中");
            save = save(sysUserExam);
        }

        return canAnswer && save;
    }


}
