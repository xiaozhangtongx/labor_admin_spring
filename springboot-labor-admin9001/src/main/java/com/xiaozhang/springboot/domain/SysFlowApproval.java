package com.xiaozhang.springboot.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 审批表
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-04-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysFlowApproval对象", description = "审批表")
public class SysFlowApproval implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "请假/销假/加班等记录ID")
    private String applicationId;

    @ApiModelProperty(value = "审批人ID")
    @NotBlank(message = "审批人不能为空")
    private String approverId;

    @ApiModelProperty(value = "审批时间")
    private Date approvalTime;

    @ApiModelProperty(value = "审批结果（0-同意/1-拒绝）")
    private Integer approvalResult;

    @ApiModelProperty(value = "审批意见")
    @NotBlank(message = "意见不能为空")
    private String reason;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "审批类型（0-请假，1-销假，2-加班，3-补办）")
    private String applicationType;

    @ApiModelProperty(value = "处理状态（0-已处理/1-未处理）")
    private Integer status;

    @TableField(exist = false)
    @ApiModelProperty(value = "请假表单详细信息")
    private SysFlowLeave flowLeaveInfo;

    @TableField(exist = false)
    @ApiModelProperty(value = "补办工时申请表单详细信息")
    private SysFlowWorktime flowWorkTimeInfo;

    @TableField(exist = false)
    @ApiModelProperty(value = "消假表单详细信息")
    private SysFlowCancel flowCancelInfo;

    @TableField(exist = false)
    @ApiModelProperty(value = "加班申请表单详细信息")
    private SysFlowOvertime flowOverTimeInfo;
}