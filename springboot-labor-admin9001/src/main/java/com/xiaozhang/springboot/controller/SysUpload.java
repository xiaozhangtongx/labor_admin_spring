package com.xiaozhang.springboot.controller;

import com.xiaozhang.springboot.common.lang.Result;
import com.xiaozhang.springboot.domain.SysUploadUnsafe;
import com.xiaozhang.springboot.utils.OssUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: xiaozhangtx
 * @ClassName: SysUpload
 * @Description: TODO 文件上传
 * @date: 2023/5/5 10:11
 * @Version: 1.0
 */
@RestController
@RequestMapping("/sys-upload")
@Slf4j
public class SysUpload {
    @Autowired
    private OssUtils ossUtils;

    @PostMapping("/images")
    public Result uploadImages(@RequestBody List<String> images) {

        List<String> urls = ossUtils.uploadImages(images);

        log.info("-------------urls------" + urls.get(0));

        return Result.success(200, "获取成功", urls, "");

    }
}
