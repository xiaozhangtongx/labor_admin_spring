package com.xiaozhang.springboot.controller;

import com.xiaozhang.springboot.common.lang.Result;
import com.xiaozhang.springboot.utils.OssUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author: xiaozhangtx
 * @ClassName: SysUpload
 * @Description: TODO 文件上传
 * @date: 2023/5/5 10:11
 * @Version: 1.0
 */
@RestController
@Api(tags = "文件上传接口")
@RequestMapping("/sys-upload")
@Slf4j
public class SysUpload {
    @Autowired
    private OssUtils ossUtils;

    @PostMapping("/images")
    @ApiOperation("上传图片")
    public Result uploadImages(@RequestBody List<String> images) {

        List<String> urls = ossUtils.uploadImages(images);

        log.info("-------------urls------" + urls.get(0));

        return Result.success(200, "获取成功", urls, "");

    }

    @PostMapping("/file")
    @ApiOperation("上传文件")
    public Result uploadFiles(@RequestParam("fileName") String fileName,
                              @RequestParam("type") String type,
                              @RequestParam("size") long size,
                              @RequestParam("url") String url,
                              @RequestParam("file") MultipartFile file) throws IOException {

        String fileUrl = ossUtils.uploadFiles(fileName, type, size, url, file);

        return Result.success(200, "文件上传成功", fileUrl, "");

    }

}
