package com.xiaozhang.springboot.domain;

import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户部门表
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-05-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysUserDept对象", description = "用户部门表")
public class SysUserDept implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "部门id")
    private String deptId;

    @ApiModelProperty(value = "职位id")
    private String roleId;

    @ApiModelProperty(value = "进入时间")
    private Date createTime;

    @ApiModelProperty(value = "状态（0-禁言，1-正常）")
    private Integer status;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @TableField(exist = false)
    @ApiModelProperty(value = "用户所在部门")
    private SysDept sysDept;

    @TableField(exist = false)
    @ApiModelProperty(value = "用户所在部门的角色")
    private SysRole sysRole;

    @TableField(exist = false)
    @ApiModelProperty(value = "用户信息")
    private SysUser sysUser;
}
