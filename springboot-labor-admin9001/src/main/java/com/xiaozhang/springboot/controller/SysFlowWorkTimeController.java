package com.xiaozhang.springboot.controller;


import cn.hutool.core.util.ObjectUtil;
import com.xiaozhang.springboot.common.lang.Result;
import com.xiaozhang.springboot.domain.SysFlowApproval;
import com.xiaozhang.springboot.domain.SysFlowOvertime;
import com.xiaozhang.springboot.domain.SysFlowWorktime;
import com.xiaozhang.springboot.service.SysCheckService;
import com.xiaozhang.springboot.service.SysFlowApprovalService;
import com.xiaozhang.springboot.service.SysFlowWorktimeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * <p>
 * 工时补办申请表 前端控制器
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-04-16
 */
@RestController
@RequestMapping("/sys-flow/workTime")
@Api(tags = "工作补办接口")
@Slf4j
public class SysFlowWorkTimeController {

    @Autowired
    SysFlowWorktimeService sysFlowWorktimeService;

    @Autowired
    SysFlowApprovalService sysFlowApprovalService;

    @PostMapping("/add")
    @ApiOperation("工时补办，需要token")
    @Transactional(rollbackFor = Exception.class)
    public Result addWorkTimeFlow(@Validated @RequestBody SysFlowWorktime sysFlowWorktime) {

        sysFlowWorktime.setCreateTime(new Date());
        boolean save = sysFlowWorktimeService.save(sysFlowWorktime);

        return save ? Result.success("提交成功!") : Result.fail("提交失败!请稍后再试一次!");
    }

    @GetMapping("/info/{id}")
    @ApiOperation("加班表单信息,需要token")
    public Result getInfoById(@PathVariable String id) {

        SysFlowWorktime workTimeInfoById = sysFlowWorktimeService.getWorkTimeInfoById(id);

        return ObjectUtil.isNotNull(workTimeInfoById) ? Result.success("获取成功") : Result.fail("获取失败");
    }
}
