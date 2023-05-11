package com.xiaozhang.springboot.service;

import com.xiaozhang.springboot.domain.SysDept;
import com.xiaozhang.springboot.domain.SysUserDept;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户部门表 服务类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-05-11
 */
public interface SysUserDeptService extends IService<SysUserDept> {

    /**
     * 获取部门信息
     *
     * @param id
     * @return
     */
    SysDept getUserDeptInfo(String id);

    /**
     * 获取部门信息
     *
     * @param deptId
     * @return
     */
    List<SysUserDept> getDeptInfoList(String deptId);

    /**
     * 删除用户部门信息
     *
     * @param id
     * @return
     */
    boolean removeByDeptId(String id);
}
