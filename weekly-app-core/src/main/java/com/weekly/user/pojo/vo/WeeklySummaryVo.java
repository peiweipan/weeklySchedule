package com.weekly.user.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WeeklySummaryVo {

    @ApiModelProperty("综合表id")
    private Long SummaryId;

    @ApiModelProperty("用户名称")
    private Long userId;

    @ApiModelProperty("任务所属者")
    private String nickname;

    @ApiModelProperty("第几周")
    private Integer taskWeek;

    @ApiModelProperty("完成质量")
    private int finishQuality;

    @ApiModelProperty("工作数量")
    private int jobCount;

    @ApiModelProperty("本职工作")
    private String jobDescription;

    @ApiModelProperty("请假天数")
    private int offDays;

    @ApiModelProperty("加班数量")
    private int overtimeDays;

    @ApiModelProperty("工作级别系数")
    private int jobLevel;

    @ApiModelProperty("工作绩效")
    private int workPerformance;

    @ApiModelProperty("学年学期")
    private String schoolYear;

    @ApiModelProperty("需工作天数")
    private Integer workDays;

}
