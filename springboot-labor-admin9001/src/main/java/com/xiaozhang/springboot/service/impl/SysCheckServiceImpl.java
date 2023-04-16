package com.xiaozhang.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaozhang.springboot.domain.SysCheck;
import com.xiaozhang.springboot.domain.SysUser;
import com.xiaozhang.springboot.mapper.SysCheckMapper;
import com.xiaozhang.springboot.mapper.SysUserMapper;
import com.xiaozhang.springboot.service.SysCheckService;
import com.xiaozhang.springboot.utils.MathUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 出勤表 服务实现类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-04-02
 */
@Service
@Slf4j
public class SysCheckServiceImpl extends ServiceImpl<SysCheckMapper, SysCheck> implements SysCheckService {

    @Autowired(required = false)
    SysCheckMapper sysCheckMapper;

    @Autowired(required = false)
    SysUserMapper sysUserMapper;

    @Autowired
    MathUtils mathUtils;


    @Override
    public List<SysCheck> getCheckInfoToday(String userId, Date now) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(now);

        QueryWrapper<SysCheck> queryWrapper = new QueryWrapper<>();
        queryWrapper.apply("DATE_FORMAT(create_time, '%Y-%m-%d') = {0}", dateStr).eq("user_id", userId);

        return sysCheckMapper.selectList(queryWrapper);
    }

    @Override
    public boolean copySysUser() {

        List<Object> userIdList = sysUserMapper.selectObjs(Wrappers.<SysUser>lambdaQuery().select(SysUser::getId));
        List<SysCheck> sysChecks = new ArrayList<>();

        for (Object id : userIdList) {
            List<SysCheck> checkInfoToday = getCheckInfoToday((String) id, new Date());

            // 判断时候已经有记录了
            if (checkInfoToday.isEmpty()) {
                SysCheck sysCheck = new SysCheck();
                sysCheck.setCreateTime(new Date());
                sysCheck.setUserId((String) id);
                sysCheck.setDes("未打卡");

                sysChecks.add(sysCheck);
            }
        }

        return saveOrUpdateBatch(sysChecks);
    }

    @Override
    public Boolean setCheckInfo(String userId, Date startTime, Date endTime, String des, Integer status) {
        Double duration = mathUtils.getDuration(startTime, endTime);
        SysCheck sysCheck = new SysCheck();

        List<SysCheck> checkInfoToday = getCheckInfoToday(userId, startTime);

        if (checkInfoToday.size() != 0) {
            sysCheck.setId(checkInfoToday.get(0).getId());
        }

        sysCheck.setUserId(userId);
        sysCheck.setCreateTime(startTime);
        sysCheck.setWorkTime(duration);
        sysCheck.setDes(des);
        sysCheck.setStatus(status);
        return saveOrUpdate(sysCheck);
    }

}
