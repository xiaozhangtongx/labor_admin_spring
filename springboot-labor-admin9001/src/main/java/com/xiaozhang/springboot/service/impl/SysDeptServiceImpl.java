package com.xiaozhang.springboot.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.xiaozhang.springboot.domain.SysDept;
import com.xiaozhang.springboot.domain.SysQuestionItem;
import com.xiaozhang.springboot.domain.SysUserDept;
import com.xiaozhang.springboot.mapper.SysDeptMapper;
import com.xiaozhang.springboot.service.SysDeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaozhang.springboot.service.SysUserDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    SysUserDeptService sysUserDeptService;

    @Override
    public SysDept getInfoById(String id) {
        SysDept sysDeptInfo = getById(id);
        if (ObjectUtil.isNotNull(sysDeptInfo)) {
            List<SysUserDept> sysUserDeptList = sysUserDeptService.getDeptInfoList(id);

            sysDeptInfo.setSysUserDeptList(sysUserDeptList);
        }
        return sysDeptInfo;
    }

    @Override
    public boolean addDept(SysDept sysDept) {

        sysDept.setCreateTime(new Date());

        boolean save = save(sysDept);

        return save;
    }

    @Override
    public boolean editDept(SysDept sysDept) {
        return updateById(sysDept);
    }
}
