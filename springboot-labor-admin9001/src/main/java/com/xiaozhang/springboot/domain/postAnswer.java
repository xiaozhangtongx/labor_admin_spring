package com.xiaozhang.springboot.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "用户选择的一个问题的答案", description = "工具类，便于接数据")
public class postAnswer {
    private String id;
    private String answer;
}
