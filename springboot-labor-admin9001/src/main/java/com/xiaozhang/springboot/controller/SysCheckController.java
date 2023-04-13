package com.xiaozhang.springboot.controller;


import com.xiaozhang.springboot.common.lang.Result;
import com.xiaozhang.springboot.domain.SysCheck;
import com.xiaozhang.springboot.domain.SysDeptStandard;
import com.xiaozhang.springboot.domain.SysUser;
import com.xiaozhang.springboot.service.SysCheckService;
import com.xiaozhang.springboot.service.SysDeptStandardService;
import com.xiaozhang.springboot.service.SysUserService;
import com.xiaozhang.springboot.utils.MathUtils;
import com.xiaozhang.springboot.utils.SchedulerUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 出勤表 前端控制器
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-04-02
 */
@RestController
@RequestMapping("/sys-check")
@Api(tags = "打卡接口")
@Slf4j
public class SysCheckController {

    @Autowired
    SysCheckService sysCheckService;

    @Autowired
    SysDeptStandardService sysDeptStandardService;

    @Autowired
    SysUserService sysUserService;

    @Autowired
    MathUtils mathUtils;

    @Autowired
    SchedulerUtils schedulerUtils;

    @PostMapping("/add")
    @ApiOperation("用户打卡,需要token")
    public Result addCheck(@RequestBody @Validated SysCheck sysCheck) {

        SysUser userInfo = sysUserService.getById(sysCheck.getUserId());

        SysDeptStandard standard = sysDeptStandardService.getRuleById(userInfo.getDeptId());

        double distance = mathUtils.calculateDistance(standard.getLat(), standard.getLon(), sysCheck.getLat(), sysCheck.getLon());

        log.info("---------计算距离-------:" + distance);

        if (distance > standard.getRadius()) {
            return Result.fail("请在工作地点内打卡!");
        } else {
            List<SysCheck> checkList = sysCheckService.getCheckInfoToday(sysCheck.getUserId());

            if (checkList.size() == 0) {
                sysCheck.setCreateTime(new Date());
                sysCheckService.save(sysCheck);
            } else {
                SysCheck checkInfo = checkList.get(0);
                checkInfo.setUpdateTime(new Date());
                sysCheckService.updateById(checkInfo);
            }

            return Result.success("打卡成功!");
        }
    }

    @GetMapping("/admin/check")
    @ApiOperation("管理员手工校验,需要token")
    public Result check() {

        schedulerUtils.checkSysCheck();
        return Result.success("校验成功");
    }

}
