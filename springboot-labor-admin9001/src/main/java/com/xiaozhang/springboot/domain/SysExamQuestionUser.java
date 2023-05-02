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

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 用户答题表
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-05-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysExamQuestionUser对象", description = "用户答题表")
public class SysExamQuestionUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "考试问题id")
    @NotBlank(message = "问题无效")
    private String examQuestionId;

    @ApiModelProperty(value = "用户答案")
    private String userAnswer;

    @ApiModelProperty(value = "是否作对")
    private Integer doRight;

    @ApiModelProperty(value = "用户此题得分")
    private Double userScore;

}
