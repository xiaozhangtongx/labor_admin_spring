package com.xiaozhang.springboot.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 小组表
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-05-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysTeam对象", description = "小组表")
public class SysTeam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "部门id")
    @NotBlank(message = "上级部门不能为空")
    private String deptId;

    @ApiModelProperty(value = "组长id")
    @NotBlank(message = "组长不能为空")
    private String teamLeaderId;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "描述")
    private String des;

    @ApiModelProperty(value = "小组名称")
    @NotBlank(message = "小组名称不能为空")
    private String teamName;

    @ApiModelProperty(value = "小组状态（0-停工，1-正常）")
    private Integer status;

    @TableField(exist = false)
    @ApiModelProperty(value = "小组组长")
    private SysUser teamLeader;

    @ApiModelProperty(value = "是否解散")
    @TableLogic
    @JsonIgnore
    private String isDeleted;
}
