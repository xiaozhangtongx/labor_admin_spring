package com.xiaozhang.springboot.service;

import com.xiaozhang.springboot.domain.SysDept;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 部门表 服务类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-05-11
 */
public interface SysDeptService extends IService<SysDept> {

    /**
     * 获取部门信息
     *
     * @param id
     * @return
     */
    SysDept getInfoById(String id);

    /**
     * 创建部门
     *
     * @param sysDept
     * @return
     */
    boolean addQuestion(SysDept sysDept);

    /**
     * 修改部门
     *
     * @param sysDept
     * @return
     */
    boolean editDept(SysDept sysDept);
}
