package com.xiaozhang.springboot.service;

import com.xiaozhang.springboot.domain.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-03-19
 */
public interface SysUserService extends IService<SysUser> {

    SysUser getByUserName(String username);

    String getUserAuthorityInfo(String userId);
}
