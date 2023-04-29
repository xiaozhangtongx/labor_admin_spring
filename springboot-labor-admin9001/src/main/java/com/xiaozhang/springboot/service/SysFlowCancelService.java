package com.xiaozhang.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaozhang.springboot.domain.SysFlowCancel;

/**
 * <p>
 * 销假表 服务类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-04-11
 */
public interface SysFlowCancelService extends IService<SysFlowCancel> {

    /**
     * 修改状态
     *
     * @param approvalResult
     * @param applicationId
     * @return
     */
    boolean updateStatus(Integer approvalResult, String applicationId);

    /**
     * 通过id获取销假表单信息
     *
     * @param id
     * @return
     */
    SysFlowCancel getCancelInfoById(String id);
}
