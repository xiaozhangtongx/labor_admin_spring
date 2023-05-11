package com.xiaozhang.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.xiaozhang.springboot.domain.SysDept;
import com.xiaozhang.springboot.domain.SysQuestionItem;
import com.xiaozhang.springboot.domain.SysUser;
import com.xiaozhang.springboot.domain.SysUserDept;
import com.xiaozhang.springboot.mapper.SysUserDeptMapper;
import com.xiaozhang.springboot.mapper.SysUserMapper;
import com.xiaozhang.springboot.service.SysDeptService;
import com.xiaozhang.springboot.service.SysRoleService;
import com.xiaozhang.springboot.service.SysUserDeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaozhang.springboot.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户部门表 服务实现类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-05-11
 */
@Service
public class SysUserDeptServiceImpl extends ServiceImpl<SysUserDeptMapper, SysUserDept> implements SysUserDeptService {

    @Autowired(required = false)
    SysUserDeptMapper sysUserDeptMapper;

    @Autowired
    SysUserService sysUserService;

    @Autowired
    SysRoleService sysRoleService;

    @Override
    public SysDept getUserDeptInfo(String id) {
        return sysUserDeptMapper.getUserDeptInfo(id);
    }

    @Override
    public List<SysUserDept> getDeptInfoList(String deptId) {

        List<SysUserDept> sysUserDeptList = sysUserDeptMapper.selectList(new QueryWrapper<SysUserDept>()
                .eq("dept_id", deptId).orderByDesc("create_time"));

        sysUserDeptList.forEach(sysUserDept -> {
            sysUserDept.setSysUser(sysUserService.getInfoById(sysUserDept.getUserId()));
            sysUserDept.setSysRole(sysRoleService.getById(sysUserDept.getRoleId()));
        });


        return sysUserDeptList;
    }

    @Override
    public boolean removeByDeptId(String id) {
        int question_id = sysUserDeptMapper.delete(new QueryWrapper<SysUserDept>()
                .eq("dept_id", id));

        return question_id > 0;
    }
}
