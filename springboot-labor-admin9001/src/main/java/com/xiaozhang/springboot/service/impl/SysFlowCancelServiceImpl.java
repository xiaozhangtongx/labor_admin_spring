package com.xiaozhang.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaozhang.springboot.domain.SysFlowCancel;
import com.xiaozhang.springboot.mapper.SysFlowCancelMapper;
import com.xiaozhang.springboot.service.SysFlowCancelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 销假表 服务实现类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-04-11
 */
@Service
public class SysFlowCancelServiceImpl extends ServiceImpl<SysFlowCancelMapper, SysFlowCancel> implements SysFlowCancelService {

    @Autowired
    SysFlowCancelService sysFlowCancelService;

    @Override
    public boolean updateStatus(Integer approvalResult, String applicationId) {

        SysFlowCancel flowCancelById = sysFlowCancelService.getById(applicationId);
        flowCancelById.setStatus(approvalResult);
        flowCancelById.setUpdateTime(new Date());

        return sysFlowCancelService.updateById(flowCancelById);
    }
}
