package com.xiaozhang.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaozhang.springboot.domain.SysCheck;
import com.xiaozhang.springboot.domain.SysFlowLeave;
import com.xiaozhang.springboot.mapper.SysFlowLeaveMapper;
import com.xiaozhang.springboot.service.SysCheckService;
import com.xiaozhang.springboot.service.SysFlowLeaveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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

    @Autowired
    SysCheckService sysCheckService;

    @Override
    public boolean updateStatus(Integer approvalResult, String applicationId) {

        SysFlowLeave flowLeaveById = sysFlowLeaveService.getById(applicationId);
        flowLeaveById.setStatus(approvalResult);

        // 审批通过更新打卡信息
        if (approvalResult == 0) {

            Boolean flag = sysCheckService.setCheckInfo(flowLeaveById.getUserId(), flowLeaveById.getCreateTime());


            List<SysCheck> checkInfoToday = sysCheckService.getCheckInfoToday(flowLeaveById.getUserId(), flowLeaveById.getCreateTime());
            SysCheck sysCheck = checkInfoToday.get(0);
            sysCheck.setDes("请假");
            sysCheck.setUpdateTime(new Date());
            sysCheck.setStatus(5);

            sysCheckService.updateById(sysCheck);
        }

        flowLeaveById.setUpdateTime(new Date());

        return sysFlowLeaveService.updateById(flowLeaveById);
    }
}
