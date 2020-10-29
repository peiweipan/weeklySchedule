package com.weekly.summary.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weekly.framework.web.domain.AjaxResult;
import com.weekly.summary.pojo.vo.*;

import java.text.ParseException;
import java.util.List;

public interface SummaryService {

    HomePageInfoVo showHomePageInfoVo() throws ParseException;

    void autoUpdateWeeklySummary(Long userId,int taskWeek,String schoolYear);

    WeeklySummaryVo getWeeklySummary(Long userId, int taskWeek, String schoolYear);

//    WeeklySummaryVo computeSummaryParam(Long userId,int taskWeek,String schoolYear);

    WeeklySummaryVo computeSummaryParam(Long userId,Integer taskWeek,String schoolYear,WeeklySummaryVo weeklySummary);

    ALLYearPerformanceVo showWeeklyPerformance(Integer year);

    WeeklySummaryVo viewWeeklySummary() throws ParseException;

    void updateWeeklySummaryByUser(WeeklySummaryUpdateVo weeklySummaryUpdateVo) throws ParseException;

    AllYearInfoVo viewAllYearSummaryInfo(Long year);

    List<FinishQualityVo> viewFinishQuality(Integer startWeek, Integer endWeek,Page pageParamsForm);

    List<WeeklyPerformanceVo> viewWorkPerformance(Integer startWeek, Integer endWeek,Page pageParamsForm);

    List<FinishQualityArrayVo> viewFinishQualityByWeek(Integer startWeek, Integer endWeek, Page pageParamsForm);

    List<WeeklyPerformanceArrayVo> viewWorkPerformanceByWeek(Integer startWeek, Integer endWeek,Page pageParamsForm);

//    AjaxResult exportFinishQuality(Integer startWeek, Integer endWeek,Page page);
}
