package com.xiaozhang.springboot.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Time;

/**
 * <p>
 * 部门标准表
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-04-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysDeptStandard对象", description = "部门标准表")
public class SysDeptStandard implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "部门id")
    @NotBlank(message = "部门id不能为空")
    private String deptId;

    @ApiModelProperty(value = "经度")
    @NotNull(message = "经度不能为空")
    private Double lon;

    @ApiModelProperty(value = "纬度")
    @NotNull(message = "纬度不能为空")
    private Double lat;

    @ApiModelProperty(value = "最早下班时间")
    @NotNull(message = "最早下班时间不能为空")
    private Time earliestTime;

    @ApiModelProperty(value = "最迟打卡时间")
    @NotNull(message = "最迟打卡时间不能为空")
    private Time latestTime;

    @ApiModelProperty(value = "最低工作时长")
    @NotNull(message = "最迟打卡时间不能为空")
    private Double minDuration;

    @ApiModelProperty(value = "附件")
    private String fileUrl;

    @ApiModelProperty(value = "描述")
    private String des;

    @ApiModelProperty(value = "打卡半径，单位km")
    @NotNull(message = "打卡半径不能为空")
    private Double radius;


}
