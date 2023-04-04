package com.xiaozhang.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaozhang.springboot.domain.SysCheck;

import java.sql.Time;
import java.util.List;

/**
 * <p>
 * 出勤表 服务类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-04-02
 */
public interface SysCheckService extends IService<SysCheck> {

    /**
     * 获取用户今天的打卡信息
     *
     * @param userId
     * @return
     */
    List<SysCheck> getCheckInfoToday(String userId);
}
