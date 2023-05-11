package com.xiaozhang.springboot.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 部门表
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-05-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysDept对象", description = "部门表")
public class SysDept implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "部门id")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "上级部门id")
    private String parentId;

    @ApiModelProperty(value = "部门名称")
    @NotBlank(message = "部门名称不能为空")
    private String deptName;

    @ApiModelProperty(value = "部门层级")
    private Integer level;

    @ApiModelProperty(value = "部门排序")
    private Integer sort;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @TableField(exist = false)
    @ApiModelProperty(value = "部门数据问题")
    private List<SysUserDept> sysUserDeptList;

    @ApiModelProperty(value = "是否删除(0-未删, 1-已删)")
    @TableLogic
    @JsonIgnore
    private Integer isDeleted;


}
