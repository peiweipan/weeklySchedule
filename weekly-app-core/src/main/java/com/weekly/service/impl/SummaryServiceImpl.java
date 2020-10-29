package com.weekly.service.impl;

import com.firefly.errcode.enums.UserErrorCode;
import com.firefly.errcode.exception.UserException;
import com.weekly.mapper.SummaryMapper;
import com.weekly.mapper.TaskMapper;
import com.weekly.mapper.UserMapper;
import com.weekly.mapper.WorkDaysSettingMapper;
import com.weekly.service.SemesterService;
import com.weekly.service.SummaryService;
import com.weekly.service.TaskService;
import com.weekly.service.UserService;
import com.weekly.user.pojo.po.User;
import com.weekly.user.pojo.po.WeeklySummary;
import com.weekly.user.pojo.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SummaryServiceImpl implements SummaryService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private SummaryMapper summaryMapper;

    @Autowired
    private WorkDaysSettingMapper workDaysSettingMapper;

    @Autowired
    private SemesterService semesterService;

    @Override
    public WeeklySummaryVo viewWeeklySummary(String nickname, Integer taskWeek,String schoolYear) {
        Long userIdbyName = userMapper.getUserIdbyName(nickname);
        if(schoolYear == null || schoolYear.equals("")){
            schoolYear = semesterService.getSemesterByCurrentDate();
        }
        WeeklySummaryVo weeklySummaryVo = summaryMapper.viewWeeklySummary(userIdbyName,taskWeek,schoolYear);
        if(null == weeklySummaryVo){
            WeeklySummaryVo weeklySummaryVo1 = new WeeklySummaryVo();
            weeklySummaryVo1.setFinishQuality(0);
            weeklySummaryVo1.setJobCount(0);
            weeklySummaryVo1.setJobDescription("无");
            weeklySummaryVo1.setJobLevel(0);
            weeklySummaryVo1.setOffDays(0);
            weeklySummaryVo1.setOvertimeDays(0);
            weeklySummaryVo1.setWorkPerformance(0);
            weeklySummaryVo1.setWorkDays(5);
            return weeklySummaryVo1;
        }
        weeklySummaryVo.setJobDescription("无");
        return weeklySummaryVo;
    }

    @Override
    public List<WeeklySummaryVo> viewAllWeeklySummary(Integer taskWeek,Long currentUserId,String schoolYear) {
        List<WeeklySummaryVo> weeklySummaryVos = new ArrayList<>();
        User user = userMapper.getUserById(currentUserId);

        if(schoolYear == null || schoolYear.equals("")){
            schoolYear = semesterService.getSemesterByCurrentDate();
        }
        if(0 == user.getIsAdmin()){
            List<Long> allGeneralUserId = userMapper.getAllGeneralUserId();
            weeklySummaryVos = summaryMapper.viewAllGeneralUserWeeklySummary(
                    taskWeek,schoolYear, allGeneralUserId);
        }else {
            weeklySummaryVos = summaryMapper.viewAllWeeklySummary(taskWeek,schoolYear);
        }
        if(null == weeklySummaryVos || weeklySummaryVos.size() <= 0) {
            return null;
        }else {
            List<Long> userIds = weeklySummaryVos.stream().map(WeeklySummaryVo::getUserId).collect(Collectors.toList());
            List<User> userList = userService.getUserByIds(userIds);
            Map<Long, User> userMap = userList.stream().collect(Collectors.toMap(User::getId, Function.identity()));

            for (WeeklySummaryVo weeklySummaryVo : weeklySummaryVos) {
                User u = userMap.get(weeklySummaryVo.getUserId());
                if (u == null) {
                    continue;
                }
                weeklySummaryVo.setNickname(u.getNickname());
            }
            return weeklySummaryVos;
        }
    }

    @Override
    public SummaryPerformanceVo viewSummaryPerformance(Long year,Long currentUserId) {
        User user = userMapper.getUserById(currentUserId);
        String first = year-1 + "第二学期";
        String second = year + "第一学期";

        Integer firstHalfYearOffDays,firstHalfYearOvertimeDays,firstHalfYearPerformance;
        HalfPerformanceVo halfPerformanceVo = summaryMapper.sumPerformanceByYear(currentUserId, first);
        if(null != halfPerformanceVo){
            if(null == halfPerformanceVo.getHalfYearOffDays()){
                firstHalfYearOffDays = 0;
            }else {
                firstHalfYearOffDays = halfPerformanceVo.getHalfYearOffDays();
            }
            if(null == halfPerformanceVo.getHalfYearOvertimeDays()){
                firstHalfYearOvertimeDays = 0;
            }else {
                firstHalfYearOvertimeDays = halfPerformanceVo.getHalfYearOvertimeDays();
            }
            if(null == halfPerformanceVo.getHalfYearPerformance()){
                firstHalfYearPerformance = 0;
            }else {
                firstHalfYearPerformance = halfPerformanceVo.getHalfYearPerformance();
            }
        }else {
            firstHalfYearOffDays = 0;
            firstHalfYearOvertimeDays = 0;
            firstHalfYearPerformance = 0;
        }

        Integer SecondHalfYearOffDays,SecondHalfYearOvertimeDays,SecondHalfYearPerformance;
        HalfPerformanceVo halfPerformanceVo1 = summaryMapper.sumPerformanceByYear(currentUserId, second);
        if(null != halfPerformanceVo1){
            if(null == halfPerformanceVo1.getHalfYearOffDays()){
                SecondHalfYearOffDays = 0;
            }else {
                SecondHalfYearOffDays = halfPerformanceVo1.getHalfYearOffDays();
            }
            if(null == halfPerformanceVo1.getHalfYearOvertimeDays()){
                SecondHalfYearOvertimeDays = 0;
            }else {
                SecondHalfYearOvertimeDays = halfPerformanceVo1.getHalfYearOvertimeDays();
            }
            if(null == halfPerformanceVo1.getHalfYearPerformance()){
                SecondHalfYearPerformance = 0;
            }else {
                SecondHalfYearPerformance = halfPerformanceVo1.getHalfYearPerformance();
            }
        }else {
            SecondHalfYearOffDays = 0;
            SecondHalfYearOvertimeDays = 0;
            SecondHalfYearPerformance = 0;
        }

        SummaryPerformanceVo summaryPerformanceVo = new SummaryPerformanceVo();
        summaryPerformanceVo.setFirstHalfYearOffDays(firstHalfYearOffDays);
        summaryPerformanceVo.setFirstHalfYearOvertimeDays(firstHalfYearOvertimeDays);
        summaryPerformanceVo.setFirstHalfYearPerformance(firstHalfYearPerformance);
        summaryPerformanceVo.setSecondHalfYearOffDays(SecondHalfYearOffDays);
        summaryPerformanceVo.setSecondHalfYearOvertimeDays(SecondHalfYearOvertimeDays);
        summaryPerformanceVo.setSecondHalfYearPerformance(SecondHalfYearPerformance);
        summaryPerformanceVo.setTotalOffDays(firstHalfYearOffDays+SecondHalfYearOffDays);
        summaryPerformanceVo.setTotalOvertimeDays(firstHalfYearOvertimeDays+SecondHalfYearOvertimeDays);
        summaryPerformanceVo.setTotalPerformance(firstHalfYearPerformance+SecondHalfYearPerformance);

        return summaryPerformanceVo;
    }

    @Override
    @Async
    @Transactional(rollbackFor = Exception.class)
    public void autoUpdateWeeklySummary(Long userId, Integer taskWeek,String schoolYear) {
        WeeklySummaryVo weeklySummary = getWeeklySummary(userId, taskWeek, schoolYear);
        WeeklySummaryVo weeklySummaryVo = computeSummaryParam(userId,taskWeek,schoolYear,weeklySummary);
        weeklySummaryVo.setSchoolYear(schoolYear);
        summaryMapper.autoUpdateWeeklySummary(weeklySummaryVo);
    }

    @Override
    public void updateWeeklySummaryByUser(WeeklySummaryUpdateVo weeklySummaryUpdateVo,Long currentUserId) {
        if(weeklySummaryUpdateVo.getSchoolYear() == null || weeklySummaryUpdateVo.getSchoolYear().equals("")){
            weeklySummaryUpdateVo.setSchoolYear(semesterService.getSemesterByCurrentDate());
        }
        if(currentUserId.equals(weeklySummaryUpdateVo.getUserId())){
            WeeklySummaryVo weeklySummaryVo = summaryMapper.viewWeeklySummary(
                    weeklySummaryUpdateVo.getUserId(),weeklySummaryUpdateVo.getTaskWeek(),weeklySummaryUpdateVo.getSchoolYear());
            if(null == weeklySummaryVo){
                throw new RuntimeException("本周没有任务信息，无法修改综合信息");
            }else{
                weeklySummaryUpdateVo.setOffDays(weeklySummaryUpdateVo.getOffDays()*100);
                weeklySummaryUpdateVo.setOvertimeDays(weeklySummaryUpdateVo.getOvertimeDays()*100);
                summaryMapper.updateWeeklySummaryByUser(weeklySummaryUpdateVo);
            }
        }else {
            throw new UserException(UserErrorCode.A0300);
        }
    }


    @Override
    public WeeklySummaryVo getWeeklySummary(Long userId, Integer taskWeek,String schoolYear) {
        Integer workdays;
        WeeklySummaryVo weeklySummary = summaryMapper.viewWeeklySummary(userId, taskWeek,schoolYear);
        if(null == weeklySummary){
            if(null != workDaysSettingMapper.getWorkDaysBySetting(schoolYear,taskWeek)){
                workdays = workDaysSettingMapper.getWorkDaysBySetting(schoolYear,taskWeek);
            }else {
                workdays = 5;
            }
            summaryMapper.createEmptySummary(userId,taskWeek,schoolYear,workdays);
            WeeklySummaryVo weeklySummary2 = summaryMapper.viewWeeklySummary(userId, taskWeek, schoolYear);
            return weeklySummary2;
        }
        return weeklySummary;
    }

    @Override
    public WeeklySummaryVo computeSummaryParam(Long userId, Integer taskWeek,String schoolYear,WeeklySummaryVo weeklySummaryVo) {
        Long finishCount = taskMapper.finishTaskCount(userId, taskWeek,schoolYear);
//        System.out.println(finishCount);

        Long allCount = taskMapper.allTaskCount(userId, taskWeek,schoolYear);
//        System.out.println(allCount);

//        WeeklySummaryVo weeklySummaryVo =new WeeklySummaryVo();
        weeklySummaryVo.setTaskWeek(taskWeek);
        weeklySummaryVo.setUserId(userId);
        double finishQuality = (double)finishCount/(double)allCount;
//        System.out.println(finishQuality);
        weeklySummaryVo.setFinishQuality((int)(finishQuality*100));

        double jobCount = (double)allCount/5;
//        System.out.println(jobCount);
        weeklySummaryVo.setJobCount((int)(jobCount*100));
                                                              //getFinishTaskLevelList -> getTaskLevelList
        List<TaskLevelListVo> finishTaskLevelList = taskMapper.getTaskLevelList(userId, taskWeek,schoolYear);
        Map<Long, Long> collect = finishTaskLevelList.stream().collect(
                Collectors.groupingBy(TaskLevelListVo::getTaskLevel, Collectors.counting()));
//        System.out.println(collect);
        double factor = 1;

        if(collect.containsKey(0L)){
            Long count = collect.get(0L);

            if(null != count){
                for (int i = 0; i <count ; i++) {
                    factor = factor * 1;
                }
            }
        }
        if(collect.containsKey(1L)){
            Long count = collect.get(1L);

            if(null != count){
                for (int i = 0; i <count ; i++) {
                    factor = factor * 1.2;
                }
            }
        }
        if(collect.containsKey(2L)){
            Long count = collect.get(2L);

            if(null != count){
                for (int i = 0; i <count ; i++) {
                    factor = factor * 1.4;
                }
            }
        }
        if(collect.containsKey(3L)){
            Long count = collect.get(3L);

            if(null != count){
                for (int i = 0; i <count ; i++) {
                    factor = factor * 1.6;
                }
            }
        }
//        System.out.println(factor);
        weeklySummaryVo.setJobLevel((int)(factor *100));
        double performance = finishQuality * jobCount *factor;
        weeklySummaryVo.setWorkPerformance((int)(performance*100));
        return weeklySummaryVo;
    }

}
