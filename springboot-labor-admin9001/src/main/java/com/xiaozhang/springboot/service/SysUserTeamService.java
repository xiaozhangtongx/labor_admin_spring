package com.xiaozhang.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaozhang.springboot.domain.SysUserTeam;

import java.util.List;

/**
 * <p>
 * 用户小组表 服务类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-05-11
 */
public interface SysUserTeamService extends IService<SysUserTeam> {

    /**
     * 移除小组成员
     *
     * @param singletonList
     * @return
     */
    boolean deleteByIds(List<String> singletonList);
}
