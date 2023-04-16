package com.xiaozhang.springboot.controller;


import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaozhang.springboot.common.lang.Result;
import com.xiaozhang.springboot.domain.SysFlowLeave;
import com.xiaozhang.springboot.domain.SysFlowOvertime;
import com.xiaozhang.springboot.domain.SysUser;
import com.xiaozhang.springboot.service.SysFlowOvertimeService;
import com.xiaozhang.springboot.utils.PageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * <p>
 * 加班申请表 前端控制器
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-04-12
 */
@RestController
@RequestMapping("/sys-flow/overtime")
@Api(tags = "加班申请接口")
@Slf4j
public class SysFlowOvertimeController {

    @Autowired
    SysFlowOvertimeService sysFlowOvertimeService;

    @Autowired
    PageUtils pageUtil;

    @PostMapping("/add")
    @ApiOperation("用户加班申请，需要token")
    @Transactional(rollbackFor = Exception.class)
    public Result addOvertimeFlow(@Validated @RequestBody SysFlowOvertime sysFlowOver) {

        long between = DateUtil.between(sysFlowOver.getStartTime(), sysFlowOver.getEndTime(), DateUnit.MINUTE);
        sysFlowOver.setDuration(Double.valueOf(NumberUtil.roundStr(NumberUtil.div(between, 60), 1)));
        sysFlowOver.setCreateTime(new Date());
        boolean save = sysFlowOvertimeService.save(sysFlowOver);

        return save ? Result.success("提交成功!") : Result.fail("提交失败!请稍后再试一次!");
    }

    @GetMapping("/list")
    @ApiOperation("请假记录列表，需要token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "请求页数", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "请求页大小", required = false, dataType = "Integer", paramType = "query")
    })
    public Result list(@RequestParam String userId) {

        Page<SysFlowOvertime> pageData = sysFlowOvertimeService.page(pageUtil.getPage(), new QueryWrapper<SysFlowOvertime>()
                .like("user_id", userId));

        return Result.success(200, "请假列表获取成功", pageData, "");
    }
}
