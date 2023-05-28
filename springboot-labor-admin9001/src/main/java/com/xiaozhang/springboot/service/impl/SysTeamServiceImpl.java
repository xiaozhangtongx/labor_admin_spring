package com.xiaozhang.springboot.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaozhang.springboot.domain.SysTeam;
import com.xiaozhang.springboot.domain.SysUser;
import com.xiaozhang.springboot.mapper.SysTeamMapper;
import com.xiaozhang.springboot.service.SysTeamService;
import com.xiaozhang.springboot.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 小组表 服务实现类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-05-11
 */
@Service
public class SysTeamServiceImpl extends ServiceImpl<SysTeamMapper, SysTeam> implements SysTeamService {

    @Autowired
    SysUserService sysUserService;

    @Override
    public SysTeam getInfoById(String id) {

        SysTeam sysTeamInfo = getById(id);

        if (ObjectUtil.isNotNull(sysTeamInfo)) {
            SysUser infoById = sysUserService.getById(sysTeamInfo.getTeamLeaderId());
            infoById.setPassword("");
            sysTeamInfo.setTeamLeader(infoById);
        }

        return sysTeamInfo;
    }

    @Override
    public boolean addTeam(SysTeam sysTeam) {
        return save(sysTeam);
    }

    @Override
    public boolean editTeam(SysTeam sysTeam) {
        return updateById(sysTeam);
    }
}
