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
 * 用户学习情况表
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-05-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SysUserStudy对象", description="用户学习情况表")
public class SysUserStudy implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "学习资料id")
    private String studyId;

    @ApiModelProperty(value = "学习情况（0-未学习，1-已经学习）")
    private Long status;

    @ApiModelProperty(value = "初次学习时间")
    private Date createTime;

    @ApiModelProperty(value = "最近一次学习时间")
    private Date updateTime;


}
