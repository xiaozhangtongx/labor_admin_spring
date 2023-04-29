package com.xiaozhang.springboot.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.xiaozhang.springboot.domain.SysCheck;
import com.xiaozhang.springboot.domain.SysFlowLeave;
import com.xiaozhang.springboot.domain.SysFlowOvertime;
import com.xiaozhang.springboot.domain.SysUser;
import com.xiaozhang.springboot.mapper.SysFlowOvertimeMapper;
import com.xiaozhang.springboot.service.SysCheckService;
import com.xiaozhang.springboot.service.SysFlowOvertimeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaozhang.springboot.service.SysUserService;
import com.xiaozhang.springboot.utils.MathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
    SysCheckService sysCheckService;

    @Autowired
    MathUtils mathUtils;

    @Autowired
    SysUserService sysUserService;

    @Override
    public boolean updateStatus(Integer approvalResult, String applicationId) {
        SysFlowOvertime flowOvertimeById = getById(applicationId);

        boolean flag = true;

        // 审批通过更新打卡信息
        if (approvalResult == 0) {
            flag = sysCheckService.setCheckInfo(flowOvertimeById.getUserId(), flowOvertimeById.getStartTime(), flowOvertimeById.getEndTime(), "加班", 6);
        }

        flowOvertimeById.setStatus(approvalResult);
        flowOvertimeById.setUpdateTime(new Date());

        return flag && updateById(flowOvertimeById);
    }

    @Override
    public SysFlowOvertime getOverTimeInfoById(String id) {

        SysFlowOvertime overTimeInfoById = getById(id);

        if (ObjectUtil.isNotNull(overTimeInfoById)) {
            SysUser userInfoById = sysUserService.getById(overTimeInfoById.getUserId());
            userInfoById.setPassword(null);
            overTimeInfoById.setProposer(userInfoById);
        }

        return overTimeInfoById;
    }
}
