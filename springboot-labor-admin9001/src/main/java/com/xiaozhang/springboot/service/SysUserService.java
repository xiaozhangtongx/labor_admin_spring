package com.xiaozhang.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaozhang.springboot.domain.SysRole;
import com.xiaozhang.springboot.domain.SysUser;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-03-19
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 通过id查找用户权限
     *
     * @param userId
     * @return 用户权限
     */
    String getUserAuthorityInfo(String userId);

    /**
     * 通过手机号查询用户
     *
     * @param phoneNum
     * @return
     */
    SysUser getByPhoneNum(String phoneNum);

    /**
     * 通过手机号查询用户信息
     *
     * @param phoneNum
     * @return
     */
    SysUser getInfoByPhoneNum(String phoneNum);

    /**
     * 获取用户角色
     *
     * @param id
     * @return
     */
    List<SysRole> getUserRoles(String id);

    /**
     * 清除用户权限
     *
     * @param phoneNum
     */
    void clearUserAuthorityInfo(String phoneNum);

    /**
     * 通过id批量删除角色
     *
     * @param asList
     * @return
     */
    Boolean deleteByIds(List<String> asList);

    /**
     * 通过id获取数据
     * @param id
     * @return
     */
    SysUser getInfoById(String id);
}
