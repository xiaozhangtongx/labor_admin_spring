package com.xiaozhang.springboot.controller;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaozhang.springboot.common.lang.Result;
import com.xiaozhang.springboot.domain.SysTeam;
import com.xiaozhang.springboot.domain.SysUser;
import com.xiaozhang.springboot.domain.SysUserTeam;
import com.xiaozhang.springboot.service.SysTeamService;
import com.xiaozhang.springboot.service.SysUserService;
import com.xiaozhang.springboot.service.SysUserTeamService;
import com.xiaozhang.springboot.utils.PageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * <p>
 * 小组表 前端控制器
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-05-11
 */
@RestController
@Api(tags = "小组接口")
@RequestMapping("/sys-team")
public class SysTeamController {
    @Autowired
    SysTeamService sysTeamService;

    @Autowired
    SysUserTeamService sysUserTeamService;

    @Autowired
    PageUtils pageUtils;

    @Autowired
    SysUserService sysUserService;

    @GetMapping("/list")
    @ApiOperation("获取小组列表,需要token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "请求页数", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "请求页大小", required = false, dataType = "Integer", paramType = "query")
    })
    public Result geTeamList(String teamName) {

        Page<SysTeam> pageData = sysTeamService.page(pageUtils.getPage(), new QueryWrapper<SysTeam>()
                .like("team_name", teamName == null ? "" : teamName)
                .orderByDesc("create_time"));

        pageData.getRecords().forEach(sysTeam -> {
            SysUser leaderInfoById = sysUserService.getInfoById(sysTeam.getTeamLeaderId());
            if (ObjectUtil.isNotNull(leaderInfoById)) {
                sysTeam.setTeamLeader(leaderInfoById);
            }
        });

        return Result.success(200, "小组列表获取成功", pageData, "");
    }

    @GetMapping("/info/{id}")
    @ApiOperation("获取小组数据,需要token")
    public Result getTeamInfo(@PathVariable(name = "id") String id) {

        SysTeam sysTeam = sysTeamService.getInfoById(id);

        return Result.success(200, "小组信息获取成功", sysTeam, "");
    }


    @PostMapping("/add")
    @ApiOperation("创建小组,需要token")
    @Transactional(rollbackFor = Exception.class)
    public Result add(@Validated @RequestBody SysTeam sysTeam) {

        sysTeam.setCreateTime(new Date());

        boolean flag = sysTeamService.addTeam(sysTeam);

        return flag ? Result.success("添加成功") : Result.fail("添加失败");
    }


    @PutMapping("/update")
    @ApiOperation("更新小组信息,需要token")
    @Transactional(rollbackFor = Exception.class)
    public Result update(@Validated @RequestBody SysTeam sysTeam) {

        sysTeam.setUpdateTime(new Date());

        boolean flag = sysTeamService.editTeam(sysTeam);

        return flag ? Result.success("修改成功") : Result.fail("修改失败");
    }


    @GetMapping("/member/list")
    @ApiOperation("获取小组成员列表,需要token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "请求页数", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "请求页大小", required = false, dataType = "Integer", paramType = "query")
    })
    public Result getTeamUserList(String userId) {

        Page<SysUserTeam> pageData = sysUserTeamService.page(pageUtils.getPage(), new QueryWrapper<SysUserTeam>()
                .like("user_id", userId == null ? "" : userId)
                .orderByDesc("create_time"));

        pageData.getRecords().forEach(sysTeamMember -> {
            SysUser memberInfoById = sysUserService.getInfoById(sysTeamMember.getUserId());
            if (ObjectUtil.isNotNull(memberInfoById)) {
                memberInfoById.setPassword("");
                sysTeamMember.setTeamMember(memberInfoById);
            }
        });

        return Result.success(200, "小组成员列表获取成功", pageData, "");
    }


    @PostMapping("/member/add")
    @ApiOperation("添加小组成员,需要token")
    @Transactional(rollbackFor = Exception.class)
    public Result addMember(@Validated @RequestBody SysUserTeam sysUserTeam) {

        sysUserTeam.setCreateTime(new Date());

        SysUserTeam userInfo = sysUserTeamService.getOne(new QueryWrapper<SysUserTeam>().eq("user_id", sysUserTeam.getUserId()));

        if (ObjectUtil.isNotNull(userInfo)) {
            return Result.fail("该用户已经是小组成员了");
        } else {
            boolean flag = sysUserTeamService.save(sysUserTeam);

            return flag ? Result.success("添加成功") : Result.fail("添加失败");
        }
    }


    @PutMapping("/member/update")
    @ApiOperation("修改小组成员,需要token")
    @Transactional(rollbackFor = Exception.class)
    public Result updateMember(@Validated @RequestBody SysUserTeam sysUserTeam) {

        sysUserTeam.setUpdateTime(new Date());

        boolean flag = sysUserTeamService.updateById(sysUserTeam);

        return flag ? Result.success("修改成功") : Result.fail("修改失败");
    }


    @DeleteMapping("/member/delete/{id}")
    @ApiOperation("移除小组成员,需要token")
    @Transactional(rollbackFor = Exception.class)
    public Result deleteMember(@Validated @RequestBody SysUserTeam sysUserTeam) {

        sysUserTeam.setUpdateTime(new Date());

        boolean flag = sysUserTeamService.removeById(sysUserTeam);

        return flag ? Result.success("移除成功") : Result.fail("移除失败");
    }

}
