package com.xiaozhang.springboot.controller;


import cn.hutool.core.map.MapUtil;
import com.xiaozhang.springboot.common.lang.Result;
import com.xiaozhang.springboot.domain.SysUser;
import com.xiaozhang.springboot.service.impl.SysUserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-02-27
 */
@RestController
@Api(tags = "用户接口,需要token")
@RequestMapping("/sys-user")
public class SysUserController {

    @Autowired
    SysUserServiceImpl sysUserService;

    @GetMapping("/userInfo")
    @ApiOperation("获取用户信息")
    public Result getUserInfo(Principal principal) {
        SysUser sysUser = sysUserService.getByUserName(principal.getName());

        return Result.success(200, "用户信息获取成功",
                MapUtil.builder()
                        .put("id", sysUser.getUserId())
                        .put("username", sysUser.getUsername())
                        .put("avatar", sysUser.getAvatar())
                        .put("phoneNum", sysUser.getPhoneNum())
                        .put("status", sysUser.getStatus())
                        .map(),
                ""
        );
    }

}
