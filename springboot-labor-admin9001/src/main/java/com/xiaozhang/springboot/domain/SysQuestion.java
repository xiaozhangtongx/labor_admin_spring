package com.xiaozhang.springboot.domain;

import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 考试问题表
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-05-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysQuestion对象", description = "考试问题表")
public class SysQuestion implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "题目")
    @NotBlank(message = "题目不能为空")
    private String title;

    @ApiModelProperty(value = "题目类型（1-选择题，0-为判断题）")
    @NotNull(message = "题目类型不能为空")
    private Long type;

    @ApiModelProperty(value = "分数")
    private Double score;

    @ApiModelProperty(value = "标准答案")
    @NotBlank(message = "标准答案不能为空")
    private String answerId;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "标签")
    @NotNull(message = "题目知识点不能为空")
    private String tag;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建者")
    private String creator;

    @ApiModelProperty(value = "是否删除")
    @TableLogic
    private Integer isDeleted;

    @ApiModelProperty(value = "问题解析")
    private String answerContent;

    @TableField(exist = false)
    @ApiModelProperty(value = "问题子选项")
    private List<SysQuestionItem> sysQuestionItemList;

}
