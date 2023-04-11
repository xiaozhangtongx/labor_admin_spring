package com.xiaozhang.springboot.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

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
    private String approverId;

    @ApiModelProperty(value = "审批时间")
    private Date approvalTime;

    @ApiModelProperty(value = "审批结果（同意/拒绝）")
    private String approvalResult;

    @ApiModelProperty(value = "审批意见")
    private String reason;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "审批类型")
    private String applicationType;

}