package com.xiaozhang.springboot.controller;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaozhang.springboot.common.lang.Result;
import com.xiaozhang.springboot.domain.*;
import com.xiaozhang.springboot.service.SysNoticeService;
import com.xiaozhang.springboot.service.SysUserService;
import com.xiaozhang.springboot.service.impl.SysNoticeServiceImpl;
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
import java.util.List;

/**
 * <p>
 * 公告 前端控制器
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-05-05
 */
@RestController
@Api(tags = "公告接口")
@RequestMapping("/sys-notice")
public class SysNoticeController {

    @Autowired
    SysNoticeService sysNoticeService;

    @Autowired
    SysUserService sysUserService;

    @Autowired
    PageUtils pageUtils;

    @GetMapping("/info/{id}")
    @ApiOperation("获取题目内容,需要token")
    public Result getNoticeInfo(@PathVariable(name = "id") String id) {

        SysNotice sysNoticeInfoById = sysNoticeService.getById(id);

        return Result.success(200, "公告信息获取成功", sysNoticeInfoById, "");
    }

    @PostMapping("/add")
    @ApiOperation("创建通知,需要token")
    @Transactional(rollbackFor = Exception.class)
    public Result add(@Validated @RequestBody SysNotice sysNotice) {

        sysNotice.setCreateTime(new Date());

        boolean flag = sysNoticeService.save(sysNotice);

        return flag ? Result.success("发布成功") : Result.fail("发布失败");
    }

    @GetMapping("/list")
    @ApiOperation("获取公告列表,需要token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "请求页数", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "请求页大小", required = false, dataType = "Integer", paramType = "query")
    })
    public Result getQuestionList(String title, String creatorId, String deptId, String teamId, Integer type) {

        Page<SysNotice> pageData = sysNoticeService.page(pageUtils.getPage(), new QueryWrapper<SysNotice>()
                .like("title", title == null ? "" : title)
                .like("creator_id", creatorId == null ? "" : creatorId)
                .like("dept_id", deptId == null ? "" : deptId)
                .like("team_id", teamId == null ? "" : teamId)
                .like("type", type == null ? "" : type)
                .orderByDesc("create_time"));

        pageData.getRecords().forEach(sysNotice -> {

            SysUser userInfoById = sysUserService.getById(sysNotice.getCreatorId());

            if (ObjectUtil.isNotNull(userInfoById)) {
                userInfoById.setPassword("");
                sysNotice.setCreator(userInfoById);
            }
        });

        return Result.success(200, "公告列表获取成功", pageData, "");
    }

    @PutMapping("/update")
    @ApiOperation("更新公告,需要token")
    @Transactional(rollbackFor = Exception.class)
    public Result update(@Validated @RequestBody SysNotice sysNotice) {

        sysNotice.setUpdateTime(new Date());

        boolean flag = sysNoticeService.updateById(sysNotice);

        return flag ? Result.success("修改成功") : Result.fail("修改失败");
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除公告,需要token")
    @Transactional(rollbackFor = Exception.class)
    public Result delete(@PathVariable("id") String id) {

        Boolean flag = sysNoticeService.deleteById(id);

        return flag ? Result.success("删除成功") : Result.fail("删除失败");
    }

}

