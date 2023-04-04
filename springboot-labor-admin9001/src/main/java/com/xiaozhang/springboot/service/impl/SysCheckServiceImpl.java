package com.xiaozhang.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaozhang.springboot.domain.SysCheck;
import com.xiaozhang.springboot.mapper.SysCheckMapper;
import com.xiaozhang.springboot.service.SysCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
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
public class SysCheckServiceImpl extends ServiceImpl<SysCheckMapper, SysCheck> implements SysCheckService {

    @Autowired(required = false)
    SysCheckMapper sysCheckMapper;

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

}
