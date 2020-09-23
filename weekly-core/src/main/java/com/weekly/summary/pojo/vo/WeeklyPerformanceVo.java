package com.weekly.summary.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyPerformanceVo {

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("用户名称")
    private String nickname;

    @ApiModelProperty("周数")
    private Integer taskWeek;

    @ApiModelProperty("完成质量")
    private Integer workPerformance;
}
