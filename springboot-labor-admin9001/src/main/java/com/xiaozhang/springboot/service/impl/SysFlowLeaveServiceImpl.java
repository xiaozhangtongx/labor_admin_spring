package com.xiaozhang.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaozhang.springboot.domain.SysFlowLeave;
import com.xiaozhang.springboot.mapper.SysFlowLeaveMapper;
import com.xiaozhang.springboot.service.SysFlowLeaveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 请假表 服务实现类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-04-04
 */
@Service
@Slf4j
public class SysFlowLeaveServiceImpl extends ServiceImpl<SysFlowLeaveMapper, SysFlowLeave> implements SysFlowLeaveService {


    @Autowired
    SysFlowLeaveService sysFlowLeaveService;

    @Override
    public boolean updateStatus(Integer approvalResult, String applicationId) {

        SysFlowLeave flowLeaveById = sysFlowLeaveService.getById(applicationId);
        flowLeaveById.setStatus(approvalResult);
        flowLeaveById.setUpdateTime(new Date());

        return sysFlowLeaveService.updateById(flowLeaveById);
    }
}
