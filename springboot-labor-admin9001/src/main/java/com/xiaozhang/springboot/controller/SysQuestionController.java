package com.xiaozhang.springboot.controller;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaozhang.springboot.common.lang.Result;
import com.xiaozhang.springboot.domain.SysMenu;
import com.xiaozhang.springboot.domain.SysQuestion;
import com.xiaozhang.springboot.domain.SysQuestionItem;
import com.xiaozhang.springboot.service.SysQuestionItemService;
import com.xiaozhang.springboot.service.SysQuestionService;
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
 * 考试问题表 前端控制器
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-05-01
 */
@RestController
@RequestMapping("/sys-question")
@Api(tags = "试题接口")
public class SysQuestionController {

    @Autowired
    SysQuestionService sysQuestionService;

    @Autowired
    SysQuestionItemService sysQuestionItemService;

    @Autowired
    PageUtils pageUtil;

    @GetMapping("/info/{id}")
    @ApiOperation("获取题目内容,需要token")
    public Result getQuestionInfo(@PathVariable(name = "id") String id) {

        SysQuestion sysQuestion = sysQuestionService.getInfoById(id);

        return Result.success(200, "问题信息获取成功", sysQuestion, "");
    }

    @GetMapping("/list")
    @ApiOperation("获取题目列表,需要token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "请求页数", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "请求页大小", required = false, dataType = "Integer", paramType = "query")
    })
    public Result getQuestionList(@RequestParam String questionId, Long type, String tag) {

        Page<SysQuestion> pageData = sysQuestionService.page(pageUtil.getPage(), new QueryWrapper<SysQuestion>()
                .like("id", questionId).like("type", type == null ? "" : type).like("tag", tag));

        pageData.getRecords().forEach(sysQuestion -> {

            List<SysQuestionItem> sysQuestionItems = sysQuestionItemService.getQuestionItem(sysQuestion.getId());

            if (sysQuestionItems.size() != 0) {
                sysQuestion.setSysQuestionItemList(sysQuestionItems);
            }

        });

        return Result.success(200, "问题列表获取成功", pageData, "");
    }

    @PostMapping("/add")
    @ApiOperation("创建问题,需要token")
    @Transactional(rollbackFor = Exception.class)
    public Result add(@Validated @RequestBody SysQuestion sysQuestion) {

        sysQuestion.setCreateTime(new Date());

        boolean flag = sysQuestionService.addQuestion(sysQuestion);

        return flag ? Result.success("添加成功") : Result.fail("添加失败");
    }


    @PutMapping("/update")
    @ApiOperation("更新题目,需要token")
    @Transactional(rollbackFor = Exception.class)
    public Result update(@Validated @RequestBody SysQuestion sysQuestion) {

        boolean flag = sysQuestionService.editQuestion(sysQuestion);

        return flag ? Result.success("修改成功") : Result.fail("修改失败");
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除题目,需要token")
    @Transactional(rollbackFor = Exception.class)
    public Result delete(@PathVariable("id") String id) {

        Boolean flag = sysQuestionService.deleteById(id);

        return flag ? Result.success("删除成功") : Result.fail("删除失败");
    }

}
