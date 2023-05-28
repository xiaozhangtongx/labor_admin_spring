package com.xiaozhang.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaozhang.springboot.domain.SysTeam;

/**
 * <p>
 * 小组表 服务类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-05-11
 */
public interface SysTeamService extends IService<SysTeam> {

    /**
     * 通过id获取小组的信息
     *
     * @param id
     * @return 小组基本信息
     */
    SysTeam getInfoById(String id);

    /**
     * 添加小组
     *
     * @param sysTeam
     * @return
     */
    boolean addTeam(SysTeam sysTeam);

    /**
     * 修改小组的信息
     *
     * @param sysTeam
     * @return
     */
    boolean editTeam(SysTeam sysTeam);
}
