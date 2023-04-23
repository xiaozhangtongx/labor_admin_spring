package com.xiaozhang.springboot.controller;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaozhang.springboot.common.lang.Result;
import com.xiaozhang.springboot.domain.RoleInfoView;
import com.xiaozhang.springboot.domain.SysRole;
import com.xiaozhang.springboot.domain.SysRoleMenu;
import com.xiaozhang.springboot.domain.SysUserRole;
import com.xiaozhang.springboot.mapper.SysRoleMapper;
import com.xiaozhang.springboot.service.SysMenuService;
import com.xiaozhang.springboot.service.SysRoleMenuService;
import com.xiaozhang.springboot.service.SysRoleService;
import com.xiaozhang.springboot.service.SysUserRoleService;
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

import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
@Api(tags = "角色接口")
@RequestMapping("/sys-role")
@Slf4j
public class SysRoleController {

    @Autowired
    PageUtils pageUtil;

    @Autowired
    SysRoleService sysRoleService;

    @Autowired
    SysRoleMenuService sysRoleMenuService;

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
    public Result list(@RequestParam String roleName) {

        Page<SysRole> pageData = sysRoleService.page(pageUtil.getPage(),
                new QueryWrapper<SysRole>()
                        .like(StrUtil.isNotBlank(roleName), "role_name", roleName)
        );

        pageData.getRecords().forEach(role -> {
            role.setMenus(sysRoleMenuService.getMenuList(role.getId()));
        });

        return Result.success(200, "用户列表获取成功", pageData, "");
    }

    @GetMapping("/allList")
    @ApiOperation("获取所有的角色列表，通过视图,需要token")
    public Result allList(@RequestParam String roleName) {

        List<RoleInfoView> roleInfoViewList = sysRoleService.selectRoleInfoViewList(roleName);

        return Result.success(200, "角色列表获取成功", roleInfoViewList, "");
    }


    @PostMapping("/add")
    @Transactional(rollbackFor = Exception.class)
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

    @PostMapping("/perm/{roleId}")
    @ApiOperation("分配权限,需要token")
    public Result updatePerm(@PathVariable("roleId") String roleId, @RequestBody String[] menuIds) {

        boolean b = sysRoleMenuService.updateMenu(roleId, menuIds);

        return b ? Result.success("修改成功") : Result.fail("修改失败");
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
