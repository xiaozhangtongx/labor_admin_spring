package com.xiaozhang.springboot.domain;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * 考试问题表
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-04-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SysQuestion对象", description="考试问题表")
public class SysQuestion implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "题目")
    private String title;

    @ApiModelProperty(value = "考试id")
    private String examId;

    @ApiModelProperty(value = "题目类型（现在暂时为两种，一种为选择题，一种为判断题）")
    private Long type;

    @ApiModelProperty(value = "分数")
    private Double score;

    @ApiModelProperty(value = "答案")
    private String answerId;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @TableField(exist = false)
    private List<SysQuestionItem> items;
}
