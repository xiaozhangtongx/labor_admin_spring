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
 * 试卷问题表
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-05-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysExamQuestion对象", description = "试卷问题表")
public class SysExamQuestion implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "试卷id")
    private String examId;

    @ApiModelProperty(value = "问题id")
    private String questionId;

    @ApiModelProperty(value = "序号")
    private Integer itemOrder;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "分数")
    private Double questionScore;

    @ApiModelProperty(value = "试题类型")
    private Long questionType;

}
