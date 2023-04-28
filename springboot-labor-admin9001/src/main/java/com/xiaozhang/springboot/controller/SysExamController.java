package com.xiaozhang.springboot.controller;


import com.xiaozhang.springboot.common.lang.Result;
import com.xiaozhang.springboot.domain.SysExam;
import com.xiaozhang.springboot.service.SysExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 考试表 前端控制器
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-04-24
 */
@RestController
@RequestMapping("/sys-exam")
public class SysExamController {

    @Autowired
    SysExamService sysExamService;

    @GetMapping("/list")
    public Result getList()
    {
        List<SysExam> exams=sysExamService.getList();
        return Result.success(200,"获取成功",exams,"");
    }


}
