package com.weekly.user.pojo.po;

import com.weekly.user.enums.TaskLevelTypeEnums;
import com.weekly.user.enums.TaskStatusTypeEnums;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Task {

    @ApiModelProperty("任务id")
    private Long taskId;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("任务名称")
    private String taskName;

    @ApiModelProperty("预期成果")
    private String expResult;

    @ApiModelProperty("任务备注")
    private String taskComment;

    @ApiModelProperty("任务状态")
    private TaskStatusTypeEnums taskStatus;

    @ApiModelProperty("任务级别")
    private TaskLevelTypeEnums taskLevel;

    @ApiModelProperty("任务预完成时间")
    private String taskDate;

    @ApiModelProperty("星期几")
    private String dayOfWeek;

    @ApiModelProperty("任务在哪周")
    private String taskWeek;

    @ApiModelProperty("学年学期")
    private Boolean isdeleted;
}
