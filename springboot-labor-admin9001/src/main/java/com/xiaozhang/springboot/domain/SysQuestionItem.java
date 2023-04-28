package com.xiaozhang.springboot.domain;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2023-04-24
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


}
