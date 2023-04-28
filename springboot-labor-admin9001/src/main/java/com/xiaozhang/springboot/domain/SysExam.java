package com.xiaozhang.springboot.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 考试表
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-04-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SysExam对象", description="考试表")
public class SysExam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "考试开始时间")
    private Date startTime;

    @ApiModelProperty(value = "考试结束时间")
    private Date endTime;

    @ApiModelProperty(value = "考试时长")
    private Double duration;

    @ApiModelProperty(value = "考试标题")
    private String title;

    @ApiModelProperty(value = "创建者")
    private String creator;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @TableField(exist = false)
    private List<SysQuestion> questions;
}
