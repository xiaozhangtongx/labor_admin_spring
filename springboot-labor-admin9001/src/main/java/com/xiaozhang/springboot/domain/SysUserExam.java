package com.xiaozhang.springboot.domain;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 *
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-05-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysUserExam对象", description = "")
public class SysUserExam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "用户id")
    @NotBlank(message = "用户无效")
    private String userId;

    @ApiModelProperty(value = "考试id")
    @NotBlank(message = "考试id失效")
    private String examId;

    @ApiModelProperty(value = "作答开始时间")
    private Date startTime;

    @ApiModelProperty(value = "作答结束时间")
    private Date endTime;

    @ApiModelProperty(value = "描述")
    private String des;

    @ApiModelProperty(value = "成绩")
    private Double grades;

    @ApiModelProperty(value = "考试状态（是否作弊，超时等等）")
    private Integer status;

    @ApiModelProperty(value = "考试结果（是否过关）")
    private Integer res;

    @ApiModelProperty(value = "考试用时（s）")
    private Double spendTime;

    @ApiModelProperty(value = "试卷总分")
    private Double examScore;

    @TableField(exist = false)
    @ApiModelProperty(value = "用户答案列表")
    private List<SysExamQuestionUser> userAnswerList;

}
