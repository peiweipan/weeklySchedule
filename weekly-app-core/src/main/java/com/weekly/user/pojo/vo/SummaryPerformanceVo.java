package com.weekly.user.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SummaryPerformanceVo {

    @ApiModelProperty("上半年请假天数")
    private int firstHalfYearOffDays;

    @ApiModelProperty("上半年加班天数")
    private int firstHalfYearOvertimeDays;

    @ApiModelProperty("上半年绩效")
    private int firstHalfYearPerformance;

    @ApiModelProperty("下半年请假天数")
    private int SecondHalfYearOffDays;

    @ApiModelProperty("下半年加班天数")
    private int SecondHalfYearOvertimeDays;

    @ApiModelProperty("下半年绩效")
    private int SecondHalfYearPerformance;

    @ApiModelProperty("全年请假天数")
    private int totalOffDays;

    @ApiModelProperty("全年加班天数")
    private int totalOvertimeDays;

    @ApiModelProperty("全年绩效")
    private int totalPerformance;
}
