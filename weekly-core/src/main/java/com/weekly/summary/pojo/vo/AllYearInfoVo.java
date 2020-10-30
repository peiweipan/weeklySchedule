package com.weekly.summary.pojo.vo;

import com.weekly.framework.aspectj.lang.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AllYearInfoVo {

    @Excel(name = "上半年请假天数")
    @ApiModelProperty("上半年请假天数")
    private double firstHalfYearOffDays;

    @Excel(name = "上半年加班天数")
    @ApiModelProperty("上半年加班天数")
    private double firstHalfYearOvertimeDays;

    @Excel(name = "上半年绩效")
    @ApiModelProperty("上半年绩效")
    private double firstHalfYearPerformance;

    @Excel(name = "下半年请假天数")
    @ApiModelProperty("下半年请假天数")
    private double SecondHalfYearOffDays;

    @Excel(name = "下半年加班天数")
    @ApiModelProperty("下半年加班天数")
    private double SecondHalfYearOvertimeDays;

    @Excel(name = "下半年绩效")
    @ApiModelProperty("下半年绩效")
    private double SecondHalfYearPerformance;

    @Excel(name = "全年请假天数")
    @ApiModelProperty("全年请假天数")
    private double totalOffDays;

    @Excel(name = "全年加班天数")
    @ApiModelProperty("全年加班天数")
    private double totalOvertimeDays;

    @Excel(name = "全年绩效")
    @ApiModelProperty("全年绩效")
    private double totalPerformance;
}
