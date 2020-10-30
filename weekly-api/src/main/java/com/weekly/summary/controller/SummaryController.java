package com.weekly.summary.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weekly.common.form.PageParamsForm;
import com.weekly.common.util.PaginationUtils;
import com.weekly.common.utils.ServletUtils;
import com.weekly.common.utils.poi.ExcelUtil;
import com.weekly.framework.aspectj.lang.annotation.Log;
import com.weekly.framework.aspectj.lang.enums.BusinessType;
import com.weekly.framework.security.LoginUser;
import com.weekly.framework.security.service.TokenService;
import com.weekly.framework.web.domain.AjaxResult;
import com.weekly.semester.service.SemesterSettingService;
import com.weekly.summary.pojo.vo.*;
import com.weekly.summary.service.SummaryService;
import com.weekly.task.pojo.po.Task;
import com.weekly.user.pojo.vo.UserListVo;
import com.weekly.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/summary")
@Api(description = "本周综合信息", tags = {"本周综合信息"})
public class SummaryController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    @Autowired
    private SummaryService summaryService;

    @Autowired
    private SemesterSettingService semesterSettingService;

    @GetMapping("/homePageInfo")
    @ApiOperation("获取首页个人加班信息等")
    public AjaxResult showHomePageInfoVo() throws ParseException {
        return AjaxResult.success(summaryService.showHomePageInfoVo());
    }


    @GetMapping("/WeeklyPerformance")
    @ApiOperation("获取上下半年每周绩效")
    public AjaxResult showWeeklyPerformance(Integer year) {
        return AjaxResult.success(summaryService.showWeeklyPerformance(year));
    }

    @GetMapping("/view")
    @ApiOperation("查看本周综合")
    public AjaxResult viewWeeklySummary() throws ParseException {
        return AjaxResult.success(summaryService.viewWeeklySummary());
    }

    @PutMapping("/updateSummary")
    @ApiOperation("用户修改本周综合")
    public AjaxResult updateWeeklySummaryByUser(@RequestBody WeeklySummaryUpdateVo weeklySummaryUpdateVo) throws ParseException {
        summaryService.updateWeeklySummaryByUser(weeklySummaryUpdateVo);
        return AjaxResult.success();
    }

    @GetMapping("/viewAllYearInfo")
    @ApiOperation("学年综合")
    public AjaxResult viewAllYearSummaryInfo(Long year) {
        return AjaxResult.success(summaryService.viewAllYearSummaryInfo(year));
    }

    @Log(title = "导出学年综合信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出学年综合信息")
    public AjaxResult export(Long year) {
        AllYearInfoVo allYearInfoVo = summaryService.viewAllYearSummaryInfo(year);
        allYearInfoVo.setFirstHalfYearOffDays(allYearInfoVo.getFirstHalfYearOffDays()/100);
        allYearInfoVo.setFirstHalfYearOvertimeDays(allYearInfoVo.getFirstHalfYearOvertimeDays()/100);
        allYearInfoVo.setFirstHalfYearPerformance(allYearInfoVo.getFirstHalfYearPerformance()/100);
        allYearInfoVo.setSecondHalfYearOffDays(allYearInfoVo.getSecondHalfYearOffDays()/100);
        allYearInfoVo.setSecondHalfYearOvertimeDays(allYearInfoVo.getSecondHalfYearOvertimeDays()/100);
        allYearInfoVo.setSecondHalfYearPerformance(allYearInfoVo.getSecondHalfYearPerformance()/100);
        allYearInfoVo.setTotalOffDays(allYearInfoVo.getTotalOffDays()/100);
        allYearInfoVo.setTotalOvertimeDays(allYearInfoVo.getTotalOvertimeDays()/100);
        allYearInfoVo.setTotalPerformance(allYearInfoVo.getTotalPerformance()/100);
        ArrayList<AllYearInfoVo> list = new ArrayList<>(1);
        list.add(allYearInfoVo);
        ExcelUtil<AllYearInfoVo> util = new ExcelUtil<AllYearInfoVo>(AllYearInfoVo.class);
        return util.exportExcel(list, "summaryInfo");
    }

    @GetMapping("/finishQuality")
    @ApiOperation("按周数查询完成质量")
    public AjaxResult viewFinishQualityByWeek(Integer startWeek, Integer endWeek, PageParamsForm pageParamsForm) {
        Page<FinishQualityArrayVo> finishQualityArrayVoPage = PaginationUtils.fromGrid(pageParamsForm);
        List<FinishQualityArrayVo> finishQualityArrayVos = summaryService.viewFinishQualityByWeek(startWeek, endWeek, finishQualityArrayVoPage);
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        Long userId = loginUser.getUser().getUserId();
        List<UserListVo> userListVos = userService.showUserList(userId);
        return AjaxResult.success(finishQualityArrayVos,userListVos.size());
    }

    @GetMapping("/exportFinishQuality")
    @ApiOperation("导出完成质量")
    public AjaxResult exportFinishQuality(Integer startWeek, Integer endWeek,PageParamsForm pageParamsForm) {
//        return summaryService.exportFinishQuality(startWeek,endWeek);

        Page<UserListVo> UserListVoPage = PaginationUtils.fromGrid(pageParamsForm);

        String schoolYear = semesterSettingService.getSemesterByCurrentDate();
        if (startWeek == null) {
            startWeek = 1;
            endWeek = semesterSettingService.getSemesterWeeks(schoolYear);
        }

        List<FinishQualityVo> finishQualityVos = summaryService.viewFinishQuality(startWeek, endWeek,UserListVoPage);
        ExcelUtil<FinishQualityVo> util = new ExcelUtil<FinishQualityVo>(FinishQualityVo.class);

        Integer finalStartWeek = startWeek;
        Integer finalEndWeek = endWeek;
//        根据名称分组 并过滤所需周之外的数据
        Map<String, List<FinishQualityVo>> userFinishQualityMap = finishQualityVos.stream()
                .filter(finishQualityVo -> finishQualityVo.getTaskWeek() >= finalStartWeek || finishQualityVo.getTaskWeek() <= finalEndWeek)
                .collect(Collectors.groupingBy(
                        FinishQualityVo::getNickname,
                        Collectors.toList()
                ));

        Set<String> userNameList = userFinishQualityMap.keySet();
        ArrayList<Map<String, Object>> excelList = new ArrayList<>(userNameList.size());

        for (String userName : userNameList) {
            HashMap<String, Object> voMap = new LinkedHashMap<>();
            List<FinishQualityVo> finishQualityVoList = userFinishQualityMap.get(userName);
            Map<Integer, FinishQualityVo> taskWeekMap = finishQualityVoList.stream()
                    .collect(Collectors.toMap(FinishQualityVo::getTaskWeek, Function.identity(), (k1, k2) -> k2));
            voMap.put("姓名", userName);
            for (int i = startWeek; i <= endWeek; i++) {
                FinishQualityVo weekVo = taskWeekMap.get(i);
                voMap.put(String.format("第%d周", i), weekVo == null ? 0 : (double)weekVo.getFinishQuality()/100);
            }
            excelList.add(voMap);
        }

        return util.exportExcelByMap(excelList, "FinishQuality");
    }

    @GetMapping("/workPerformance")
    @ApiOperation("按周数查询绩效")
    public AjaxResult viewWorkPerformanceByWeek(Integer startWeek, Integer endWeek,PageParamsForm pageParamsForm) {
        Page<WeeklyPerformanceArrayVo> WeeklyPerformanceArrayVoPage = PaginationUtils.fromGrid(pageParamsForm);
        List<WeeklyPerformanceArrayVo> weeklyPerformanceArrayVos = summaryService.viewWorkPerformanceByWeek(startWeek, endWeek,WeeklyPerformanceArrayVoPage);
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        Long userId = loginUser.getUser().getUserId();
        List<UserListVo> userListVos = userService.showUserList(userId);
        return AjaxResult.success(weeklyPerformanceArrayVos,userListVos.size());
    }

    @GetMapping("/exportWorkPerformance")
    @ApiOperation("导出绩效")
    public AjaxResult exportWorkPerformance(Integer startWeek, Integer endWeek,PageParamsForm pageParamsForm) {

        Page<UserListVo> UserListVoPage = PaginationUtils.fromGrid(pageParamsForm);
        String schoolYear = semesterSettingService.getSemesterByCurrentDate();
        if (startWeek == null) {
            startWeek = 1;
            endWeek = semesterSettingService.getSemesterWeeks(schoolYear);
        }
        List<WeeklyPerformanceVo> weeklyPerformanceVos = summaryService.viewWorkPerformance(startWeek, endWeek,UserListVoPage);
        ExcelUtil<WeeklyPerformanceVo> util = new ExcelUtil<WeeklyPerformanceVo>(WeeklyPerformanceVo.class);
        Integer finalStartWeek = startWeek;
        Integer finalEndWeek = endWeek;
        Map<String, List<WeeklyPerformanceVo>> userWeeklyPerformanceMap = weeklyPerformanceVos.stream()
                .filter(weeklyPerformanceVo -> weeklyPerformanceVo.getTaskWeek() >= finalStartWeek || weeklyPerformanceVo.getTaskWeek() <= finalEndWeek)
                .collect(Collectors.groupingBy(
                        WeeklyPerformanceVo::getNickname,
                        Collectors.toList()
                ));
        Set<String> userNameList = userWeeklyPerformanceMap.keySet();
        ArrayList<Map<String, Object>> excelList = new ArrayList<>(userNameList.size());

        for (String userName : userNameList) {
            HashMap<String, Object> voMap = new LinkedHashMap<>();
            List<WeeklyPerformanceVo> weeklyPerformanceVoList = userWeeklyPerformanceMap.get(userName);
            Map<Integer, WeeklyPerformanceVo> taskWeekMap = weeklyPerformanceVoList.stream()
                    .collect(Collectors.toMap(WeeklyPerformanceVo::getTaskWeek, Function.identity(), (k1, k2) -> k2));
            voMap.put("姓名", userName);
            for (int i = startWeek; i <= endWeek; i++) {
                WeeklyPerformanceVo weekVo = taskWeekMap.get(i);
                voMap.put(String.format("第%d周", i), weekVo == null ? 0 : (double)weekVo.getWorkPerformance()/100);
            }
            excelList.add(voMap);
        }

        return util.exportExcelByMap(excelList, "WorkPerformance");
    }
}
