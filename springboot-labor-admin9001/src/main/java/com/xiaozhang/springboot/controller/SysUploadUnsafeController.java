package com.xiaozhang.springboot.controller;


import com.xiaozhang.springboot.common.lang.Result;
import com.xiaozhang.springboot.domain.SysUploadUnsafe;
import com.xiaozhang.springboot.utils.OssUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/uploadUnsafe")
    public Result uploadUnsafe(@RequestBody SysUploadUnsafe sysUploadUnsafe)
    {
        List<String> urls = ossUtils.uploadOneFile(sysUploadUnsafe.getDatas());
        return Result.success(200,"获取成功",urls,"");
    }
}
