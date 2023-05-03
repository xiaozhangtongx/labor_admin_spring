package com.xiaozhang.springboot.utils;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaozhang.springboot.domain.SysCheck;
import com.xiaozhang.springboot.domain.SysDeptStandard;
import com.xiaozhang.springboot.domain.SysExam;
import com.xiaozhang.springboot.domain.SysUser;
import com.xiaozhang.springboot.mapper.SysCheckMapper;
import com.xiaozhang.springboot.service.SysCheckService;
import com.xiaozhang.springboot.service.SysDeptStandardService;
import com.xiaozhang.springboot.service.SysExamService;
import com.xiaozhang.springboot.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

/**
 * @author: xiaozhangtx
 * @ClassName: SchedulerUtils
 * @Description: TODO 定时执行工具类
 * @date: 2023/4/13 9:04
 * @Version: 1.0
 */
@Component
public class SchedulerUtils {

    @Autowired
    SysCheckService sysCheckService;

    @Autowired
    SysUserService sysUserService;

    @Autowired
    SysDeptStandardService sysDeptStandardService;

    @Autowired
    SysExamService sysExamService;

    @Autowired(required = false)
    SysCheckMapper sysCheckMapper;

    @Autowired
    MathUtils mathUtils;

    /**
     * 每天晚上23点37分执行打卡检测
     */
    @Scheduled(cron = "0 37 23 * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void checkSysCheck() {
        // 获取今天的打卡
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = LocalDateTime.of(now.toLocalDate(), LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(now.toLocalDate(), LocalTime.MAX);

        QueryWrapper<SysCheck> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("create_time", start, end);

        List<SysCheck> sysChecks = sysCheckMapper.selectList(queryWrapper);

        // 统计工时
        sysChecks.forEach(sysCheck -> {
            Date signOutTime = sysCheck.getSignOutTime();
            if (ObjectUtil.isNull(signOutTime)) {
                sysCheck.setDes("未签退！");
                sysCheck.setStatus(1);
            } else {
                Date signInTime = sysCheck.getSignInTime();
                Double duration = mathUtils.getDuration(signInTime, signOutTime);
                sysCheck.setWorkTime(duration);

                // 获取部门标准
                SysUser userInfoById = sysUserService.getById(sysCheck.getUserId());
                SysDeptStandard ruleById = sysDeptStandardService.getRuleById(userInfoById.getDeptId());

                if (sysCheck.getSignInTime().after(ruleById.getLatestTime())) {
                    sysCheck.setDes("你今天迟到了！");
                    sysCheck.setStatus(2);
                } else if (sysCheck.getSignOutTime().before(ruleById.getEarliestTime())) {
                    sysCheck.setDes("你今天提前下班了！");
                    sysCheck.setStatus(3);
                } else if (ruleById.getMinDuration() > duration) {
                    sysCheck.setDes("早退了，工作时间不足");
                    sysCheck.setStatus(4);
                } else {
                    sysCheck.setDes("今天你真棒，按时完成工作了！");
                    sysCheck.setStatus(0);
                }
            }
            sysCheckService.updateById(sysCheck);

        });

    }

    @Scheduled(cron = "0 1 0 * * ?")
    @Transactional(rollbackFor = Exception.class)
    public Boolean checkSysCheckUser() {

        return sysCheckService.copySysUser();
    }

    /**
     * 定时任务，每分钟执行一次，用于判定试卷开始
     */
    @Scheduled(cron = "0 1 0 * * ?")
    public void publishExam() {
        Date now = new Date();
        sysExamService.updateExamPaperList(now);
    }

}
