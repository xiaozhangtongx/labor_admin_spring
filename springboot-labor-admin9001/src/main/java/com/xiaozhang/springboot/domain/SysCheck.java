package com.xiaozhang.springboot.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 出勤表
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-04-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysCheck对象", description = "出勤表")
public class SysCheck implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "经度")
    @NotNull(message = "坐标不能为空")
    private Double lon;

    @ApiModelProperty(value = "纬度")
    @NotNull(message = "坐标不能为空")
    private Double lat;

    @ApiModelProperty(value = "工作时间")
    private Double workTime;

    @ApiModelProperty(value = "描述")
    private String des;

    @ApiModelProperty(value = "状况")
    private Integer status;


}