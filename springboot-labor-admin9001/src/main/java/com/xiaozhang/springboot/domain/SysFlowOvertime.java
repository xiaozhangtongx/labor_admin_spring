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

/**
 * <p>
 * 加班申请表
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-04-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysFlowOvertime对象", description = "加班申请表")
public class SysFlowOvertime implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "申请人ID")
    private String userId;

    @ApiModelProperty(value = "加班类型")
    private String overtimeType;

    @ApiModelProperty(value = "开始时间")
    @NotNull(message = "开始时间不能为空")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    @NotNull(message = "结束时间不能为空")
    private Date endTime;

    @ApiModelProperty(value = "加班时长（单位：小时）")
    private Double duration;

    @ApiModelProperty(value = "加班原因")
    @NotBlank(message = "原因不能为空")
    private String reason;

    @ApiModelProperty(value = "加班状态（待审批/已批准/已拒绝）")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "主管id")
    @NotBlank(message = "审核人不能为空")
    private String leaderId;

    @TableField(exist = false)
    @ApiModelProperty(value = "申请人信息")
    private SysUser proposer;

    @TableField(exist = false)
    private SysUser leader;
}
