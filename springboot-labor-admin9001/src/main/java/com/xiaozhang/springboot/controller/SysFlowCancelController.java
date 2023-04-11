package com.xiaozhang.springboot.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaozhang.springboot.common.lang.Result;
import com.xiaozhang.springboot.domain.SysFlowCancel;
import com.xiaozhang.springboot.domain.SysFlowLeave;
import com.xiaozhang.springboot.domain.SysUser;
import com.xiaozhang.springboot.service.SysFlowCancelService;
import com.xiaozhang.springboot.service.SysFlowLeaveService;
import com.xiaozhang.springboot.service.SysUserService;
import com.xiaozhang.springboot.utils.PageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * <p>
 * 销假表 前端控制器
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-04-11
 */
@RestController
@RequestMapping("/sys-flow/cancel")
@Api(tags = "销假接口")
public class SysFlowCancelController {

    @Autowired
    SysFlowCancelService sysFlowCancelService;

    @Autowired
    SysFlowLeaveService sysFlowLeaveService;

    @Autowired
    SysUserService sysUserService;

    @Autowired
    PageUtils pageUtil;

    @PostMapping("/add")
    @ApiOperation("用户销假，需要token")
    public Result addCancelFlow(@Validated @RequestBody SysFlowCancel sysFlowCancel) {

        SysFlowLeave flowLeave = sysFlowLeaveService.getById(sysFlowCancel.getLeaveId());

        if (flowLeave == null) {
            return Result.fail("无相关请假记录");
        } else {
            sysFlowCancel.setCreateTime(new Date());
            boolean save = sysFlowCancelService.save(sysFlowCancel);

            return save ? Result.success("提交成功!") : Result.fail("提交失败!请稍后再试一次!");
        }

    }


    @GetMapping("/list")
    @ApiOperation("销假记录列表，需要token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "请求页数", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "请求页大小", required = false, dataType = "Integer", paramType = "query")
    })
    public Result list(@RequestParam String userId) {

        Page<SysFlowCancel> pageData = sysFlowCancelService.page(pageUtil.getPage(), new QueryWrapper<SysFlowCancel>()
                .eq("user_id", userId));

        pageData.getRecords().forEach(cancelFlow -> {
            SysUser leader = sysUserService.getById(cancelFlow.getLeaderId());
            leader.setPassword("");
            cancelFlow.setLeader(leader);
        });

        return Result.success(200, "销假列表获取成功", pageData, "");
    }
}
