package com.xiaozhang.springboot.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author: xiaozhangtx
 * @ClassName: RoleInfoView
 * @Description: TODO 角色表视图
 * @date: 2023/4/17 11:10
 * @Version: 1.0
 */
@Data
@TableName("role_info_view")
@ApiModel(value = "角色信息", description = "角色信息视图")
public class RoleInfoView {
    private String id;
    private String roleName;
}
