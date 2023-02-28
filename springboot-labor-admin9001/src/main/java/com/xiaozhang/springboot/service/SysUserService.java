package com.xiaozhang.springboot.service;

import com.xiaozhang.springboot.domain.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-02-27
 */
public interface SysUserService extends IService<SysUser> {

    String getUserAuthorityInfo(String userId);

    SysUser getByUserName(String username);

}
