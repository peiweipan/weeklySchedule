package com.weekly.user.pojo.vo;

import com.weekly.user.enums.TaskLevelTypeEnums;
import com.weekly.user.enums.TaskStatusTypeEnums;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class TaskUpdateVo {

    @NotNull
    @ApiModelProperty("任务id")
    private Long taskId;

    @NotNull
    @ApiModelProperty("任务所属者id")
    private Long userId;

    @NotBlank
    @ApiModelProperty("任务名称")
    private String taskName;

    @ApiModelProperty("预期成果")
    private String expResult;

    @ApiModelProperty("任务备注")
    private String taskComment;

    @NotNull
    @ApiModelProperty("任务状态")
    private TaskStatusTypeEnums taskStatus;

    @NotNull
    @ApiModelProperty("任务级别")
    private TaskLevelTypeEnums taskLevel;

    @NotBlank
    @ApiModelProperty("任务预完成时间")
    private String taskDate;

    @NotBlank
    @ApiModelProperty("星期几")
    private String dayOfWeek;

    @NotNull
    @ApiModelProperty("任务在哪周")
    private Integer taskWeek;

    @ApiModelProperty("学年学期")
    private String schoolYear;
}
