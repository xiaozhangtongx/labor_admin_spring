package com.xiaozhang.springboot.controller;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaozhang.springboot.common.lang.Result;
import com.xiaozhang.springboot.domain.SysDept;
import com.xiaozhang.springboot.domain.SysDeptStandard;
import com.xiaozhang.springboot.service.SysDeptService;
import com.xiaozhang.springboot.service.SysDeptStandardService;
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

import java.util.Date;

/**
 * <p>
 * 部门标准表 前端控制器
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-04-04
 */
@RestController
@RequestMapping("/sys-dept/standard")
@Api(tags = "部门接口")
@Slf4j
public class SysDeptStandardController {

    @Autowired
    SysDeptStandardService sysDeptStandardService;

    @Autowired
    SysDeptService sysDeptService;

    @Autowired
    PageUtils pageUtils;

    @GetMapping("/info/{id}")
    @ApiOperation("部门打卡标准，需要token")
    public Result getDeptStandard(@PathVariable(name = "id") String deptId) {

        SysDeptStandard sysDeptStandard = sysDeptStandardService.getRuleById(deptId);

        if (ObjectUtil.isNull(sysDeptStandard)) {
            return Result.fail("标准获取失败，无相关部门");
        } else {
            return Result.success(200, "打卡标准获取成功", sysDeptStandard, "");
        }
    }

    @GetMapping("/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "请求页数", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "请求页大小", required = false, dataType = "Integer", paramType = "query")
    })
    @ApiOperation("部门打卡标准列表，需要token")
    public Result getDeptStandardList(String deptId) {

        Page<SysDeptStandard> pageData = sysDeptStandardService.page(pageUtils.getPage(), new QueryWrapper<SysDeptStandard>()
                .like("dept_id", deptId == null ? "" : deptId)
                .orderByDesc("create_time"));

        pageData.getRecords().forEach(sysDeptStandard -> {

            SysDept sysDept = sysDeptService.getInfoById(sysDeptStandard.getDeptId());

            if (ObjectUtil.isNotNull(sysDept)) {
                sysDeptStandard.setSysDept(sysDept);
            }

        });

        return Result.success(200, "部门打卡标准列表获取成功", pageData, "");
    }


    @PostMapping("/add")
    @ApiOperation("创建部门打卡标准,需要token")
    @Transactional(rollbackFor = Exception.class)
    public Result add(@Validated @RequestBody SysDeptStandard sysDeptStandard) {

        sysDeptStandard.setCreateTime(new Date());

        SysDeptStandard deptStandardInfo = sysDeptStandardService.getOne(new QueryWrapper<SysDeptStandard>().eq("dept_id", sysDeptStandard.getDeptId()));

        if (ObjectUtil.isNotNull(deptStandardInfo)) {
            return Result.fail("改部门已存在打卡标准");
        } else {
            boolean flag = sysDeptStandardService.save(sysDeptStandard);
            return flag ? Result.success("添加成功") : Result.fail("添加失败");
        }
    }


    @PutMapping("/update")
    @ApiOperation("更新部门打卡标准,需要token")
    @Transactional(rollbackFor = Exception.class)
    public Result update(@Validated @RequestBody SysDeptStandard sysDeptStandard) {

        sysDeptStandard.setUpdateTime(new Date());

        boolean flag = sysDeptStandardService.updateById(sysDeptStandard);

        return flag ? Result.success("修改成功") : Result.fail("修改失败");
    }
}