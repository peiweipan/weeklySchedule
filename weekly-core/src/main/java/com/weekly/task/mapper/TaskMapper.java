package com.weekly.task.mapper;

import com.weekly.task.pojo.po.Task;
import com.weekly.task.pojo.vo.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskMapper {

    List<TaskListVo> showUserTask(int taskWeek, Long userId, String schoolYear);

    Long finishTaskCount(Long userId,int taskWeek,String schoolYear);

    Long allTaskCount(Long userId,int taskWeek,String schoolYear);

    void AddTask(TaskAddVo taskAddVo);

    void deleteTask(Long taskId);

    void updateTask(TaskUpdateVo taskUpdateVo);

    List<TaskLevelListVo> getFinishTaskLevelList(Long userId, int taskWeek, String schoolYear);

    List<TaskSortByDayVo> viewTaskByMonth(String month,Long userId);

    List<Task> selectTaskList(String schoolYear,Integer taskWeek,Long userId);

    void releaseTask(TaskReleaseVo taskReleaseVo);

    TaskListVo showTaskById(Integer taskId);
}
