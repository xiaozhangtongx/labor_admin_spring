package com.xiaozhang.springboot.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaozhang.springboot.common.lang.Result;
import com.xiaozhang.springboot.domain.*;
import com.xiaozhang.springboot.service.SysExamService;
import com.xiaozhang.springboot.utils.PageUtils;
import com.xiaozhang.springboot.utils.SchedulerUtils;
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
 * 考试表 前端控制器
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-05-02
 */
@RestController
@RequestMapping("/sys-exam")
@Api(tags = "试卷接口")
public class SysExamController {

    @Autowired
    SysExamService sysExamService;

    @Autowired
    PageUtils pageUtils;


    @GetMapping("/list")
    @ApiOperation("获取试卷列表,需要token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "请求页数", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "请求页大小", required = false, dataType = "Integer", paramType = "query")
    })
    public Result list(String title, Integer status, String creator) {

        sysExamService.updateExamPaperList(new Date());

        Page<SysExam> pageData = sysExamService.page(pageUtils.getPage(), new QueryWrapper<SysExam>()
                .like("title", title == null ? "" : title).like("status", status == null ? "" : status)
                .like("creator", creator == null ? "" : creator).orderByDesc("start_time"));

        return Result.success(200, "试卷列表获取成功", pageData, "");
    }


    @GetMapping("/info/{id}")
    @ApiOperation("获取试卷数据,需要token")
    public Result getQuestionInfo(@PathVariable(name = "id") String id) {

        SysExam sysExamPaper = sysExamService.getInfoById(id);

        return Result.success(200, "试卷获取成功", sysExamPaper, "");
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除试卷,需要token")
    @Transactional(rollbackFor = Exception.class)
    public Result delete(@PathVariable("id") String id) {

        Boolean flag = sysExamService.deleteById(id);

        return flag ? Result.success("删除成功") : Result.fail("删除失败");
    }

    @PostMapping("/add")
    @ApiOperation("创建试卷,需要token")
    @Transactional(rollbackFor = Exception.class)
    public Result add(@Validated @RequestBody SysExam sysExam) {

        sysExam.setCreateTime(new Date());

        boolean flag = sysExamService.addExam(sysExam);

        return flag ? Result.success("添加成功") : Result.fail("添加失败");
    }

    @PutMapping("/update")
    @ApiOperation("更新试卷,需要token")
    @Transactional(rollbackFor = Exception.class)
    public Result update(@Validated @RequestBody SysExam sysExam) {

        boolean flag = sysExamService.editExam(sysExam);

        return flag ? Result.success("修改成功") : Result.fail("修改失败");
    }

    @GetMapping("/admin/edit")
    @ApiOperation("手动刷新状态")
    public Result editExamStatus() {

        sysExamService.updateExamPaperList(new Date());

        return Result.success("刷新成功");
    }


}
