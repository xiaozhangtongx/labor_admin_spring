package com.xiaozhang.springboot.controller;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaozhang.springboot.common.lang.Result;
import com.xiaozhang.springboot.domain.*;
import com.xiaozhang.springboot.service.SysDeptService;
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
 * 部门表 前端控制器
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-05-11
 */
@RestController
@Api(tags = "部门接口")
@RequestMapping("/sys-dept")
public class SysDeptController {

    @Autowired
    SysDeptService sysDeptService;

    @Autowired
    PageUtils pageUtils;

    @GetMapping("/list")
    @ApiOperation("获取部门列表,需要token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "请求页数", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "请求页大小", required = false, dataType = "Integer", paramType = "query")
    })
    public Result getQuestionList(String deptName, String parentId) {

        Page<SysDept> pageData = sysDeptService.page(pageUtils.getPage(), new QueryWrapper<SysDept>()
                .like("dept_name", deptName == null ? "" : deptName)
                .like("parent_id", parentId == null ? "" : parentId)
                .like("id", parentId == null ? "" : parentId)
                .orderByDesc("create_time"));

        return Result.success(200, "部门列表获取成功", pageData, "");
    }

    @GetMapping("/info/{id}")
    @ApiOperation("获取部门数据,需要token")
    public Result getDeptInfo(@PathVariable(name = "id") String id) {

        SysDept sysDept = sysDeptService.getInfoById(id);

        return Result.success(200, "部门信息获取成功", sysDept, "");
    }


    @PostMapping("/add")
    @ApiOperation("创建部门,需要token")
    @Transactional(rollbackFor = Exception.class)
    public Result add(@Validated @RequestBody SysDept sysDept) {

        sysDept.setCreateTime(new Date());

        boolean flag = sysDeptService.addDept(sysDept);

        return flag ? Result.success("添加成功") : Result.fail("添加失败");
    }


    @PutMapping("/update")
    @ApiOperation("更新部门信息,需要token")
    @Transactional(rollbackFor = Exception.class)
    public Result update(@Validated @RequestBody SysDept sysDept) {

        sysDept.setUpdateTime(new Date());

        boolean flag = sysDeptService.editDept(sysDept);

        return flag ? Result.success("修改成功") : Result.fail("修改失败");
    }

}
