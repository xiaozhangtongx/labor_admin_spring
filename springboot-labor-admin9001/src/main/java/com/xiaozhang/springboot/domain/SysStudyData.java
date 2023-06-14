package com.xiaozhang.springboot.domain;

import com.baomidou.mybatisplus.annotation.IdType;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 *
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-05-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysStudyData对象", description = "")
public class SysStudyData implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "创建者id")
    private String creatorId;

    @ApiModelProperty(value = "标题")
    @NotBlank(message = "标题不能为空")
    private String title;

    @ApiModelProperty(value = "内容描述")
    @NotBlank(message = "内容描述不能为空")
    private String content;

    @ApiModelProperty(value = "学习类型（0-文档，1-视频，2-图片）")
    @NotNull(message = "学习资料类型不能为空")
    private Integer type;

    @ApiModelProperty(value = "资源链接")
    @NotNull(message = "学习资料不能为空")
    private String fileUrl;

    @ApiModelProperty(value = "封面")
    @NotNull(message = "封面不能为空")
    private String coverUrl;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "是否删除")
    @JsonIgnore
    @TableLogic()
    private Integer isDeleted;

    @TableField(exist = false)
    @ApiModelProperty(value = "发布者信息")
    private SysUser creator;

    @TableField(exist = false)
    private Integer textNum;

    @TableField(exist = false)
    private  Integer PDFNum;

    @TableField(exist = false)
    private Integer videoNum;

    @TableField(exist = false)
    private Integer allNum;

    @TableField(exist = false)
    private Date time;


}
