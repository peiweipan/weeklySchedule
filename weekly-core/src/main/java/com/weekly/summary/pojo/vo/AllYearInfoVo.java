package com.weekly.summary.pojo.vo;

import com.weekly.framework.aspectj.lang.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AllYearInfoVo {

    @Excel(name = "上半年请假天数")
    @ApiModelProperty("上半年请假天数")
    private int firstHalfYearOffDays;

    @Excel(name = "上半年加班天数")
    @ApiModelProperty("上半年加班天数")
    private int firstHalfYearOvertimeDays;

    @Excel(name = "上半年绩效")
    @ApiModelProperty("上半年绩效")
    private int firstHalfYearPerformance;

    @Excel(name = "下半年请假天数")
    @ApiModelProperty("下半年请假天数")
    private int SecondHalfYearOffDays;

    @Excel(name = "下半年加班天数")
    @ApiModelProperty("下半年加班天数")
    private int SecondHalfYearOvertimeDays;

    @Excel(name = "下半年绩效")
    @ApiModelProperty("下半年绩效")
    private int SecondHalfYearPerformance;

    @Excel(name = "全年请假天数")
    @ApiModelProperty("全年请假天数")
    private int totalOffDays;

    @Excel(name = "全年加班天数")
    @ApiModelProperty("全年加班天数")
    private int totalOvertimeDays;

    @Excel(name = "全年绩效")
    @ApiModelProperty("全年绩效")
    private int totalPerformance;
}
