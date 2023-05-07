package com.xiaozhang.springboot.service;

import com.xiaozhang.springboot.domain.SysNotice;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 公告 服务类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-05-05
 */
public interface SysNoticeService extends IService<SysNotice> {

    /**
     * 删除公告
     *
     * @param id
     * @return
     */
    Boolean deleteById(String id);
}
