package com.xiaozhang.springboot.domain;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-04-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SysUserExam对象", description="")
public class SysUserExam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "考试结果")
    private String userId;

    @ApiModelProperty(value = "考试id")
    private String examId;

    @ApiModelProperty(value = "作答开始时间")
    private Date startTime;

    @ApiModelProperty(value = "作答结束时间")
    private Date endTime;

    @ApiModelProperty(value = "描述")
    private String des;

    @ApiModelProperty(value = "成绩")
    private Double gardes;

    @ApiModelProperty(value = "考试状态（是否作弊，超时等等）")
    private Integer status;

    @ApiModelProperty(value = "考试结果（是否过关）")
    private Integer res;

    @ApiModelProperty(value = "考试用时（s）")
    private Double spendTime;

    @ApiModelProperty(value = "用户提交答案id")
    private String anserId;

    @TableField(exist = false)
    private List<postAnswer> answers;

}
