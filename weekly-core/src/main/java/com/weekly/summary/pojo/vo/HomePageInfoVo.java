package com.weekly.summary.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class HomePageInfoVo {

    @ApiModelProperty("已完成任务数")
    private Long finishCount;

    @ApiModelProperty("总任务数")
    private Long AllCount;

    @ApiModelProperty("加班天数")
    private Integer overtimeDays;

    @ApiModelProperty("本周为第几周")
    private int weeks;
}
