package com.xiaozhang.springboot.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-03-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
//@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value = "SysMenu对象", description = "菜单表")
public class SysMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "父菜单ID，一级菜单为0")
    @NotNull(message = "上级菜单不能为空")
    private String parentId;

    @ApiModelProperty(value = "菜单名")
    @NotBlank(message = "菜单名称不能为空")
    private String menuName;

    @ApiModelProperty(value = "菜单URL")
    private String menuPath;

    @ApiModelProperty(value = "授权(多个用逗号分隔，如：user:list,user:create)")
    @NotBlank(message = "菜单授权码不能为空")
    private String menuPerms;

    @ApiModelProperty(value = "图标")
    private String menuIcon;

    @ApiModelProperty(value = "组件")
    private String component;

    @ApiModelProperty(value = "排序")
    private Integer orderNum;

    @ApiModelProperty(value = "创建时间")
    @JsonIgnore
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @JsonIgnore
    private Date updateTime;

    @TableField(exist = false)
    @ApiModelProperty(value = "子级菜单")
    private List<SysMenu> children;

    @ApiModelProperty(value = "是否删除(0-未删, 1-已删)")
    @JsonIgnore
    @TableLogic
    private Integer isDeleted;
    
}
