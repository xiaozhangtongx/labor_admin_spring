package com.xiaozhang.springboot.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.xiaozhang.springboot.common.lang.Result;
import com.xiaozhang.springboot.domain.SysUserExam;
import com.xiaozhang.springboot.service.SysUserExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-04-24
 */
@RestController
@RequestMapping("/sys-user-exam")
public class SysUserExamController {

    @Autowired
    SysUserExamService sysUserExamService;

    @PostMapping("/add")
    public Result addUserExam(@RequestBody SysUserExam sysUserExam)
    {
        double score=sysUserExamService.getScore(sysUserExam);
        //保存答题数据
        sysUserExam.setGardes(score);
        Boolean result=sysUserExamService.save(sysUserExam);
        return result? Result.success("提交成功"):Result.fail("提交失败");
    }
}
