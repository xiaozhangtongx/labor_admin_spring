package com.xiaozhang.springboot.controller;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaozhang.springboot.common.lang.Result;
import com.xiaozhang.springboot.domain.SysMenu;
import com.xiaozhang.springboot.domain.SysUser;
import com.xiaozhang.springboot.service.SysMenuService;
import com.xiaozhang.springboot.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
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
@Transactional
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


    @GetMapping("/info/{id}")
    @ApiOperation("获取菜单信息(通过id),需要token")
    public Result info(@PathVariable(name = "id") Long id) {
        return Result.success(200, "菜单信息获取成功", sysMenuService.getById(id), "");
    }

    @GetMapping("/list")
    @ApiOperation("获取菜单列表,需要token")
    public Result list() {

        List<SysMenu> menus = sysMenuService.list2tree();

        return Result.success(200, "菜单列表获取成功", menus, "");
    }

    @PostMapping("/add")
    @ApiOperation("创建菜单,需要token")
    public Result add(@Validated @RequestBody SysMenu sysMenu) {

        SysMenu sysMenuByPath = sysMenuService.getOne(new QueryWrapper<SysMenu>().eq("menu_path", sysMenu.getMenuPath()));
        SysMenu sysMenuByName = sysMenuService.getOne(new QueryWrapper<SysMenu>().eq("menu_name", sysMenu.getMenuName()));

        if (ObjectUtil.isNotNull(sysMenuByPath) || ObjectUtil.isNotNull(sysMenuByName)) {

            return Result.fail("该路径/名称已经被使用了");
        }

        sysMenu.setCreateTime(new Date());
        sysMenuService.save(sysMenu);

        return Result.success(200, "添加成功", sysMenu, "");
    }

    @PutMapping("/update")
    @ApiOperation("更新菜单,需要token")
    public Result update(@Validated @RequestBody SysMenu sysMenu) {

        sysMenu.setUpdateTime(new Date());
        boolean flag = sysMenuService.updateById(sysMenu);

        return flag ? Result.success(200, "修改成功", sysMenu, "") : Result.fail("修改失败");
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除菜单,需要token")
    public Result delete(@PathVariable("id") String id) {

        int count = sysMenuService.count(new QueryWrapper<SysMenu>().eq("parent_id", id));
        if (count > 0) {
            return Result.fail("请先删除子菜单");
        }

        Boolean flag = sysMenuService.deleteById(id);

        return flag ? Result.success("删除成功") : Result.fail("删除失败");
    }
}
