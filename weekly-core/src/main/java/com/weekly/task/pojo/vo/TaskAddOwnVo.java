package com.weekly.task.pojo.vo;

import com.weekly.task.enums.TaskLevelTypeEnums;
import com.weekly.task.enums.TaskStatusTypeEnums;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class TaskAddOwnVo {

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

}
