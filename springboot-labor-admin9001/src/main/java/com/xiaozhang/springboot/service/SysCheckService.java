package com.xiaozhang.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaozhang.springboot.domain.SysCheck;

import java.util.Date;
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
     * @param now
     * @return
     */
    List<SysCheck> getCheckInfoToday(String userId, Date now);

    /**
     * 批量拷贝用户信息
     *
     * @return
     */
    boolean copySysUser();

    /**
     * 审批通过后修改信息
     *
     * @param userId
     * @param createTime
     * @return
     */
    Boolean setCheckInfo(String userId, Date createTime);
}
