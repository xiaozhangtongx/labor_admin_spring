package com.xiaozhang.springboot.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaozhang.springboot.common.lang.Result;
import com.xiaozhang.springboot.domain.SysExam;
import com.xiaozhang.springboot.domain.SysUploadUnsafe;
import com.xiaozhang.springboot.domain.SysUser;
import com.xiaozhang.springboot.service.SysUploadUnsafeService;
import com.xiaozhang.springboot.service.SysUserService;
import com.xiaozhang.springboot.utils.OssUtils;
import com.xiaozhang.springboot.utils.PageUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 不安全行为上传 前端控制器
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-04-21
 */
@RestController
@RequestMapping("/sys-upload-unsafe")
public class SysUploadUnsafeController {

    @Autowired
    private OssUtils ossUtils;

    @Autowired
    PageUtils pageUtils;

    @Autowired
    private SysUploadUnsafeService sysUploadUnsafeService;

    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/uploadUnsafe")
    public Result uploadUnsafe(@RequestBody SysUploadUnsafe sysUploadUnsafe)
    {
        System.out.println("-------->"+sysUploadUnsafe.getDatas().size());
        List<String> urls = ossUtils.uploadImages(sysUploadUnsafe.getDatas());
        System.out.println("--------->"+urls.size());
        sysUploadUnsafeService.saveUrls(sysUploadUnsafe,urls);
        return Result.success(200,"获取成功",urls,"");
    }

    @GetMapping("/list")
    @ApiOperation("获取安全公告列表,需要token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "请求页数", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "请求页大小", required = false, dataType = "Integer", paramType = "query")
    })
    public Result list(String content) {

        Page<SysUploadUnsafe> pageData = sysUploadUnsafeService.page(pageUtils.getPage(), new QueryWrapper<SysUploadUnsafe>()
//                .like("title", title == null ? "" : title)
                .like("content", content == null ? "" : content).orderByDesc("create_time"));
        pageData.getRecords().forEach(unSafeInfo ->{
            SysUser laborInfo = sysUserService.getById(unSafeInfo.getLaborId());
            SysUser saferInfo = sysUserService.getById(unSafeInfo.getSafetyId());
            saferInfo.setPassword("");
            unSafeInfo.setSafer(saferInfo);
            laborInfo.setPassword("");
            unSafeInfo.setLabor(laborInfo);
        });
        return Result.success(200, "安全公告列表获取成功", pageData, "");
    }


}
