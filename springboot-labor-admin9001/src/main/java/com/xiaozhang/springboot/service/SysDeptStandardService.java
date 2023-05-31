package com.xiaozhang.springboot.service;

import com.xiaozhang.springboot.domain.SysDeptStandard;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 部门标准表 服务类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-04-04
 */
public interface SysDeptStandardService extends IService<SysDeptStandard> {

    /**
     * 根据部门id获取打卡标准
     * @param userId
     * @return
     */
    SysDeptStandard getRuleById(String userId);
}
