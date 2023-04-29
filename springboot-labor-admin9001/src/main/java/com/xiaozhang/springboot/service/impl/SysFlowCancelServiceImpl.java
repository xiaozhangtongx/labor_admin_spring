package com.xiaozhang.springboot.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaozhang.springboot.domain.SysFlowCancel;
import com.xiaozhang.springboot.domain.SysFlowLeave;
import com.xiaozhang.springboot.domain.SysUser;
import com.xiaozhang.springboot.mapper.SysFlowCancelMapper;
import com.xiaozhang.springboot.service.SysFlowCancelService;
import com.xiaozhang.springboot.service.SysFlowLeaveService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class SysFlowCancelServiceImpl extends ServiceImpl<SysFlowCancelMapper, SysFlowCancel> implements SysFlowCancelService {

    @Autowired
    SysFlowCancelService sysFlowCancelService;

    @Autowired
    SysFlowLeaveService sysFlowLeaveService;

    @Override
    public boolean updateStatus(Integer approvalResult, String applicationId) {

        SysFlowCancel flowCancelById = getById(applicationId);
        flowCancelById.setStatus(approvalResult);
        flowCancelById.setUpdateTime(new Date());

        return sysFlowCancelService.updateById(flowCancelById);
    }

    @Override
    public SysFlowCancel getCancelInfoById(String id) {

        SysFlowCancel cancelInfoById = getById(id);

        if (ObjectUtil.isNotNull(cancelInfoById)) {
            SysFlowLeave leaveInfoById = sysFlowLeaveService.getLeaveInfoById(cancelInfoById.getLeaveId());
            cancelInfoById.setLeaveInfo(leaveInfoById);
            log.info("--------------------销假-------------" + cancelInfoById.getId());
        }

        return cancelInfoById;
    }
}
