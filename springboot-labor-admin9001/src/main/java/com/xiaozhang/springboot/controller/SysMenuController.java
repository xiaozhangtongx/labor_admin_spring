package com.xiaozhang.springboot.controller;


import com.xiaozhang.springboot.common.lang.Result;
import com.xiaozhang.springboot.domain.SysMenu;
import com.xiaozhang.springboot.domain.SysUser;
import com.xiaozhang.springboot.service.SysMenuService;
import com.xiaozhang.springboot.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-03-19
 */
@RestController
@Api(tags = "菜单接口")
@RequestMapping("/sys-menu")
public class SysMenuController {

    @Autowired
    SysUserService sysUserService;

    @Autowired
    SysMenuService sysMenuService;

    @GetMapping("/nav")
    @ApiOperation("获取菜单,需要token")
    public Result getMenuList(Principal principal) {
        SysUser sysUser = sysUserService.getByPhoneNum(principal.getName());
        List<SysMenu> navs = sysMenuService.getCurrentUserNavList(sysUser.getId());

        return Result.success(200, "菜单获取成功", navs, "");
    }
}
