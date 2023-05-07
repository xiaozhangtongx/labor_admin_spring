package com.xiaozhang.springboot.domain;

import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 公告
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-05-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysNotice对象", description = "公告")
public class SysNotice implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "标题")
    @NotBlank(message = "标题不能为空")
    private String title;

    @ApiModelProperty(value = "创建者")
    private String creatorId;

    @ApiModelProperty(value = "部门id")
    private String deptId;

    @ApiModelProperty(value = "小组id")
    private String teamId;

    @ApiModelProperty(value = "公告内容")
    @NotBlank(message = "公告内容不能为空")
    private String content;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "类型")
    private Integer type;

    @ApiModelProperty(value = "是否删除")
    @TableLogic
    @JsonIgnore
    private Integer isDeleted;

    @TableField(exist = false)
    @ApiModelProperty(value = "发布者信息")
    private SysUser creator;

}
