package com.xiaozhang.springboot.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaozhang.springboot.common.lang.Result;
import com.xiaozhang.springboot.domain.SysUserStudy;
import com.xiaozhang.springboot.service.SysUserStudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户学习情况表 前端控制器
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-05-28
 */
@RestController
@RequestMapping("/sys-user-study")
public class SysUserStudyController {

    @Autowired
    SysUserStudyService sysUserStudyService;

    @PostMapping("/add")
    public Result addInfo(@RequestBody SysUserStudy sysUserStudy)
    {
        sysUserStudy.setStatus(1L);
        List<SysUserStudy> studies = sysUserStudyService.list(new QueryWrapper<SysUserStudy>().eq("user_id", sysUserStudy.getUserId()).eq("study_id", sysUserStudy.getStudyId()));
        if(studies.isEmpty())
        {
            sysUserStudy.setCreateTime(new Date());
            sysUserStudyService.save(sysUserStudy);
        }
        else
        {
            sysUserStudy.setUpdateTime(new Date());
            sysUserStudyService.update(sysUserStudy,new QueryWrapper<SysUserStudy>().eq("user_id", sysUserStudy.getUserId()).eq("study_id", sysUserStudy.getStudyId()));
        }
        return Result.success("记录成功");
    }
}
