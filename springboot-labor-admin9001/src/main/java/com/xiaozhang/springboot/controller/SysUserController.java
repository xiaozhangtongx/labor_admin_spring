package com.xiaozhang.springboot.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaozhang.springboot.common.lang.Result;
import com.xiaozhang.springboot.domain.SysUser;
import com.xiaozhang.springboot.service.SysUserService;
import com.xiaozhang.springboot.utils.PageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-03-19
 */
@RestController
@Api(tags = "用户接口")
@RequestMapping("/sys-user")
@Slf4j
public class SysUserController {
    @Autowired
    SysUserService sysUserService;

    @Autowired
    PageUtils pageUtil;

    @GetMapping("/userInfo")
    @ApiOperation("获取用户信息,需要token")
    public Result getUserInfo(Principal principal) {
        SysUser sysUser = sysUserService.getInfoByPhoneNum(principal.getName());

        return Result.success(200, "用户信息获取成功", sysUser, "");
    }

    @GetMapping("/list")
    @ApiOperation("获取用户列表,需要token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "请求页数", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "请求页大小", required = false, dataType = "Integer", paramType = "query")
    })
    public Result list(@RequestParam String username) {
        Page<SysUser> pageData = sysUserService.page(pageUtil.getPage(), new QueryWrapper<SysUser>()
                .like(StrUtil.isNotBlank(username), "username", username));

        pageData.getRecords().forEach(user -> {
            user.setRoles(sysUserService.getUserRoles(user.getId()));
        });

        return Result.success(200, "用户列表获取成功", pageData, "");
    }


}
