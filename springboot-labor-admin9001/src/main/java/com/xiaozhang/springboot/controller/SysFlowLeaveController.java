package com.xiaozhang.springboot.controller;


import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaozhang.springboot.common.lang.Result;
import com.xiaozhang.springboot.domain.SysFlowLeave;
import com.xiaozhang.springboot.domain.SysUser;
import com.xiaozhang.springboot.service.SysFlowLeaveService;
import com.xiaozhang.springboot.service.SysUserService;
import com.xiaozhang.springboot.utils.PageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;

/**
 * <p>
 * 请假表 前端控制器
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-04-04
 */
@RestController
@RequestMapping("/sys-flow/leave")
@Api(tags = "请假接口")
public class SysFlowLeaveController {

    @Autowired
    SysFlowLeaveService sysFlowLeaveService;

    @Autowired
    SysUserService sysUserService;

    @Autowired
    PageUtils pageUtil;

    @PostMapping("/add")
    @ApiOperation("用户请假，需要token")
    @Transactional(rollbackFor = Exception.class)
    public Result addLeaveFlow(@Validated @RequestBody SysFlowLeave sysFlowLeave) {

        sysFlowLeave.setCreateTime(new Date());
        long between = DateUtil.between(sysFlowLeave.getStartTime(), sysFlowLeave.getEndTime(), DateUnit.DAY);
        sysFlowLeave.setDuration(between);

        boolean save = sysFlowLeaveService.save(sysFlowLeave);

        return save ? Result.success("提交成功!") : Result.fail("提交失败!请稍后再试一次!");
    }

    @GetMapping("/Info/{id}")
    @ApiOperation("申请表单信息,需要token")
    public Result delete(@PathVariable String id) {

        SysFlowLeave leaveInfoById = sysFlowLeaveService.getLeaveInfoById(id);

        return ObjectUtil.isNotNull(leaveInfoById) ? Result.success("获取成功") : Result.fail("获取失败");
    }


    @GetMapping("/list")
    @ApiOperation("请假记录列表，需要token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "请求页数", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "请求页大小", required = false, dataType = "Integer", paramType = "query")
    })
    public Result list(@RequestParam String userId) {

        Page<SysFlowLeave> pageData = sysFlowLeaveService.page(pageUtil.getPage(), new QueryWrapper<SysFlowLeave>()
                .like("user_id", userId).orderByDesc("create_time"));

        pageData.getRecords().forEach(leaveFlow -> {
            SysUser leader = sysUserService.getById(leaveFlow.getLeaderId());
            leader.setPassword("");
            leaveFlow.setLeader(leader);
        });

        return Result.success(200, "请假列表获取成功", pageData, "");
    }

}
