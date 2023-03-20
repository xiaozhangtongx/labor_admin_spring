package com.xiaozhang.springboot.service.impl;

import com.xiaozhang.springboot.domain.SysRole;
import com.xiaozhang.springboot.mapper.SysRoleMapper;
import com.xiaozhang.springboot.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-03-19
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

}
