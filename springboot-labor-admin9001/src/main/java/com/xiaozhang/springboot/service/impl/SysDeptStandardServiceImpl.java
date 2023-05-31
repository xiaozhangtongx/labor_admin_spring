package com.xiaozhang.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaozhang.springboot.domain.SysDeptStandard;
import com.xiaozhang.springboot.mapper.SysDeptStandardMapper;
import com.xiaozhang.springboot.service.SysDeptStandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.awt.SystemColor.info;

/**
 * <p>
 * 部门标准表 服务实现类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-04-04
 */
@Service
public class SysDeptStandardServiceImpl extends ServiceImpl<SysDeptStandardMapper, SysDeptStandard> implements SysDeptStandardService {

    @Autowired(required = false)
    SysDeptStandardMapper sysDeptStandardMapper;

    @Override
    public SysDeptStandard getRuleById(String userId) {

//        SysDeptStandard sysDeptStandard = sysDeptStandardMapper.selectOne(new QueryWrapper<SysDeptStandard>().eq("dept_id", deptId));
        String dept = sysDeptStandardMapper.getDept(userId);
        System.out.println("-------------->部门ID"+dept);
        SysDeptStandard standard = sysDeptStandardMapper.getStandard(dept);
        System.out.println("-------------->部门标准"+standard);
        return standard;

    }
}
