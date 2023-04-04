package com.xiaozhang.springboot.controller;


import com.xiaozhang.springboot.common.lang.Result;
import com.xiaozhang.springboot.domain.SysFlowLeave;
import com.xiaozhang.springboot.service.SysFlowLeaveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/add")
    @ApiOperation("用户请假，需要token")
    public Result addLeaveFlow(@RequestBody SysFlowLeave sysFlowLeave) {

        sysFlowLeave.setCreateTime(new Date());
        boolean save = sysFlowLeaveService.save(sysFlowLeave);

        return save ? Result.success("提交成功!") : Result.fail("提交失败!请稍后再试一次!");
    }

}
