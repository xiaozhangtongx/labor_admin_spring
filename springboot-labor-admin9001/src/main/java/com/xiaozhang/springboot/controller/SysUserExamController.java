package com.xiaozhang.springboot.controller;


import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaozhang.springboot.common.lang.Const;
import com.xiaozhang.springboot.common.lang.Result;
import com.xiaozhang.springboot.domain.SysExam;
import com.xiaozhang.springboot.domain.SysUser;
import com.xiaozhang.springboot.domain.SysUserExam;
import com.xiaozhang.springboot.service.SysExamService;
import com.xiaozhang.springboot.service.SysUserExamService;
import com.xiaozhang.springboot.service.SysUserService;
import com.xiaozhang.springboot.utils.PageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xiaozhang.springboot.common.lang.Result.success;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-05-02
 */
@RestController
@RequestMapping("/sys-exam/user")
@Api(tags = "用户答题接口")
public class SysUserExamController {

    @Autowired
    SysUserExamService sysUserExamService;

    @Autowired
    SysExamService sysExamService;

    @Autowired
    SysUserService sysUserService;

    @Autowired
    PageUtils pageUtil;

    @PostMapping("/add")
    @ApiOperation("用户开始答题,需要token")
    @Transactional(rollbackFor = Exception.class)
    public Result add(@Validated @RequestBody SysUserExam sysUserExam) {

        String uuid = IdUtil.simpleUUID();
        sysUserExam.setId(uuid);

        boolean flag = sysUserExamService.enterExam(sysUserExam);

        if (flag) {
            return success(200,
                    "请开始答题",
                    MapUtil.builder()
                            .put("userExamId", uuid)
                            .build(),
                    "");
        }

        return Result.fail("暂时未在答题时间，请稍后再试");

    }

    @PostMapping("/submit")
    @ApiOperation("用户答题提交,需要token")
    @Transactional(rollbackFor = Exception.class)
    public Result submit(@Validated @RequestBody SysUserExam sysUserExam) {

        System.out.println("------------->"+sysUserExam);
        System.out.println("------------>"+sysUserExam.getUserAnswerList());
        sysUserExam.setStartTime(new Date());
        SysUserExam sysUserExamRes = sysUserExamService.addExamAnswer(sysUserExam);

        return ObjectUtil.isNotNull(sysUserExamRes) ? Result.success(200, "提交成功", sysUserExamRes, "") : Result.fail("提交失败，请稍后在试！");

    }

    @GetMapping("/listEmpty")
    @ApiOperation(("获取用户未参加的考试数"))
    @Transactional(rollbackFor = Exception.class)
    public Result listEmpty(@RequestParam String phoneNumber)
    {
        SysUser user = sysUserService.getByPhoneNum(phoneNumber);
//        //参加过的考试
//        QueryWrapper<SysUserExam> wrapper = new QueryWrapper<SysUserExam>().select("DISTINCT exam_id").eq("user_id",user.getId());
//        Integer join= sysUserExamService.count(wrapper);
//        wrapper.eq("res",0);
//        //未过关的考试
//        Integer fail = sysUserExamService.count(wrapper);

        //过关的考试
        QueryWrapper<SysUserExam> wrapper = new QueryWrapper<SysUserExam>().select("DISTINCT exam_id").eq("user_id",user.getId()).eq("res",1);
        Integer pass= sysUserExamService.count(wrapper);
        //试卷总数
        Integer allExam = sysExamService.count(new QueryWrapper<SysExam>().select("id"));
//        Integer emptySize=allExam-join+fail;
        Integer emptySize = allExam - pass;
        return ObjectUtil.isNotNull((emptySize)) ? Result.success(200, "获取成功",emptySize, "") : Result.fail("提交失败，请稍后在试！");
    }

    @GetMapping("/listHistory")
    @ApiOperation(("获取用户未参加的考试列表"))
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "请求页数", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "请求页大小", required = false, dataType = "Integer", paramType = "query")
    })
    @Transactional(rollbackFor = Exception.class)
    public Result listHistory(@RequestParam String userId)
    {
        //参加过的考试
        Page<SysUserExam> records = sysUserExamService.page(pageUtil.getPage(),new QueryWrapper<SysUserExam>().eq("user_id",userId).orderByDesc("end_time"));
        records.getRecords().forEach(one -> {
            SysExam examInfo = sysExamService.getById(one.getExamId());
            one.setExamInfo(examInfo);
        });
        return Result.success(200,"获取成功",records,"");
    }
}
