package com.xiaozhang.springboot.domain;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "pie图数据格式转换", description = "工具类，便于接数据")
public class ChartData {
    private String name;
    private Double addTime;
    private Double overTime;
    private Double workTime;
    private Integer leaveDay;
    private Integer overDay;
    private Integer workDay;
}
