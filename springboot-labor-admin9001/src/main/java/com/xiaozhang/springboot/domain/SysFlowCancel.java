package com.xiaozhang.springboot.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 销假表
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-04-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysFlowCancel对象", description = "销假表")
public class SysFlowCancel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "请假记录ID")
    private String leaveId;

    @ApiModelProperty(value = "申请人ID")
    private String userId;

    @ApiModelProperty(value = "销假原因")
    private String reason;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "主管id")
    private String leaderId;

    @TableField(exist = false)
    @ApiModelProperty(value = "审批领导")
    private SysUser leader;

    @ApiModelProperty(value = "请假状态（2-待审批/0-已批准/1-已拒绝）")
    private Integer status;
}
