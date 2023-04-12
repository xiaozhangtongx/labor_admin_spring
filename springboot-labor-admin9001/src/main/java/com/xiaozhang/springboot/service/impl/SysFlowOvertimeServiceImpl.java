package com.xiaozhang.springboot.service.impl;

import com.xiaozhang.springboot.domain.SysFlowOvertime;
import com.xiaozhang.springboot.mapper.SysFlowOvertimeMapper;
import com.xiaozhang.springboot.service.SysFlowOvertimeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 加班申请表 服务实现类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-04-12
 */
@Service
public class SysFlowOvertimeServiceImpl extends ServiceImpl<SysFlowOvertimeMapper, SysFlowOvertime> implements SysFlowOvertimeService {

    @Autowired
    SysFlowOvertimeService sysFlowOvertimeService;

    @Override
    public boolean updateStatus(Integer approvalResult, String applicationId) {
        SysFlowOvertime flowOvertimeById = sysFlowOvertimeService.getById(applicationId);
        flowOvertimeById.setStatus(approvalResult);
        flowOvertimeById.setUpdateTime(new Date());

        return sysFlowOvertimeService.updateById(flowOvertimeById);
    }
}
