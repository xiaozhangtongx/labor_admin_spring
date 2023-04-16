package com.xiaozhang.springboot.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@ApiModel(value="SysFlowWorktime对象", description="工时补办申请表")
public class SysFlowWorktime implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "申请人ID")
    private String userId;

    @ApiModelProperty(value = "工时日期")
    private Date workDate;

    @ApiModelProperty(value = "工时时长（单位：小时）")
    private Double workDuration;

    @ApiModelProperty(value = "补办原因")
    private String reason;

    @ApiModelProperty(value = "补办状态（2-待审批/0-已批准/1-已拒绝）")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "申请类别（0-直接申请/1-帮他人申请）")
    private Integer workType;

    @ApiModelProperty(value = "帮他人申请的列表（id用逗号隔开）")
    private String userlist;

    @ApiModelProperty(value = "主管id")
    private String leaderId;


}
