package com.weekly.summary.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weekly.common.exception.BaseException;
import com.weekly.common.util.PaginationUtils;
import com.weekly.common.utils.CalendarUtils;
import com.weekly.common.utils.ServletUtils;
import com.weekly.common.utils.poi.ExcelUtil;
import com.weekly.framework.security.LoginUser;
import com.weekly.framework.security.service.TokenService;
import com.weekly.framework.web.domain.AjaxResult;
import com.weekly.semester.service.SemesterSettingService;
import com.weekly.summary.mapper.SummaryMapper;
import com.weekly.summary.pojo.vo.*;
import com.weekly.summary.service.SummaryService;
import com.weekly.task.mapper.TaskMapper;
import com.weekly.task.pojo.vo.TaskLevelListVo;
import com.weekly.user.mapper.UserMapper;
import com.weekly.user.pojo.po.User;
import com.weekly.user.pojo.vo.UserListVo;
import com.weekly.user.service.UserService;
import com.weekly.workdays.mapper.WorkDaysSettingMapper;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SummaryServiceImpl implements SummaryService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SummaryMapper summaryMapper;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private SemesterSettingService semesterSettingService;

    @Autowired
    private WorkDaysSettingMapper workDaysSettingMapper;


    @Override
    public HomePageInfoVo showHomePageInfoVo() throws ParseException {

        YearAndWeekByNowVo infoByNow = getInfoByNow();
        Long userId = infoByNow.getUserId();
        String schoolYear = infoByNow.getSchoolYear();
        Integer taskWeek = infoByNow.getTaskWeek();

        HomePageInfoVo homePageInfoVo = new HomePageInfoVo();
        Long finishTaskCount = taskMapper.finishTaskCount(userId, taskWeek, schoolYear);
        Long allTaskCount = taskMapper.allTaskCount(userId, taskWeek, schoolYear);
        WeeklySummaryVo weeklySummaryVo = summaryMapper.viewWeeklySummary(userId, taskWeek, schoolYear);
        if (null == weeklySummaryVo) {
            homePageInfoVo.setOvertimeDays(0);
        } else {
            homePageInfoVo.setOvertimeDays(weeklySummaryVo.getOvertimeDays());
        }
        homePageInfoVo.setFinishCount(finishTaskCount);
        homePageInfoVo.setAllCount(allTaskCount);
        homePageInfoVo.setWeeks(taskWeek);
        return homePageInfoVo;
    }


    @Override
    @Async
    @Transactional(rollbackFor = Exception.class)
    public void autoUpdateWeeklySummary(Long userId, int taskWeek, String schoolYear) {
        WeeklySummaryVo weeklySummary = getWeeklySummary(userId, taskWeek, schoolYear);
        WeeklySummaryVo weeklySummaryVo = computeSummaryParam(userId, taskWeek, schoolYear,weeklySummary);
        weeklySummaryVo.setSchoolYear(schoolYear);
        summaryMapper.autoUpdateWeeklySummary(weeklySummaryVo);
    }

    @Override
    public WeeklySummaryVo getWeeklySummary(Long userId, int taskWeek, String schoolYear) {
        Integer workdays;
        WeeklySummaryVo weeklySummary = summaryMapper.viewWeeklySummary(userId, taskWeek, schoolYear);
        if (null == weeklySummary) {
            if (null != workDaysSettingMapper.getWorkDaysBySetting(schoolYear, taskWeek)) {
                workdays = workDaysSettingMapper.getWorkDaysBySetting(schoolYear, taskWeek);
            } else {
                workdays = 5;
            }
            summaryMapper.createEmptySummary(userId, taskWeek, schoolYear, workdays);
            WeeklySummaryVo weeklySummary2 = summaryMapper.viewWeeklySummary(userId, taskWeek, schoolYear);
            return weeklySummary2;
        }
        return weeklySummary;
    }

    @Override
    public WeeklySummaryVo computeSummaryParam(Long userId, Integer taskWeek, String schoolYear, WeeklySummaryVo weeklySummaryVo) {
        Long finishCount = taskMapper.finishTaskCount(userId, taskWeek, schoolYear);
//        System.out.println(finishCount);

        Long allCount = taskMapper.allTaskCount(userId, taskWeek, schoolYear);
//        System.out.println(allCount);

//        WeeklySummaryVo weeklySummaryVo = new WeeklySummaryVo();
        weeklySummaryVo.setTaskWeek(taskWeek);
        weeklySummaryVo.setUserId(userId);
        double finishQuality = (double) finishCount / (double) allCount;
//        System.out.println(finishQuality);
        weeklySummaryVo.setFinishQuality((int) (finishQuality * 100));

        double jobCount = (double) allCount / 5;
//        System.out.println(jobCount);
        weeklySummaryVo.setJobCount((int) (jobCount * 100));
                                                              //getFinishTaskLevelList -> getTaskLevelList
        List<TaskLevelListVo> finishTaskLevelList = taskMapper.getTaskLevelList(userId, taskWeek, schoolYear);
        Map<Long, Long> collect = finishTaskLevelList.stream().collect(
                Collectors.groupingBy(TaskLevelListVo::getTaskLevel, Collectors.counting()));
//        System.out.println(collect);
        double factor = 1;

        if (collect.containsKey(0L)) {
            Long count = collect.get(0L);

            if (null != count) {
                for (int i = 0; i < count; i++) {
                    factor = factor * 1;
                }
            }
        }
        if (collect.containsKey(1L)) {
            Long count = collect.get(1L);

            if (null != count) {
                for (int i = 0; i < count; i++) {
                    factor = factor * 1.2;
                }
            }
        }
        if (collect.containsKey(2L)) {
            Long count = collect.get(2L);

            if (null != count) {
                for (int i = 0; i < count; i++) {
                    factor = factor * 1.4;
                }
            }
        }
        if (collect.containsKey(3L)) {
            Long count = collect.get(3L);

            if (null != count) {
                for (int i = 0; i < count; i++) {
                    factor = factor * 1.6;
                }
            }
        }
//        System.out.println(factor);
        weeklySummaryVo.setJobLevel((int) (factor * 100));
        double performance = finishQuality * jobCount * factor;
        weeklySummaryVo.setWorkPerformance((int) (performance * 100));
        return weeklySummaryVo;
    }

    @Override
    public ALLYearPerformanceVo showWeeklyPerformance(Integer year) {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        Long userId = loginUser.getUser().getUserId();

        if (null == year) {
            Calendar cal2 = Calendar.getInstance();
            year = cal2.get(Calendar.YEAR);
        }
        String first = year - 1 + "第二学期";
        String second = year + "第一学期";

        List<HalfYearPerformanceVo> firstHalfYear = summaryMapper.getWeeklyPerformanceHalfYear(userId, first);
        List<HalfYearPerformanceVo> secondHalfYear = summaryMapper.getWeeklyPerformanceHalfYear(userId, second);

        ALLYearPerformanceVo allYearPerformanceVo = new ALLYearPerformanceVo();
        allYearPerformanceVo.setFirstHalfYear(firstHalfYear);
        allYearPerformanceVo.setSecondHalfYear(secondHalfYear);
        return allYearPerformanceVo;
    }

    @Override
    public WeeklySummaryVo viewWeeklySummary() throws ParseException {

        YearAndWeekByNowVo infoByNow = getInfoByNow();
        WeeklySummaryVo weeklySummaryVo = summaryMapper.viewWeeklySummary(
                infoByNow.getUserId(), infoByNow.getTaskWeek(), infoByNow.getSchoolYear());
        if (null == weeklySummaryVo) {
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
        return weeklySummaryVo;
    }

    @Override
    public void updateWeeklySummaryByUser(WeeklySummaryUpdateVo weeklySummaryUpdateVo) throws ParseException {
        YearAndWeekByNowVo infoByNow = getInfoByNow();
        weeklySummaryUpdateVo.setUserId(infoByNow.getUserId());
        weeklySummaryUpdateVo.setTaskWeek(infoByNow.getTaskWeek());
        weeklySummaryUpdateVo.setSchoolYear(infoByNow.getSchoolYear());
        WeeklySummaryVo weeklySummaryVo = summaryMapper.viewWeeklySummary(
                weeklySummaryUpdateVo.getUserId(), weeklySummaryUpdateVo.getTaskWeek(), weeklySummaryUpdateVo.getSchoolYear());
        if (null == weeklySummaryVo) {
            throw new BaseException("本周没有任务信息，无法修改综合信息");
        } else {
            weeklySummaryUpdateVo.setOffDays(weeklySummaryUpdateVo.getOffDays() * 100);
            weeklySummaryUpdateVo.setOvertimeDays(weeklySummaryUpdateVo.getOvertimeDays() * 100);
            summaryMapper.updateWeeklySummaryByUser(weeklySummaryUpdateVo);
        }
    }

    @Override
    public AllYearInfoVo viewAllYearSummaryInfo(Long year) {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        Long userId = loginUser.getUser().getUserId();

        String first = year - 1 + "第二学期";
        String second = year + "第一学期";

        Integer firstHalfYearOffDays, firstHalfYearOvertimeDays, firstHalfYearPerformance;
        HalfYearInfoVo halfYearInfoVo = summaryMapper.sumPerformanceByYear(userId, first);
        if (null != halfYearInfoVo) {
            if (null == halfYearInfoVo.getHalfYearOffDays()) {
                firstHalfYearOffDays = 0;
            } else {
                firstHalfYearOffDays = halfYearInfoVo.getHalfYearOffDays();
            }
            if (null == halfYearInfoVo.getHalfYearOvertimeDays()) {
                firstHalfYearOvertimeDays = 0;
            } else {
                firstHalfYearOvertimeDays = halfYearInfoVo.getHalfYearOvertimeDays();
            }
            if (null == halfYearInfoVo.getHalfYearPerformance()) {
                firstHalfYearPerformance = 0;
            } else {
                firstHalfYearPerformance = halfYearInfoVo.getHalfYearPerformance();
            }
        } else {
            firstHalfYearOffDays = 0;
            firstHalfYearOvertimeDays = 0;
            firstHalfYearPerformance = 0;
        }

        Integer SecondHalfYearOffDays, SecondHalfYearOvertimeDays, SecondHalfYearPerformance;
        HalfYearInfoVo halfYearInfoVo1 = summaryMapper.sumPerformanceByYear(userId, second);
        if (null != halfYearInfoVo1) {
            if (null == halfYearInfoVo1.getHalfYearOffDays()) {
                SecondHalfYearOffDays = 0;
            } else {
                SecondHalfYearOffDays = halfYearInfoVo1.getHalfYearOffDays();
            }
            if (null == halfYearInfoVo1.getHalfYearOvertimeDays()) {
                SecondHalfYearOvertimeDays = 0;
            } else {
                SecondHalfYearOvertimeDays = halfYearInfoVo1.getHalfYearOvertimeDays();
            }
            if (null == halfYearInfoVo1.getHalfYearPerformance()) {
                SecondHalfYearPerformance = 0;
            } else {
                SecondHalfYearPerformance = halfYearInfoVo1.getHalfYearPerformance();
            }
        } else {
            SecondHalfYearOffDays = 0;
            SecondHalfYearOvertimeDays = 0;
            SecondHalfYearPerformance = 0;
        }
        AllYearInfoVo allYearInfoVo = new AllYearInfoVo();
        allYearInfoVo.setFirstHalfYearOffDays(firstHalfYearOffDays);
        allYearInfoVo.setFirstHalfYearOvertimeDays(firstHalfYearOvertimeDays);
        allYearInfoVo.setFirstHalfYearPerformance(firstHalfYearPerformance);
        allYearInfoVo.setSecondHalfYearOffDays(SecondHalfYearOffDays);
        allYearInfoVo.setSecondHalfYearOvertimeDays(SecondHalfYearOvertimeDays);
        allYearInfoVo.setSecondHalfYearPerformance(SecondHalfYearPerformance);
        allYearInfoVo.setTotalOffDays(firstHalfYearOffDays + SecondHalfYearOffDays);
        allYearInfoVo.setTotalOvertimeDays(firstHalfYearOvertimeDays + SecondHalfYearOvertimeDays);
        allYearInfoVo.setTotalPerformance(firstHalfYearPerformance + SecondHalfYearPerformance);
        return allYearInfoVo;
    }


    @Override
    public List<FinishQualityVo> viewFinishQuality(Integer startWeek, Integer endWeek, Page page) {
        List<FinishQualityVo> finishQualityVos = new ArrayList<>();

        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        Long userId = loginUser.getUser().getUserId();
        User user = userMapper.getUserById(userId);
        List<Long> allGeneralUserIds = new ArrayList<>();
        if (0 == user.getIsAdmin()) {
            allGeneralUserIds = userMapper.getAllGeneralUserId();
        }

        String schoolYear = semesterSettingService.getSemesterByCurrentDate();

        finishQualityVos = summaryMapper.viewFinishQualityByWeek(startWeek, endWeek, schoolYear, allGeneralUserIds);
//        System.out.println(finishQualityVos);

        Page<UserListVo> userListVoPage = userService.showUserList(userId, (Page<UserListVo>) page);
        List<UserListVo> userList = userListVoPage.getRecords();
//        System.out.println(userList);
        Map<Integer, List<FinishQualityVo>> userFinishMap = finishQualityVos.stream().collect(Collectors.groupingBy(FinishQualityVo::getUserId, Collectors.toList()));
//        System.out.println(userFinishMap);

        List<FinishQualityVo> finishQualityVos1 = userList.stream()
                .map(userListVo -> userFinishMap.getOrDefault(userListVo.getUserId(), Collections.singletonList(
                        new FinishQualityVo(userListVo.getUserId(), userListVo.getNickname(), startWeek, 0)
                        )
                ))
                .flatMap(List::stream)
                .collect(Collectors.toList());
//        System.out.println(finishQualityVos1);
        return finishQualityVos1;
    }

    @Override
    public List<FinishQualityArrayVo> viewFinishQualityByWeek(Integer startWeek, Integer endWeek, Page page) {
        String schoolYear = semesterSettingService.getSemesterByCurrentDate();
        if (startWeek == null || endWeek == null) {
            startWeek = 1;
            endWeek = semesterSettingService.getSemesterWeeks(schoolYear);
        }
        if (startWeek > endWeek) {
            throw new BaseException("起始周数比结束周数大，请重新选择");
        }
        List<FinishQualityVo> finishQualityVos = viewFinishQuality(startWeek, endWeek, page);
//        System.out.println(finishQualityVos);
        Map<String, List<FinishQualityVo>> userFinishQualityMap = finishQualityVos.stream()
                .collect(Collectors.groupingBy(
                        FinishQualityVo::getNickname,
                        Collectors.toList()
                ));
//        System.out.println(userFinishQualityMap);
        Integer finalEndWeek = endWeek;
        Integer finalStartWeek = startWeek;
        List<FinishQualityArrayVo> finishQualityArrayVoList = userFinishQualityMap.entrySet().stream()
                .map(entry -> {
                    int[] totalWeek = new int[finalEndWeek - finalStartWeek + 1];
                    Arrays.fill(totalWeek, 0);
                    List<FinishQualityVo> value = entry.getValue();
                    for (FinishQualityVo finishQualityVo : value) {
                        totalWeek[finishQualityVo.getTaskWeek() - finalStartWeek] = finishQualityVo.getFinishQuality();
                    }
                    return new FinishQualityArrayVo(entry.getKey(), totalWeek); }
                    )
                .collect(Collectors.toList());
//        System.out.println(finishQualityArrayVoList);
        return finishQualityArrayVoList;
    }

    @Override
    public List<WeeklyPerformanceArrayVo> viewWorkPerformanceByWeek(Integer startWeek, Integer endWeek,Page page) {
        String schoolYear = semesterSettingService.getSemesterByCurrentDate();
        if (startWeek == null || endWeek == null) {
            startWeek = 1;
            endWeek = semesterSettingService.getSemesterWeeks(schoolYear);
        }
        if (startWeek > endWeek) {
            throw new BaseException("起始周数比结束周数大，请重新选择");
        }
//        System.out.println(startWeek);
//        System.out.println(endWeek);

        List<WeeklyPerformanceVo> weeklyPerformanceVos = viewWorkPerformance(startWeek, endWeek,page);
        Map<String, List<WeeklyPerformanceVo>> userWeeklyPerformanceMap = weeklyPerformanceVos.stream()
                .collect(Collectors.groupingBy(
                        WeeklyPerformanceVo::getNickname,
                        Collectors.toList()
                ));

        Integer finalEndWeek = endWeek;
        Integer finalStartWeek = startWeek;
        List<WeeklyPerformanceArrayVo> weeklyPerformanceArrayVoList = userWeeklyPerformanceMap.entrySet().stream()
                .map(entry -> {
                    int[] totalWeek = new int[finalEndWeek - finalStartWeek + 1];
                    Arrays.fill(totalWeek, 0);
                    for (WeeklyPerformanceVo weeklyPerformanceVo : entry.getValue()) {
                        totalWeek[weeklyPerformanceVo.getTaskWeek() - finalStartWeek] = weeklyPerformanceVo.getWorkPerformance();
                    }
                    return new WeeklyPerformanceArrayVo(entry.getKey(), totalWeek);
                })
                .collect(Collectors.toList());
        return weeklyPerformanceArrayVoList;
    }

    @Override
    public List<WeeklyPerformanceVo> viewWorkPerformance(Integer startWeek, Integer endWeek,Page page) {
        List<WeeklyPerformanceVo> weeklyPerformanceVos = new ArrayList<>();

        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        Long userId = loginUser.getUser().getUserId();
        User user = userMapper.getUserById(userId);
        List<Long> allGeneralUserIds = new ArrayList<>();
        if (0 == user.getIsAdmin()) {
            allGeneralUserIds = userMapper.getAllGeneralUserId();
        }

        String schoolYear = semesterSettingService.getSemesterByCurrentDate();

        weeklyPerformanceVos = summaryMapper.viewWeeklyPerformanceByWeek(startWeek, endWeek, schoolYear, allGeneralUserIds);

        Page<UserListVo> userListVoPage = userService.showUserList(userId, (Page<UserListVo>) page);
        List<UserListVo> userList = userListVoPage.getRecords();
//        System.out.println(userList);
        Map<Integer, List<WeeklyPerformanceVo>> userPerformanceMap = weeklyPerformanceVos.stream().collect(Collectors.groupingBy(WeeklyPerformanceVo::getUserId, Collectors.toList()));
//        System.out.println(userPerformanceMap);

        List<WeeklyPerformanceVo> weeklyPerformanceVos1 = userList.stream()
                .map(userListVo -> userPerformanceMap.getOrDefault(userListVo.getUserId(), Collections.singletonList(
                        new WeeklyPerformanceVo(userListVo.getUserId(), userListVo.getNickname(), startWeek, 0)
                        )
                ))
                .flatMap(List::stream)
                .collect(Collectors.toList());
//        System.out.println(weeklyPerformanceVos1);

        return weeklyPerformanceVos1;
    }

//    @Override
//    public AjaxResult exportFinishQuality(Integer startWeek, Integer endWeek,Page page) {
//        String schoolYear = semesterSettingService.getSemesterByCurrentDate();
//        if (startWeek == null) {
//            startWeek = 1;
//            endWeek = semesterSettingService.getSemesterWeeks(schoolYear);
//        }
//
//        List<FinishQualityVo> finishQualityVos = viewFinishQuality(startWeek, endWeek,page);
//        ExcelUtil<FinishQualityVo> util = new ExcelUtil<FinishQualityVo>(FinishQualityVo.class);
//
//        Integer finalStartWeek = startWeek;
//        Integer finalEndWeek = endWeek;
////        根据名称分组 并过滤所需周之外的数据
//        Map<String, List<FinishQualityVo>> userFinishQualityMap = finishQualityVos.stream()
//                .filter(finishQualityVo -> finishQualityVo.getTaskWeek() >= finalStartWeek || finishQualityVo.getTaskWeek() <= finalEndWeek)
//                .collect(Collectors.groupingBy(
//                        FinishQualityVo::getNickname,
//                        Collectors.toList()
//                ));
//
//        Set<String> userNameList = userFinishQualityMap.keySet();
//        ArrayList<Map<String, Object>> excelList = new ArrayList<>(userNameList.size());
//
//        for (String userName : userNameList) {
//            HashMap<String, Object> voMap = new LinkedHashMap<>();
//            List<FinishQualityVo> finishQualityVoList = userFinishQualityMap.get(userName);
//            Map<Integer, FinishQualityVo> taskWeekMap = finishQualityVoList.stream()
//                    .collect(Collectors.toMap(FinishQualityVo::getTaskWeek, Function.identity(), (k1, k2) -> k2));
//            voMap.put("姓名", userName);
//            for (int i = startWeek; i <= endWeek; i++) {
//                FinishQualityVo weekVo = taskWeekMap.get(i);
//                voMap.put(String.format("第%d周", i), weekVo == null ? 0 : weekVo.getFinishQuality());
//            }
//            excelList.add(voMap);
//        }
//
//        return util.exportExcelByMap(excelList, "FinishQuality");
//    }


    public YearAndWeekByNowVo getInfoByNow() throws ParseException {
        String schoolYear = semesterSettingService.getSemesterByCurrentDate();

        String startDateBySchoolYear = semesterSettingService.getStartDateBySchoolYear(schoolYear);
        int weeksByStartDate = CalendarUtils.computeWeeks(startDateBySchoolYear);
        LocalDate now = LocalDate.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String nowDays = now.format(fmt);
        int weeksByNow = CalendarUtils.computeWeeks(nowDays);
        int taskWeek = weeksByNow - weeksByStartDate + 1;

        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        Long userId = loginUser.getUser().getUserId();

        YearAndWeekByNowVo yearAndWeekByNowVo = new YearAndWeekByNowVo();
        yearAndWeekByNowVo.setSchoolYear(schoolYear);
        yearAndWeekByNowVo.setTaskWeek(taskWeek);
        yearAndWeekByNowVo.setUserId(userId);
        return yearAndWeekByNowVo;
    }
}
