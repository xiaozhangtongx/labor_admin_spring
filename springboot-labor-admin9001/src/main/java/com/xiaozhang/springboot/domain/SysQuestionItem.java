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
 * 题目选项/内容表
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-05-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SysQuestionItem对象", description="题目选项/内容表")
public class SysQuestionItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "问题id")
    private String questionId;

    @ApiModelProperty(value = "描述")
    private String des;

    @ApiModelProperty(value = "描述")
    private Integer sort;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;


}
