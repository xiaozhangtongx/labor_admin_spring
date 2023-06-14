package com.xiaozhang.springboot.domain;

import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 工时补办申请表
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-04-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysFlowWorktime对象", description = "工时补办申请表")
public class SysFlowWorktime implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "申请人ID")
    @NotBlank(message = "申请人不能为空")
    private String userId;

    @ApiModelProperty(value = "工时日期")
    @NotNull(message = "工时日期不能为空")
    private Date workDate;

    @ApiModelProperty(value = "工时时长（单位：小时）")
    @NotNull(message = "工时时长不能为空")
    private Double workDuration;

    @ApiModelProperty(value = "补办原因")
    @NotBlank(message = "补办原因不能为空")
    private String reason;

    @ApiModelProperty(value = "补办状态（2-待审批/0-已批准/1-已拒绝）")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "主管id")
    @NotBlank(message = "审批人不能为空")
    private String leaderId;

    @TableField(exist = false)
    @ApiModelProperty(value = "申请人信息")
    private SysUser proposer;

    @TableField(exist = false)
    private SysUser leader;
}
