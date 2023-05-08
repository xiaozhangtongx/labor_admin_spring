package com.xiaozhang.springboot.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jdk.internal.dynalink.linker.LinkerServices;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 不安全行为上传
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-04-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SysUploadUnsafe对象", description="不安全行为上传")
public class SysUploadUnsafe implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "安全员id")
    private String safetyId;

    @ApiModelProperty(value = "劳工id")
    private String laborId;

    @ApiModelProperty(value = "现场照片")
    private String imgUrl;

    @ApiModelProperty(value = "现场描述")
    private String content;

    @ApiModelProperty(value = "上传时间")
    private Date createTime;

    @ApiModelProperty(value = "标题")
    private String title;

    @TableField(exist = false)
    private List<String> datas;

    @TableField(exist = false)
    private String phoneNum;

    @TableField(exist = false)
    private SysUser labor;

}
