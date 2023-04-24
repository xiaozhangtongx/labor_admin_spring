package com.xiaozhang.springboot.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaozhang.springboot.common.lang.Result;
import com.xiaozhang.springboot.domain.SysFlowApproval;
import com.xiaozhang.springboot.service.*;
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
 * 审批表 前端控制器
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-04-11
 */
@RestController
@RequestMapping("/sys-flow/approval")
@Api(tags = "审批接口")
@Slf4j
public class SysFlowApprovalController {

    @Autowired
    SysFlowApprovalService sysFlowApprovalService;

    @Autowired
    SysFlowLeaveService sysFlowLeaveService;

    @Autowired
    SysFlowCancelService sysFlowCancelService;

    @Autowired
    SysFlowOvertimeService sysFlowOvertimeService;

    @Autowired
    SysFlowWorktimeService sysFlowWorktimeService;

    @Autowired
    PageUtils pageUtil;

    @PutMapping("/update")
    @ApiOperation("审批,需要token")
    @Transactional(rollbackFor = Exception.class)
    public Result update(@Validated @RequestBody SysFlowApproval sysFlowApproval) {

        SysFlowApproval flowApprovalById = sysFlowApprovalService.getById(sysFlowApproval.getId());

        // 判断审批人是否一致,并判断是否已经处理
        if (flowApprovalById.getApproverId().equals(sysFlowApproval.getApproverId()) && flowApprovalById.getStatus() != 2) {
            sysFlowApproval.setUpdateTime(new Date());
            sysFlowApproval.setApprovalTime(new Date());
            sysFlowApproval.setStatus(0);

            boolean save = sysFlowApprovalService.updateById(sysFlowApproval);

            if (save) {
                String applicationType = flowApprovalById.getApplicationType();
                // 根据审批类型处理对应的方法
                switch (applicationType) {
                    // 处理请假
                    case "0":
                        sysFlowLeaveService.updateStatus(sysFlowApproval.getApprovalResult(), flowApprovalById.getApplicationId());
                        break;
                    // 处理销假
                    case "1":
                        sysFlowCancelService.updateStatus(sysFlowApproval.getApprovalResult(), flowApprovalById.getApplicationId());
                        break;
                    // 处理加班
                    case "2":
                        sysFlowOvertimeService.updateStatus(sysFlowApproval.getApprovalResult(), flowApprovalById.getApplicationId());
                        break;
                    // 处理补办
                    case "3":
                        sysFlowWorktimeService.updateStatus(sysFlowApproval.getApprovalResult(), flowApprovalById.getApplicationId());
                        break;

                    default:
                        return Result.fail("无相关操作");
                }

                return Result.success("提交成功");
            } else {
                return Result.fail("提交失败");
            }
        } else {
            return Result.fail("你没有审批权限");
        }

    }

    @GetMapping("/list")
    @ApiOperation("审批列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "请求页数", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "请求页大小", required = false, dataType = "Integer", paramType = "query")
    })
    public Result list(@RequestParam String approverId, Integer status, String applicationType) {

        Page<SysFlowApproval> pageData = sysFlowApprovalService.page(pageUtil.getPage(), new QueryWrapper<SysFlowApproval>()
                .eq("approver_id", approverId).like("status", status == null ? "" : status).eq("application_type", applicationType));

        // 根据审批类型处理对应的方法
        for (SysFlowApproval sysFlowApproval : pageData.getRecords()) {
            switch (sysFlowApproval.getApplicationType()) {
                // 处理请假
                case "0":
                    sysFlowApproval.setFlowLeaveInfo(sysFlowLeaveService.getById(sysFlowApproval.getApplicationId()));
                    break;
                // 处理销假
                case "1":
                    sysFlowApproval.setFlowCancelInfo(sysFlowCancelService.getById(sysFlowApproval.getApplicationId()));
                    break;
                // 处理加班
                case "2":
                    sysFlowApproval.setFlowOverTimeInfo(sysFlowOvertimeService.getById(sysFlowApproval.getApplicationId()));
                    break;
                // 处理补办
                case "3":
                    sysFlowApproval.setFlowWorkTimeInfo(sysFlowWorktimeService.getById(sysFlowApproval.getApplicationId()));
                    break;
                default:
                    return Result.fail("无相关操作");
            }
        }

        return Result.success(200, "审批列表获取成功", pageData, "");
    }
}
