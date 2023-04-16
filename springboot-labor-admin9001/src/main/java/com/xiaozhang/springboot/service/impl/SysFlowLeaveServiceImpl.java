package com.xiaozhang.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaozhang.springboot.domain.SysCheck;
import com.xiaozhang.springboot.domain.SysFlowLeave;
import com.xiaozhang.springboot.mapper.SysFlowLeaveMapper;
import com.xiaozhang.springboot.service.SysCheckService;
import com.xiaozhang.springboot.service.SysFlowLeaveService;
import com.xiaozhang.springboot.utils.MathUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
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

    @Autowired
    MathUtils mathUtils;

    @Override
    public boolean updateStatus(Integer approvalResult, String applicationId) {

        SysFlowLeave flowLeaveById = sysFlowLeaveService.getById(applicationId);

        // 审批通过更新打卡信息,同时插入空表数据
        if (approvalResult == 0) {
            Date startTime = flowLeaveById.getStartTime();
            Date endTime = flowLeaveById.getEndTime();
            String userId = flowLeaveById.getUserId();
            List<SysCheck> sysChecks = new ArrayList<>();

            // 获得中间间隔的日期
            Date[] datesBetween = mathUtils.getDatesBetween(startTime, endTime);

            for (Date createTime : datesBetween) {
                SysCheck sysCheck = new SysCheck();

                List<SysCheck> checkInfoToday = sysCheckService.getCheckInfoToday(userId, createTime);

                if (checkInfoToday.size() != 0) {
                    sysCheck.setId(checkInfoToday.get(0).getId());
                }

                sysCheck.setUserId(userId);
                sysCheck.setDes("请假");
                sysCheck.setCreateTime(createTime);
                sysCheck.setStatus(5);

                sysChecks.add(sysCheck);
            }

            sysCheckService.saveOrUpdateBatch(sysChecks);
        }

        flowLeaveById.setUpdateTime(new Date());
        flowLeaveById.setStatus(approvalResult);

        return sysFlowLeaveService.updateById(flowLeaveById);
    }
}
