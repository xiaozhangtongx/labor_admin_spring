package com.xiaozhang.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaozhang.springboot.domain.SysCheck;
import com.xiaozhang.springboot.domain.SysUser;
import com.xiaozhang.springboot.mapper.SysCheckMapper;
import com.xiaozhang.springboot.mapper.SysUserMapper;
import com.xiaozhang.springboot.service.SysCheckService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
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
    SysCheckService sysCheckService;


    @Override
    public List<SysCheck> getCheckInfoToday(String userId) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = LocalDateTime.of(now.toLocalDate(), LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(now.toLocalDate(), LocalTime.MAX);

        QueryWrapper<SysCheck> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId)
                .between("create_time", start, end);

        List<SysCheck> sysChecks = sysCheckMapper.selectList(queryWrapper);

        return sysChecks;
    }

    @Override
    public boolean copySysUser() {

        List<Object> userIdList = sysUserMapper.selectObjs(Wrappers.<SysUser>lambdaQuery().select(SysUser::getId));

        List<SysCheck> sysChecks = new ArrayList<>();

        for (Object id : userIdList) {
            SysCheck sysCheck = new SysCheck();
            sysCheck.setUserId((String) id);
            sysChecks.add(sysCheck);
        }

        boolean b = sysCheckService.saveOrUpdateBatch(sysChecks);

        return b;
    }

}
