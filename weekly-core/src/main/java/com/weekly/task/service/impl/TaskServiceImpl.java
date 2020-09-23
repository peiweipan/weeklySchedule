package com.weekly.task.service.impl;

import com.firefly.errcode.enums.UserErrorCode;
import com.firefly.errcode.exception.UserException;
import com.weekly.common.utils.CalendarUtils;
import com.weekly.common.utils.ServletUtils;
import com.weekly.common.utils.poi.ExcelUtil;
import com.weekly.framework.security.LoginUser;
import com.weekly.framework.security.service.TokenService;
import com.weekly.framework.web.domain.AjaxResult;
import com.weekly.semester.mapper.SemesterSettingMapper;
import com.weekly.summary.service.SummaryService;
import com.weekly.task.mapper.TaskMapper;
import com.weekly.task.pojo.po.Task;
import com.weekly.task.pojo.vo.*;
import com.weekly.task.service.TaskService;
import com.weekly.user.mapper.UserMapper;
import com.weekly.user.pojo.po.User;
import com.weekly.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    private TokenService tokenService;

    @Autowired
    private SummaryService summaryService;

    @Autowired
    private SemesterSettingMapper semesterSettingMapper;

    @Override
    public List<TaskListVo> showCurrentUserTask(int taskWeek) {
        LocalDate now = LocalDate.now();
        String schoolYear = semesterSettingMapper.getSemesterByCurrentDate(now);

        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        Long userId = loginUser.getUser().getUserId();
        User userById = userMapper.getUserById(userId);

        List<TaskListVo> taskListVos = taskMapper.showUserTask(taskWeek, userId, schoolYear);
        if(null == taskListVos || taskListVos.size()<=0){
            return taskListVos;
        }else {
            for (TaskListVo taskListVo : taskListVos) {
                taskListVo.setNickname(userById.getNickname());
            }
            return taskListVos;
        }
    }

    @Override
    public List<TaskListVo> showUserTask(Integer taskWeek, String nickname, String schoolYear) throws ParseException {
        List<TaskListVo> taskListVos = new ArrayList<>();
        if (null == taskWeek
                && (null == schoolYear || schoolYear.length()<=0)
                && (null == nickname || nickname.length()<=0)){
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            Long userId = loginUser.getUser().getUserId();

            LocalDate now = LocalDate.now();
            schoolYear = semesterSettingMapper.getSemesterByCurrentDate(now);
            String startDateBySchoolYear = semesterSettingMapper.getStartDateBySchoolYear(schoolYear);
            int weeksByStartDate = CalendarUtils.computeWeeks(startDateBySchoolYear);
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String nowDays = now.format(fmt);
            int weeksByNow = CalendarUtils.computeWeeks(nowDays);
            taskWeek = weeksByNow - weeksByStartDate +1;

            taskListVos = taskMapper.showUserTask(taskWeek, userId, schoolYear);
        }else {
            Long userId = userMapper.getUserIdbyName(nickname);
            taskListVos = taskMapper.showUserTask(taskWeek, userId, schoolYear);
        }
        if(null == taskListVos || taskListVos.size()<=0){
            return taskListVos;
        }else {
            List<Long> userIds = taskListVos.stream().map(TaskListVo::getUserId).collect(Collectors.toList());
//            System.out.println(userIds);
            List<User> userList = userService.getUserByIds(userIds);
//            System.out.println(userList);
            Map<Long, User> userMap = userList.stream().collect(Collectors.toMap(User::getId, Function.identity()));
//            System.out.println(userMap);
            for (TaskListVo taskListVo : taskListVos) {
                User user = userMap.get(taskListVo.getUserId());
                if (user == null) {
                    continue;
                }
                taskListVo.setNickname(user.getNickname());
//                System.out.println(taskListVo);
            }
            return taskListVos;
        }
    }

    @Override
    public void AddTask(TaskAddVo taskAddVo) {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        Long userId = loginUser.getUser().getUserId();
//        System.out.println(userId);
        Long userIdbyName = userMapper.getUserIdbyName(taskAddVo.getNickname());
//        System.out.println(userIdbyName);
        if (userId.longValue() == userIdbyName.longValue()){
            taskAddVo.setUserId(userIdbyName);
            taskMapper.AddTask(taskAddVo);
            summaryService.autoUpdateWeeklySummary(userIdbyName,taskAddVo.getTaskWeek(),taskAddVo.getSchoolYear());
        }else {
            throw new UserException(UserErrorCode.A0300);
        }
    }

    @Override
    public void AddOwnTask(TaskAddOwnVo taskAddOwnVo) throws ParseException {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        Long userId = loginUser.getUser().getUserId();

        LocalDate now = LocalDate.now();
        String schoolYear = semesterSettingMapper.getSemesterByCurrentDate(now);
        String startDateBySchoolYear = semesterSettingMapper.getStartDateBySchoolYear(schoolYear);
        int weeksByStartDate = CalendarUtils.computeWeeks(startDateBySchoolYear);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String nowDays = now.format(fmt);
        int weeksByNow = CalendarUtils.computeWeeks(nowDays);
        int taskWeek = weeksByNow - weeksByStartDate +1;

        TaskAddVo taskAddVo = new TaskAddVo();
        taskAddVo.setUserId(userId);
        taskAddVo.setSchoolYear(schoolYear);
        taskAddVo.setTaskWeek(taskWeek);
        taskAddVo.setDayOfWeek(taskAddOwnVo.getDayOfWeek());
        taskAddVo.setExpResult(taskAddOwnVo.getExpResult());
        taskAddVo.setTaskComment(taskAddOwnVo.getTaskComment());
        taskAddVo.setTaskDate(taskAddOwnVo.getTaskDate());
        taskAddVo.setTaskLevel(taskAddOwnVo.getTaskLevel());
        taskAddVo.setTaskStatus(taskAddOwnVo.getTaskStatus());
        taskAddVo.setTaskName(taskAddOwnVo.getTaskName());
        taskMapper.AddTask(taskAddVo);
        summaryService.autoUpdateWeeklySummary(userId,taskAddVo.getTaskWeek(),taskAddVo.getSchoolYear());
    }

    @Override
    public void deleteTask(Long taskId, String nickname, int taskWeek, String schoolYear) {
        System.out.println(schoolYear);
//        System.out.println(taskId);
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        Long userId = loginUser.getUser().getUserId();
        Long userIdbyName = userMapper.getUserIdbyName(nickname);
        if (userId.longValue() == userIdbyName.longValue()){
            taskMapper.deleteTask(taskId);
            summaryService.autoUpdateWeeklySummary(userIdbyName,taskWeek,schoolYear);
        }else {
            throw new UserException(UserErrorCode.A0300);
        }
    }

    @Override
    public void updateTask(TaskUpdateVo taskUpdateVo) {
        System.out.println(taskUpdateVo);
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        Long userId = loginUser.getUser().getUserId();
        Long userIdbyName = userMapper.getUserIdbyName(taskUpdateVo.getNickname());
        if (userId.longValue() == userIdbyName.longValue()){
//            taskUpdateVo.setUserId(userIdbyName);
            taskMapper.updateTask(taskUpdateVo);
            summaryService.autoUpdateWeeklySummary(userIdbyName,taskUpdateVo.getTaskWeek(),taskUpdateVo.getSchoolYear());
        }else {
            throw new UserException(UserErrorCode.A0300);
        }
    }

    @Override
    public Map<String, List<TaskSortByDayVo>> viewTaskByMonth(String month) {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        Long userId = loginUser.getUser().getUserId();

        List<TaskSortByDayVo> taskSortByDayVos = taskMapper.viewTaskByMonth(month,userId);
        Map<String, List<TaskSortByDayVo>> collect = taskSortByDayVos.stream()
                .collect(Collectors.groupingBy(TaskSortByDayVo::getDay, Collectors.toList()));
        return collect;
    }

    @Override
    public List<Task> selectTaskList(String schoolYear,Integer taskWeek,String nickname) {
        Long userIdbyName = userMapper.getUserIdbyName(nickname);
        return taskMapper.selectTaskList(schoolYear,taskWeek,userIdbyName);
    }

    @Override
    public AjaxResult exportTask(String schoolYear, Integer taskWeek, String nickname) {
        List<Task> list = selectTaskList(schoolYear, taskWeek, nickname);
        ExcelUtil<Task> util = new ExcelUtil<Task>(Task.class);
        return util.exportExcel(list, "task");
    }

    @Override
    public void releaseTask(TaskReleaseVo taskReleaseVo) {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        Long userId = loginUser.getUser().getUserId();
        if(taskReleaseVo.getSchoolYear() == null || taskReleaseVo.getSchoolYear().equals("")){
            LocalDate now = LocalDate.now();
            String schoolYear = semesterSettingMapper.getSemesterByCurrentDate(now);
            taskReleaseVo.setSchoolYear(schoolYear);
        }
        User user = userMapper.getUserById(userId);
        Long userIdbyName = userMapper.getUserIdbyName(taskReleaseVo.getNickname());
        taskReleaseVo.setUserId(userIdbyName);
        if(1 == user.getIsAdmin()){
            taskMapper.releaseTask(taskReleaseVo);
            summaryService.autoUpdateWeeklySummary(userIdbyName,taskReleaseVo.getTaskWeek(),taskReleaseVo.getSchoolYear());
        }else {
            throw new UserException(UserErrorCode.A0300);
        }
    }

    @Override
    public TaskListVo showTaskById(Integer taskId) {
        return taskMapper.showTaskById(taskId);
    }
}
