package com.xiaozhang.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaozhang.springboot.domain.SysUserTeam;
import com.xiaozhang.springboot.mapper.SysUserTeamMapper;
import com.xiaozhang.springboot.service.SysUserTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户小组表 服务实现类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-05-11
 */
@Service
public class SysUserTeamServiceImpl extends ServiceImpl<SysUserTeamMapper, SysUserTeam> implements SysUserTeamService {

    @Autowired(required = false)
    SysUserTeamMapper sysUserTeamMapper;

    @Override
    public boolean deleteByIds(List<String> idList) {

        return removeByIds(idList);
    }
}
