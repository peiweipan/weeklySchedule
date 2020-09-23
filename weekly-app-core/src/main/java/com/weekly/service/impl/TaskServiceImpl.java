package com.weekly.service.impl;

import com.firefly.errcode.enums.UserErrorCode;
import com.firefly.errcode.exception.UserException;
import com.weekly.mapper.TaskMapper;
import com.weekly.mapper.UserMapper;
import com.weekly.service.SemesterService;
import com.weekly.service.SummaryService;
import com.weekly.service.TaskService;
import com.weekly.service.UserService;
import com.weekly.user.pojo.po.User;
import com.weekly.user.pojo.vo.TaskAddVo;
import com.weekly.user.pojo.vo.TaskListVo;
import com.weekly.user.pojo.vo.TaskReleaseVo;
import com.weekly.user.pojo.vo.TaskUpdateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private SummaryService summaryService;

    @Autowired
    private SemesterService semesterService;

    @Override
    public List<TaskListVo> showUserTask(Integer taskWeek, String nickname,String schoolYear) {
        Long userId = userMapper.getUserIdbyName(nickname);
        if(schoolYear == null || schoolYear.equals("")){
            schoolYear = semesterService.getSemesterByCurrentDate();
        }
        return taskMapper.showUserTask(taskWeek,userId,schoolYear);
    }

    @Override
    public Map<Long,List<TaskListVo>> showAllUserTask(Integer taskWeek,Long currentUserId,String schoolYear) {
        List<TaskListVo> taskListVos = new ArrayList<>();
        User user = userMapper.getUserById(currentUserId);
        if(schoolYear == null || schoolYear.equals("")){
            schoolYear = semesterService.getSemesterByCurrentDate();
        }
        if(0 == user.getIsAdmin()){
            List<Long> allGeneralUserId = userMapper.getAllGeneralUserId();
            taskListVos = taskMapper.showAllGeneralUserTask(taskWeek,allGeneralUserId,schoolYear);
        }else {
            taskListVos = taskMapper.showAllUserTask(taskWeek,schoolYear);
        }
        if(null == taskListVos || taskListVos.size()<=0){
            return null;
        }else {
            List<Long> userIds = taskListVos.stream().map(TaskListVo::getUserId).collect(Collectors.toList());
            List<User> userList = userService.getUserByIds(userIds);
            Map<Long, User> userMap = userList.stream().collect(Collectors.toMap(User::getId, Function.identity()));

            for (TaskListVo taskListVo : taskListVos) {
                User u = userMap.get(taskListVo.getUserId());
                if (u == null) {
                    continue;
                }
                taskListVo.setNickname(u.getNickname());
            }
            Map<Long, List<TaskListVo>> collect = taskListVos.stream().collect(
                    Collectors.groupingBy(TaskListVo::getUserId, Collectors.toList()));
            return collect;
        }
    }

    @Override
    public TaskListVo showTaskDetails(Long taskId) {
        TaskListVo taskListVo = taskMapper.showTaskDetails(taskId);
        User user = userMapper.getUserById(taskListVo.getUserId());
        taskListVo.setNickname(user.getNickname());
        return taskListVo;
    }

    @Override
    public void updateTask(TaskUpdateVo taskUpdateVo,Long currentUserId) {
        if(taskUpdateVo.getSchoolYear() == null || taskUpdateVo.getSchoolYear().equals("")){
            taskUpdateVo.setSchoolYear(semesterService.getSemesterByCurrentDate());
        }
        if(currentUserId.longValue() == taskUpdateVo.getUserId().longValue()){
            taskMapper.updateTask(taskUpdateVo);
            summaryService.autoUpdateWeeklySummary(currentUserId,taskUpdateVo.getTaskWeek(),taskUpdateVo.getSchoolYear());
        }else {
            throw new UserException(UserErrorCode.A0300);
        }
    }

    @Override
    public void deleteTask(Long taskId, Long userId,Integer taskWeek, Long currentUserId,String schoolYear) {
        if(schoolYear == null || schoolYear.equals("")){
            schoolYear = semesterService.getSemesterByCurrentDate();
        }
//        System.out.println(taskId);
        if(currentUserId.longValue() == userId.longValue()){
            taskMapper.deleteTask(taskId);
            summaryService.autoUpdateWeeklySummary(currentUserId,taskWeek,schoolYear);
        }else {
            throw new UserException(UserErrorCode.A0300);
        }
    }

    @Override
    public void AddTask(TaskAddVo taskAddVo, Long currentUserId) {
        if(taskAddVo.getSchoolYear() == null || taskAddVo.getSchoolYear().equals("")){
            taskAddVo.setSchoolYear(semesterService.getSemesterByCurrentDate());
        }
        if(currentUserId.longValue() == taskAddVo.getUserId().longValue()){
            taskMapper.AddTask(taskAddVo);
            summaryService.autoUpdateWeeklySummary(currentUserId,taskAddVo.getTaskWeek(),taskAddVo.getSchoolYear());
        }else {
            throw new UserException(UserErrorCode.A0300);
        }
    }

    @Override
    public void releaseTask(TaskReleaseVo taskReleaseVo, Long currentUserId) {
        User user = userMapper.getUserById(currentUserId);
        if(taskReleaseVo.getSchoolYear() == null || taskReleaseVo.getSchoolYear().equals("")){
            taskReleaseVo.setSchoolYear(semesterService.getSemesterByCurrentDate());
        }
        Long userIdbyName = userMapper.getUserIdbyName(taskReleaseVo.getNickname());
        taskReleaseVo.setUserId(userIdbyName);
        if(1 == user.getIsAdmin()){
            taskMapper.releaseTask(taskReleaseVo);
            summaryService.autoUpdateWeeklySummary(userIdbyName,taskReleaseVo.getTaskWeek(),taskReleaseVo.getSchoolYear());
        }else {
            throw new UserException(UserErrorCode.A0300);
        }
    }

}
