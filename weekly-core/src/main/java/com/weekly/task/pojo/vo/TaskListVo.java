package com.weekly.task.pojo.vo;

import com.weekly.framework.aspectj.lang.annotation.Excel;
import com.weekly.task.enums.TaskLevelTypeEnums;
import com.weekly.task.enums.TaskStatusTypeEnums;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class TaskListVo {

    @ApiModelProperty("任务id")
    private Long taskId;

    @ApiModelProperty("任务所属者id")
    private Long userId;

    @Excel(name = "负责人")
    @ApiModelProperty("任务所属者")
    private String nickname;

    @Excel(name = "任务标题")
    @ApiModelProperty("任务名称")
    private String taskName;

    @Excel(name = "预期成果")
    @ApiModelProperty("预期成果")
    private String expResult;

    @Excel(name = "事项备注")
    @ApiModelProperty("任务备注")
    private String taskComment;

    @Excel(name = "任务状态")
    @ApiModelProperty("任务状态")
    private TaskStatusTypeEnums taskStatus;

    @Excel(name = "任务级别")
    @ApiModelProperty("任务级别")
    private TaskLevelTypeEnums taskLevel;

    @Excel(name = "结束时间")
    @ApiModelProperty("任务预完成时间")
    private String taskDate;

    @Excel(name = "星期几")
    @ApiModelProperty("星期几")
    private String dayOfWeek;

    @Excel(name = "第几周")
    @ApiModelProperty("任务在哪周")
    private Integer taskWeek;

    @Excel(name = "学年学期")
    @ApiModelProperty("学年学期")
    private String schoolYear;

}
