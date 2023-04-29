package com.xiaozhang.springboot.service;

import com.xiaozhang.springboot.domain.SysFlowOvertime;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 加班申请表 服务类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-04-12
 */
public interface SysFlowOvertimeService extends IService<SysFlowOvertime> {

    /**
     * 设置加班状态
     *
     * @param approvalResult
     * @param applicationId
     * @return
     */
    boolean updateStatus(Integer approvalResult, String applicationId);

    /**
     * 根据id获取用户加班表单信息
     *
     * @param id
     * @return
     */
    SysFlowOvertime getOverTimeInfoById(String id);
}
