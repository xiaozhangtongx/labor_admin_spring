package com.xiaozhang.springboot.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.xiaozhang.springboot.domain.SysFlowOvertime;
import com.xiaozhang.springboot.domain.SysFlowWorktime;
import com.xiaozhang.springboot.domain.SysUser;
import com.xiaozhang.springboot.mapper.SysFlowWorktimeMapper;
import com.xiaozhang.springboot.service.SysCheckService;
import com.xiaozhang.springboot.service.SysFlowWorktimeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaozhang.springboot.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 工时补办申请表 服务实现类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-04-16
 */
@Service
public class SysFlowWorktimeServiceImpl extends ServiceImpl<SysFlowWorktimeMapper, SysFlowWorktime> implements SysFlowWorktimeService {

    @Autowired
    SysCheckService sysCheckService;

    @Autowired
    SysUserService sysUserService;

    @Override
    public boolean updateStatus(Integer approvalResult, String applicationId) {
        SysFlowWorktime flowWorkTimeById = getById(applicationId);

        boolean flag = true;

        // 审批通过更新打卡信息
        if (approvalResult == 0) {
            flag = sysCheckService.setCheckInfo(flowWorkTimeById.getUserId(), flowWorkTimeById.getWorkDate(), DateUtil.offsetHour(flowWorkTimeById.getWorkDate(), (int) (flowWorkTimeById.getWorkDuration() / 1)), "补办工时", 7);
        }

        flowWorkTimeById.setStatus(approvalResult);
        flowWorkTimeById.setUpdateTime(new Date());

        return flag && updateById(flowWorkTimeById);
    }

    @Override
    public SysFlowWorktime getWorkTimeInfoById(String id) {
        SysFlowWorktime workTimeInfoById = getById(id);

        if (ObjectUtil.isNotNull(workTimeInfoById)) {
            SysUser userInfoById = sysUserService.getById(workTimeInfoById.getUserId());
            userInfoById.setPassword(null);
            workTimeInfoById.setProposer(userInfoById);
        }

        return workTimeInfoById;
    }
}
