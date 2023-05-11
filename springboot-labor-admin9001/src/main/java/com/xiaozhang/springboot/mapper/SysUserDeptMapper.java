package com.xiaozhang.springboot.mapper;

import com.xiaozhang.springboot.domain.SysDept;
import com.xiaozhang.springboot.domain.SysUser;
import com.xiaozhang.springboot.domain.SysUserDept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 用户部门表 Mapper 接口
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-05-11
 */
@Mapper
public interface SysUserDeptMapper extends BaseMapper<SysUserDept> {

    /**
     * 获取用户部门信息
     *
     * @param userId
     * @return
     */
    SysDept getUserDeptInfo(String userId);

    /**
     * 获取部门详情
     *
     * @param deptId
     * @return
     */
    List<SysUser> getDeptInfo(String deptId);
}
