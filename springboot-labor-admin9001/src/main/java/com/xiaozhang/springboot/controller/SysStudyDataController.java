package com.xiaozhang.springboot.controller;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaozhang.springboot.common.lang.Result;
import com.xiaozhang.springboot.domain.SysStudyData;
import com.xiaozhang.springboot.domain.SysUser;
import com.xiaozhang.springboot.service.SysStudyDataService;
import com.xiaozhang.springboot.service.SysStudyDataService;
import com.xiaozhang.springboot.service.SysUserService;
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
 * 前端控制器
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-05-07
 */
@RestController
@Api(tags = "学习资料接口")
@RequestMapping("/sys-study")
public class SysStudyDataController {
    @Autowired
    SysStudyDataService sysStudyDataService;

    @Autowired
    SysUserService sysUserService;

    @Autowired
    PageUtils pageUtils;

    @GetMapping("/info/{id}")
    @ApiOperation("获取资料内容,需要token")
    public Result getStudyDataInfo(@PathVariable(name = "id") String id) {

        SysStudyData sysStudyDataInfoById = sysStudyDataService.getById(id);

        return Result.success(200, "学习资料信息获取成功", sysStudyDataInfoById, "");
    }

    @PostMapping("/add")
    @ApiOperation("添加学习资料,需要token")
    @Transactional(rollbackFor = Exception.class)
    public Result add(@Validated @RequestBody SysStudyData sysStudyData) {

        sysStudyData.setCreateTime(new Date());

        boolean flag = sysStudyDataService.save(sysStudyData);

        return flag ? Result.success("添加成功") : Result.fail("添加失败");
    }

    @GetMapping("/list")
    @ApiOperation("获取学习资料列表,需要token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "请求页数", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "请求页大小", required = false, dataType = "Integer", paramType = "query")
    })
    public Result getQuestionList(String title, String creatorId, String content, Integer type) {

        Page<SysStudyData> pageData = sysStudyDataService.page(pageUtils.getPage(), new QueryWrapper<SysStudyData>()
                .like("title", title == null ? "" : title)
                .like("creator_id", creatorId == null ? "" : creatorId)
                .like("content", content == null ? "" : content)
                .like("type", type == null ? "" : type)
                .orderByDesc("create_time"));

        pageData.getRecords().forEach(sysStudyData -> {

            SysUser userInfoById = sysUserService.getById(sysStudyData.getCreatorId());

            if (ObjectUtil.isNotNull(userInfoById)) {
                userInfoById.setPassword("");
                sysStudyData.setCreator(userInfoById);
            }
        });

        return Result.success(200, "学习资料列表获取成功", pageData, "");
    }

    @PutMapping("/update")
    @ApiOperation("更新学习资料,需要token")
    @Transactional(rollbackFor = Exception.class)
    public Result update(@Validated @RequestBody SysStudyData sysStudyData) {

        sysStudyData.setUpdateTime(new Date());

        boolean flag = sysStudyDataService.updateById(sysStudyData);

        return flag ? Result.success("修改成功") : Result.fail("修改失败");
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除学习资料,需要token")
    @Transactional(rollbackFor = Exception.class)
    public Result delete(@PathVariable("id") String id) {

        Boolean flag = sysStudyDataService.deleteById(id);

        return flag ? Result.success("删除成功") : Result.fail("删除失败");
    }
}
