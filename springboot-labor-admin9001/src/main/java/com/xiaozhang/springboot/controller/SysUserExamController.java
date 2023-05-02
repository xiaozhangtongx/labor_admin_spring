package com.xiaozhang.springboot.controller;


import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.xiaozhang.springboot.common.lang.Const;
import com.xiaozhang.springboot.common.lang.Result;
import com.xiaozhang.springboot.domain.SysExam;
import com.xiaozhang.springboot.domain.SysUser;
import com.xiaozhang.springboot.domain.SysUserExam;
import com.xiaozhang.springboot.service.SysExamService;
import com.xiaozhang.springboot.service.SysUserExamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.Date;

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

        SysUserExam sysUserExamRes = sysUserExamService.addExamAnswer(sysUserExam);

        return ObjectUtil.isNotNull(sysUserExamRes) ? success(200, "提交成功", sysUserExamRes, "") : Result.fail("提交失败，请稍后在试！");

    }
}
