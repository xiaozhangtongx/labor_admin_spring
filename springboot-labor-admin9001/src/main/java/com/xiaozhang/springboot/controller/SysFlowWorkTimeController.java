package com.xiaozhang.springboot.controller;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaozhang.springboot.common.lang.Result;
import com.xiaozhang.springboot.domain.SysFlowApproval;
import com.xiaozhang.springboot.domain.SysFlowOvertime;
import com.xiaozhang.springboot.domain.SysFlowWorktime;
import com.xiaozhang.springboot.domain.SysUser;
import com.xiaozhang.springboot.service.SysCheckService;
import com.xiaozhang.springboot.service.SysFlowApprovalService;
import com.xiaozhang.springboot.service.SysFlowWorktimeService;
import com.xiaozhang.springboot.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.xiaozhang.springboot.utils.PageUtils;
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

    @Autowired
    SysUserService sysUserService;

    @Autowired
    PageUtils pageUtil;

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

    @GetMapping("/list")
    @ApiOperation("工时补充记录列表，需要token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "请求页数", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "请求页大小", required = false, dataType = "Integer", paramType = "query")
    })
    public Result list(@RequestParam String userId) {

        Page<SysFlowWorktime> pageData = sysFlowWorktimeService.page(pageUtil.getPage(), new QueryWrapper<SysFlowWorktime>()
                .like("user_id", userId));
        pageData.getRecords().forEach(Flow -> {
            SysUser leader = sysUserService.getById(Flow.getLeaderId());
            leader.setPassword("");
            Flow.setLeader(leader);
        });
        return Result.success(200, "请假列表获取成功", pageData, "");
    }
}
