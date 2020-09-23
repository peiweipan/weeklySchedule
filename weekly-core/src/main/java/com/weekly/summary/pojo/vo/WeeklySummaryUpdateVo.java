package com.weekly.summary.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class WeeklySummaryUpdateVo {

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("第几周")
    private Integer taskWeek;

    @ApiModelProperty("学年学期")
    private String schoolYear;

    @ApiModelProperty("本职工作")
    private String jobDescription;

    @ApiModelProperty("请假天数")
    private double offDays;

    @ApiModelProperty("加班数量")
    private double overtimeDays;
}
