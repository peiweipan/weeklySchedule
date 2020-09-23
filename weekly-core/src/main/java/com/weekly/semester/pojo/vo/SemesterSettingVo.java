package com.weekly.semester.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


@Data
public class SemesterSettingVo {

    @ApiModelProperty("学期设置id")
    private Long semesterId;

    @ApiModelProperty("学年及学期：XXXX第几学期")
    private String schoolYearAndSemester;

    @ApiModelProperty("学期开始日期:2XXX-XX-XX")
    private Date startDate;

    @ApiModelProperty("学期周数")
    private int semesterWeeks;
}
