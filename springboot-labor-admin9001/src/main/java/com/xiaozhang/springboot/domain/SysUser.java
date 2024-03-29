package com.xiaozhang.springboot.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-03-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "SysUser对象", description = "用户表")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "当前密码")
    @TableField(exist = false)
    private String oldPassword;

    @ApiModelProperty(value = "电话号码")
    @NotBlank(message = "电话号码不能为空")
    private String phoneNum;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "安全积分（如果为负，则要重新学习）")
    private Integer score;

    @ApiModelProperty(value = "最近登录时间")
    @JsonIgnore
    private Date lastLogin;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @JsonIgnore
    private Date updateTime;

    @ApiModelProperty(value = "用户状态(0-未登录,1-已登录，2-已冻结)")
    private Integer status;

    @ApiModelProperty(value = "是否删除(0-未删, 1-已删)")
    @TableField("is_deleted")
    @JsonIgnore
    @TableLogic()
    private Integer isDeleted;

    @TableField(exist = false)
    @ApiModelProperty(value = "用户角色列表")
    private List<SysRole> roles;

    @TableField(exist = false)
    @ApiModelProperty(value = "用户所在部门")
    private SysDept sysDept;

}
