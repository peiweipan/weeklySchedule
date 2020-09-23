package com.weekly.task.pojo.po;

import com.weekly.framework.aspectj.lang.annotation.Excel;
import com.weekly.framework.web.domain.BaseEntity;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 任务事项对象 task
 *
 * @author weekly
 * @date 2020-09-11
 */
public class Task extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 任务id */
    private Long taskId;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 任务名称 */
    @Excel(name = "任务名称")
    private String taskName;

    /** 预期成果 */
    @Excel(name = "预期成果")
    private String expResult;

    /** 任务备注 */
    @Excel(name = "任务备注")
    private String taskComment;

    /** 任务状态 */
    @Excel(name = "任务状态")
    private Long taskStatus;

    /** 任务级别 */
    @Excel(name = "任务级别")
    private Long taskLevel;

    /** 预完成时间 */
    @Excel(name = "预完成时间")
    private String taskDate;

    /** 星期几 */
    @Excel(name = "星期几")
    private String dayofweek;

    /** 第几周 */
    @Excel(name = "第几周")
    private String taskWeek;

    /** 是否删除 */
    @Excel(name = "是否删除")
    private Long isDeleted;

    /** 学年学期 */
    @Excel(name = "学年学期")
    private String schoolYear;

    public void setTaskId(Long taskId)
    {
        this.taskId = taskId;
    }

    public Long getTaskId()
    {
        return taskId;
    }
    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }
    public void setTaskName(String taskName)
    {
        this.taskName = taskName;
    }

    public String getTaskName()
    {
        return taskName;
    }
    public void setExpResult(String expResult)
    {
        this.expResult = expResult;
    }

    public String getExpResult()
    {
        return expResult;
    }
    public void setTaskComment(String taskComment)
    {
        this.taskComment = taskComment;
    }

    public String getTaskComment()
    {
        return taskComment;
    }
    public void setTaskStatus(Long taskStatus)
    {
        this.taskStatus = taskStatus;
    }

    public Long getTaskStatus()
    {
        return taskStatus;
    }
    public void setTaskLevel(Long taskLevel)
    {
        this.taskLevel = taskLevel;
    }

    public Long getTaskLevel()
    {
        return taskLevel;
    }
    public void setTaskDate(String taskDate)
    {
        this.taskDate = taskDate;
    }

    public String getTaskDate()
    {
        return taskDate;
    }
    public void setDayofweek(String dayofweek)
    {
        this.dayofweek = dayofweek;
    }

    public String getDayofweek()
    {
        return dayofweek;
    }
    public void setTaskWeek(String taskWeek)
    {
        this.taskWeek = taskWeek;
    }

    public String getTaskWeek()
    {
        return taskWeek;
    }
    public void setIsDeleted(Long isDeleted)
    {
        this.isDeleted = isDeleted;
    }

    public Long getIsDeleted()
    {
        return isDeleted;
    }
    public void setSchoolYear(String schoolYear)
    {
        this.schoolYear = schoolYear;
    }

    public String getSchoolYear()
    {
        return schoolYear;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("taskId", getTaskId())
                .append("userId", getUserId())
                .append("taskName", getTaskName())
                .append("expResult", getExpResult())
                .append("taskComment", getTaskComment())
                .append("taskStatus", getTaskStatus())
                .append("taskLevel", getTaskLevel())
                .append("taskDate", getTaskDate())
                .append("dayofweek", getDayofweek())
                .append("taskWeek", getTaskWeek())
                .append("isDeleted", getIsDeleted())
                .append("schoolYear", getSchoolYear())
                .toString();
    }
}