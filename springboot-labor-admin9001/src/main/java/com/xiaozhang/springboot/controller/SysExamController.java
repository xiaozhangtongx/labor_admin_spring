package com.xiaozhang.springboot.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaozhang.springboot.common.lang.Result;
import com.xiaozhang.springboot.domain.SysExam;
import com.xiaozhang.springboot.domain.SysQuestion;
import com.xiaozhang.springboot.domain.SysQuestionItem;
import com.xiaozhang.springboot.domain.SysUser;
import com.xiaozhang.springboot.service.SysExamService;
import com.xiaozhang.springboot.utils.PageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Result list(String title) {

        Page<SysExam> pageData = sysExamService.page(pageUtils.getPage(), new QueryWrapper<SysExam>()
                .like("title", title == null ? "" : title).orderByDesc("start_time"));

        return Result.success(200, "试卷列表获取成功", pageData, "");
    }


    @GetMapping("/info/{id}")
    @ApiOperation("获取试卷数据,需要token")
    public Result getQuestionInfo(@PathVariable(name = "id") String id) {

        SysExam sysExamPaper = sysExamService.getInfoById(id);

        return Result.success(200, "试卷获取成功", sysExamPaper, "");
    }

}
