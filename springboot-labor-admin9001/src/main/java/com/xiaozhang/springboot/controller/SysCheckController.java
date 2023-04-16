package com.xiaozhang.springboot.controller;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaozhang.springboot.common.lang.Result;
import com.xiaozhang.springboot.domain.SysCheck;
import com.xiaozhang.springboot.domain.SysDeptStandard;
import com.xiaozhang.springboot.domain.SysFlowLeave;
import com.xiaozhang.springboot.domain.SysUser;
import com.xiaozhang.springboot.service.SysCheckService;
import com.xiaozhang.springboot.service.SysDeptStandardService;
import com.xiaozhang.springboot.service.SysUserService;
import com.xiaozhang.springboot.utils.MathUtils;
import com.xiaozhang.springboot.utils.PageUtils;
import com.xiaozhang.springboot.utils.SchedulerUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
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

    @Autowired
    PageUtils pageUtils;

    @PostMapping("/add")
    @ApiOperation("用户打卡,需要token")
    public Result addCheck(@RequestBody @Validated SysCheck sysCheck) {

        SysUser userInfo = sysUserService.getById(sysCheck.getUserId());

        SysDeptStandard standard = sysDeptStandardService.getRuleById(userInfo.getDeptId());

        double distance = mathUtils.calculateDistance(standard.getLat(), standard.getLon(), sysCheck.getLat(), sysCheck.getLon());

        if (distance > standard.getRadius()) {
            return Result.fail("请在工作地点内打卡!");
        } else {
            List<SysCheck> checkList = sysCheckService.getCheckInfoToday(sysCheck.getUserId(), new Date());
            SysCheck checkInfo = new SysCheck();

            if (checkList.isEmpty()) {
                checkInfo.setUserId(sysCheck.getUserId());
                checkInfo.setCreateTime(new Date());
            } else {
                checkInfo = checkList.get(0);
            }

            if (ObjectUtil.isNull(checkInfo.getSignInTime())) {
                checkInfo.setSignInTime(new Date());
            } else {
                checkInfo.setSignOutTime(new Date());
                checkInfo.setUpdateTime(new Date());
            }
            checkInfo.setLat(sysCheck.getLat());
            checkInfo.setLon(sysCheck.getLon());

            sysCheckService.saveOrUpdate(checkInfo);

            return Result.success("打卡成功!");
        }
    }

    @GetMapping("/admin/check")
    @ApiOperation("管理员手工校验,需要token")
    public Result check() {

        schedulerUtils.checkSysCheck();
        return Result.success("校验成功");
    }

    @GetMapping("/admin/addUser")
    @ApiOperation("管理员手工添加用户,需要token")
    public Result addUser() {

        Boolean flag = schedulerUtils.checkSysCheckUser();

        return flag ? Result.success("添加成功") : Result.fail("添加失败");
    }

    @GetMapping("/list")
    @ApiOperation("考勤列表，需要token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "请求页数", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "请求页大小", required = false, dataType = "Integer", paramType = "query")
    })
    public Result list(@RequestParam String userId, Integer status) {
        Page<SysCheck> pageData = sysCheckService.page(pageUtils.getPage(), new QueryWrapper<SysCheck>()
                .like("user_id", userId).like("status", status == null ? "" : status));

        pageData.getRecords().forEach(sysCheck -> {
            SysUser user = sysUserService.getById(sysCheck.getUserId());
            user.setPassword("");
            sysCheck.setUser(user);
        });

        return Result.success(200, "考勤列表获取成功", pageData, "");
    }

}
