package com.weekly.mapper;

import com.weekly.user.pojo.vo.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskMapper {

    List<TaskListVo> showUserTask(Integer taskWeek, Long userId,String schoolYear);

    TaskListVo showTaskDetails(Long taskId);

    void updateTask(TaskUpdateVo taskUpdateVo);

    void deleteTask(Long taskId);

    void AddTask(TaskAddVo taskAddVo);

    void releaseTask(TaskReleaseVo taskReleaseVo);

    Long finishTaskCount(Long userId,Integer taskWeek,String schoolYear);

    Long allTaskCount(Long userId,Integer taskWeek,String schoolYear);

    List<TaskLevelListVo> getFinishTaskLevelList(Long userId,Integer taskWeek,String schoolYear);

    List<TaskListVo> showAllUserTask(Integer taskWeek,String schoolYear);

    List<TaskListVo> showAllGeneralUserTask(Integer taskWeek,List<Long> allGeneralUserId,String schoolYear);

}
