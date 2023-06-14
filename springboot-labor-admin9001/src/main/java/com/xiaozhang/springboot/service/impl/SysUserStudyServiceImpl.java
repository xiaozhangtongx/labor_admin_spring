package com.xiaozhang.springboot.service.impl;

import cn.hutool.core.date.DateTime;
import com.xiaozhang.springboot.domain.SysStudyData;
import com.xiaozhang.springboot.domain.SysUserStudy;
import com.xiaozhang.springboot.mapper.SysUserStudyMapper;
import com.xiaozhang.springboot.service.SysUserStudyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户学习情况表 服务实现类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-05-28
 */
@Service
public class SysUserStudyServiceImpl extends ServiceImpl<SysUserStudyMapper, SysUserStudy> implements SysUserStudyService {

    @Autowired
    SysUserStudyMapper sysUserStudyMapper;

    @Override
    public List<SysStudyData> getCount(String userId, DateTime start, DateTime end) {
        return sysUserStudyMapper.selectVarietyCount(userId,start,end);
    }
}
