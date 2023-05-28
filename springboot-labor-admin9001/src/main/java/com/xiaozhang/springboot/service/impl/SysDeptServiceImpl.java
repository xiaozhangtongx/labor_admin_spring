package com.xiaozhang.springboot.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaozhang.springboot.domain.SysDept;
import com.xiaozhang.springboot.mapper.SysDeptMapper;
import com.xiaozhang.springboot.service.SysDeptService;
import com.xiaozhang.springboot.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-05-11
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    @Autowired
    SysUserService sysUserService;

    @Override
    public SysDept getInfoById(String id) {
        SysDept sysDeptInfo = getById(id);
        if (ObjectUtil.isNotNull(sysDeptInfo)) {
            sysDeptInfo.setLeader(sysUserService.getInfoById(sysDeptInfo.getLeaderId()));
        }
        return sysDeptInfo;
    }

    @Override
    public boolean addDept(SysDept sysDept) {

        sysDept.setCreateTime(new Date());

        return save(sysDept);
    }

    @Override
    public boolean editDept(SysDept sysDept) {
        return updateById(sysDept);
    }
}
