package com.xiaozhang.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaozhang.springboot.domain.RoleInfoView;
import com.xiaozhang.springboot.domain.SysRole;
import com.xiaozhang.springboot.mapper.SysRoleMapper;
import com.xiaozhang.springboot.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-03-19
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired(required = false)
    SysRoleMapper sysRoleMapper;

    @Override
    public SysRole getInfoByName(String roleName) {
        return getOne(new QueryWrapper<SysRole>().eq("role_name", roleName));
    }

    @Override
    public Boolean deleteByIds(List<String> idList) {
        Integer lines = sysRoleMapper.deleteBatchIds(idList);

        return lines != 0;
    }

    @Override
    public List<RoleInfoView> selectRoleInfoViewList(String roleName) {
        return sysRoleMapper.selectUserInfoViewList(roleName);
    }
}
