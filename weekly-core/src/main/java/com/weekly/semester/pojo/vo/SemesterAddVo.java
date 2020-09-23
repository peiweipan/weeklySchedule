package com.weekly.semester.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class SemesterAddVo {

    @NotBlank
    @ApiModelProperty("学年及学期：XXXX第几学期")
    private String schoolYearAndSemester;

    @NotNull
    @ApiModelProperty("学期开始日期:2XXX-XX-XX")
    private Date startDate;

    @NotNull
    @ApiModelProperty("学期周数")
    private int semesterWeeks;
}
