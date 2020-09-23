package com.weekly.service;

import com.weekly.user.pojo.po.WeeklySummary;
import com.weekly.user.pojo.vo.*;

import java.util.List;
import java.util.Map;

public interface TaskService {

    List<TaskListVo> showUserTask(Integer taskWeek,String nickname,String schoolYear);

    TaskListVo showTaskDetails(Long taskId);

    void updateTask(TaskUpdateVo taskUpdateVo,Long currentUserId);

    void deleteTask(Long taskId,Long userId,Integer taskWeek,Long currentUserId,String schoolYear);

    void AddTask(TaskAddVo taskAddVo,Long currentUserId);

    void releaseTask(TaskReleaseVo taskReleaseVo,Long currentUserId);

    Map<Long, List<TaskListVo>> showAllUserTask(Integer taskWeek, Long currentUserId,String schoolYear);

//    Long finishTaskCount(String username,String taskWeek);

//    Long allTaskCount(String username,String taskWeek);


}
