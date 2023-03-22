package com.xiaozhang.springboot.controller;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaozhang.springboot.common.lang.Result;
import com.xiaozhang.springboot.domain.SysRole;
import com.xiaozhang.springboot.service.SysRoleService;
import com.xiaozhang.springboot.utils.PageUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-03-19
 */
@RestController
@Transactional
@RequestMapping("/sys-role")
public class SysRoleController {

    @Autowired
    PageUtils pageUtil;

    @Autowired
    SysRoleService sysRoleService;

    @GetMapping("/info/{roleName}")
    @ApiOperation("获取角色信息(通过角色名),需要token")
    public Result getRoleInfoByName(@PathVariable String roleName) {

        SysRole sysRole = sysRoleService.getInfoByName(roleName);

        return Result.success(200, "角色信息获取成功", sysRole, "");
    }

    @GetMapping("/list")
    @ApiOperation("获取角色列表,需要token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "请求页数", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "请求页大小", required = false, dataType = "Integer", paramType = "query")
    })
    public Result list(String roleName) {

        Page<SysRole> pageData = sysRoleService.page(pageUtil.getPage(),
                new QueryWrapper<SysRole>()
                        .like(StrUtil.isNotBlank(roleName), "role_name", roleName)
        );

        return Result.success(200, "用户列表获取成功", pageData, "");
    }

    @PostMapping("/add")
    @ApiOperation("添加角色,需要token")
    public Result add(@Validated @RequestBody SysRole sysRole) {

        SysRole roleInfoByName = sysRoleService.getOne(new QueryWrapper<SysRole>().eq("role_name", sysRole.getRoleName()));
        SysRole roleInfoByCode = sysRoleService.getOne(new QueryWrapper<SysRole>().eq("role_code", sysRole.getRoleCode()));

        if (ObjectUtil.isNotNull(roleInfoByName) || ObjectUtil.isNotNull(roleInfoByCode)) {

            return Result.fail("该角色/编码已经被使用了");
        } else {
            sysRole.setCreateTime(new Date());
            sysRoleService.save(sysRole);

            return Result.success(200, "添加成功", sysRole, "");
        }
    }

    @PutMapping("/update")
    @ApiOperation("更新角色信息,需要token")
    public Result update(@Validated @RequestBody SysRole sysRole) {

        sysRole.setUpdateTime(new Date());
        boolean b = sysRoleService.updateById(sysRole);

        return b ? Result.success(200, "修改成功", sysRole, "") : Result.fail("修改失败");
    }


    @PostMapping("/delete")
    @ApiOperation("批量删除角色,需要token")
    public Result delete(@RequestBody String[] ids) {

        Boolean flag = sysRoleService.deleteByIds(Arrays.asList(ids));

        return flag ? Result.success("删除成功") : Result.fail("删除失败");
    }


    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除角色,需要token")
    public Result delete(@PathVariable String id) {

        Boolean flag = sysRoleService.deleteByIds(Arrays.asList(id));

        return flag ? Result.success("删除成功") : Result.fail("删除失败");
    }
}
