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
 * 用户小组表
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-05-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SysUserTeam对象", description="用户小组表")
public class SysUserTeam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "小组id")
    private String teamId;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "状态（0-禁言，1-正常）")
    private Integer status;

    @ApiModelProperty(value = "角色id")
    private String roleId;


}
