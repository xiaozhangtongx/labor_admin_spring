package com.xiaozhang.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaozhang.springboot.domain.SysFlowLeave;

/**
 * <p>
 * 请假表 服务类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-04-04
 */
public interface SysFlowLeaveService extends IService<SysFlowLeave> {

    /**
     * 设置请假表的状态
     *
     * @param approvalResult
     * @param applicationId
     * @return
     */
    boolean updateStatus(Integer approvalResult, String applicationId);

    /**
     * 通过id获取请假表单所有用户信息
     *
     * @param id
     * @return
     */
    SysFlowLeave getLeaveInfoById(String id);
}
