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
 * 用户表
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-03-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SysUser对象", description="用户表")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "电话号码")
    private String phoneNum;

    @ApiModelProperty(value = "部门id")
    private String deptId;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "安全积分（如果为负，则要重新学习）")
    private Integer score;

    @ApiModelProperty(value = "最近登录时间")
    private Date lastLogin;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "用户状态(0-未登录,1-已登录，2-已冻结)")
    private Integer status;

    @ApiModelProperty(value = "是否删除(0-未删, 1-已删)")
    private Integer  isDeleted;


}
