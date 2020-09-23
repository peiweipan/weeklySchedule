package com.weekly.task.service;

import com.weekly.framework.web.domain.AjaxResult;
import com.weekly.task.pojo.po.Task;
import com.weekly.task.pojo.vo.*;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface TaskService {

    List<TaskListVo> showCurrentUserTask(int taskWeek);

    List<TaskListVo> showUserTask(Integer taskWeek,String nickname,String schoolYear) throws ParseException;

    void AddTask(TaskAddVo taskAddVo);

    void AddOwnTask(TaskAddOwnVo taskAddOwnVo) throws ParseException;

    void deleteTask(Long taskId,String nickname,int taskWeek,String schoolYear);

    void updateTask(TaskUpdateVo taskUpdateVo);

    Map<String, List<TaskSortByDayVo>> viewTaskByMonth(String month);

    List<Task> selectTaskList(String schoolYear,Integer taskWeek,String nickname);

    AjaxResult exportTask(String schoolYear, Integer taskWeek, String nickname);

    void releaseTask(TaskReleaseVo taskReleaseVo);

    TaskListVo showTaskById(Integer taskId);
}
