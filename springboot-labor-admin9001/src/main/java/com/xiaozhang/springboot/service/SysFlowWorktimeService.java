package com.xiaozhang.springboot.service;

import com.xiaozhang.springboot.domain.SysFlowWorktime;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 工时补办申请表 服务类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-04-16
 */
public interface SysFlowWorktimeService extends IService<SysFlowWorktime> {

    /**
     * 根据审批结果设置补办状态
     *  @param approvalResult
     * @param applicationId
     * @return
     */
    boolean updateStatus(Integer approvalResult, String applicationId);
}
