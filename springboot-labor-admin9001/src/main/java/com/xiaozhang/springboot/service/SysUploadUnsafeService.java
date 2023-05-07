package com.xiaozhang.springboot.service;

import com.xiaozhang.springboot.domain.SysUploadUnsafe;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 不安全行为上传 服务类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-04-21
 */
public interface SysUploadUnsafeService extends IService<SysUploadUnsafe> {

    boolean saveUrls(SysUploadUnsafe sysUploadUnsafe, List<String> urls);
}
