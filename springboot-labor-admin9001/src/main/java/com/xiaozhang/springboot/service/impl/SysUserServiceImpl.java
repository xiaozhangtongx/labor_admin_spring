package com.xiaozhang.springboot.service.impl;

import com.xiaozhang.springboot.domain.SysUser;
import com.xiaozhang.springboot.mapper.SysUserMapper;
import com.xiaozhang.springboot.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-03-19
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Override
    public SysUser getByUserName(String username) {
        return null;
    }

    @Override
    public String getUserAuthorityInfo(String userId) {
        return null;
    }
}
