package com.xiaozhang.springboot.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 小组表
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-05-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SysTeam对象", description="小组表")
public class SysTeam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "部门id")
    private String deptId;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "是否解散")
    @TableLogic
    private String isDeleted;

    @ApiModelProperty(value = "描述")
    private String des;

    @ApiModelProperty(value = "小组名称")
    private String teamName;

    @ApiModelProperty(value = "小组状态（0-停工，1-正常）")
    private Integer status;


}
