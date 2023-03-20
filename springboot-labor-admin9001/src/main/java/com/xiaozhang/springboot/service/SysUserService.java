package com.xiaozhang.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaozhang.springboot.domain.SysUser;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-03-19
 */
public interface SysUserService extends IService<SysUser> {
    String getUserAuthorityInfo(String userId);

    SysUser getByPhoneNum(String phoneNum);

    SysUser getInfoByPhoneNum(String phoneNum);
}
