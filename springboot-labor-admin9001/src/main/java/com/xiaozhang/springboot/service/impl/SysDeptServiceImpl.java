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
    public boolean addQuestion(SysDept sysDept) {
        String id = IdUtil.simpleUUID();

        List<SysUserDept> sysUserDeptList = new ArrayList();

        for (SysUserDept sysUserDeptItem : sysDept.getSysUserDeptList()) {
            sysUserDeptItem.setDeptId(id);
            sysUserDeptItem.setCreateTime(new Date());
            sysUserDeptList.add(sysUserDeptItem);
        }

        boolean b = sysUserDeptService.saveBatch(sysUserDeptList);

        sysDept.setId(id);
        sysDept.setCreateTime(new Date());

        boolean save = save(sysDept);

        return b && save;
    }

    @Override
    public boolean editDept(SysDept sysDept) {
        // 首先删除问题选项
        boolean deptId = sysUserDeptService.removeByDeptId(sysDept.getId());

        // 然后插入问题子选项
        boolean b = sysUserDeptService.saveBatch(sysDept.getSysUserDeptList());

        // 最后更新
        boolean b1 = updateById(sysDept);

        return deptId && b && b1;
    }
}
