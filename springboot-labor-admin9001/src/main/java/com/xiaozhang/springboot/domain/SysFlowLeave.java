package com.xiaozhang.springboot.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 请假表
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-04-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysFlowLeave对象", description = "请假表")
public class SysFlowLeave implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "申请人ID")
    private String userId;

    @ApiModelProperty(value = "请假类型")
    private String leaveType;

    @ApiModelProperty(value = "开始时间")
    @NotNull(message = "开始时间不能为空")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    @NotNull(message = "结束时间不能为空")
    private Date endTime;

    @ApiModelProperty(value = "请假时长（单位：小时）")
    private Long duration;

    @ApiModelProperty(value = "请假原因")
    @NotBlank(message = "请假原因不能为空")
    private String reason;

    @ApiModelProperty(value = "请假状态（待审批/已批准/已拒绝）")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "主管id")
    private String leaderId;

    @TableField(exist = false)
    @ApiModelProperty(value = "审批领导")
    private SysUser leader;

    @TableField(exist = false)
    @ApiModelProperty(value = "申请人信息")
    private SysUser proposer;
}
