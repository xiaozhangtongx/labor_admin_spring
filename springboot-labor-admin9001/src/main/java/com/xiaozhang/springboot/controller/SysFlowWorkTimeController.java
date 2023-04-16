package com.xiaozhang.springboot.controller;


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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

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

        sysFlowWorktime.setId(new SysFlowWorktime().getId());

        Integer workType = sysFlowWorktime.getWorkType();

        if (workType == 0) {
            SysFlowApproval sysFlowApproval = new SysFlowApproval();
            sysFlowApproval.setApplicationId(sysFlowWorktime.getId());
            sysFlowApproval.setApproverId(sysFlowWorktime.getLeaderId());
            sysFlowApproval.setApplicationType("3");

            sysFlowApprovalService.save(sysFlowApproval);
        } else {
            String userList = sysFlowWorktime.getUserlist();
            String[] userIdArray = userList.split(",");

            for(String userId:userIdArray){

            }

        }

        boolean save = sysFlowWorktimeService.save(sysFlowWorktime);

        return save ? Result.success("提交成功!") : Result.fail("提交失败!请稍后再试一次!");
    }
}
