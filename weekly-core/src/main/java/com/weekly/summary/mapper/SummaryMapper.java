package com.weekly.summary.mapper;

import com.weekly.summary.pojo.vo.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SummaryMapper {

    WeeklySummaryVo viewWeeklySummary(Long userId, int taskWeek, String schoolYear);

    void autoUpdateWeeklySummary(WeeklySummaryVo weeklySummaryVo);

    void createEmptySummary(Long userId,int taskWeek,String schoolYear,Integer workDays);

    List<HalfYearPerformanceVo> getWeeklyPerformanceHalfYear(Long userId, String schoolYear);

    void updateWeeklySummaryByUser(WeeklySummaryUpdateVo weeklySummaryUpdateVo);

    HalfYearInfoVo sumPerformanceByYear(Long userId,String schoolYear);

    List<FinishQualityVo> viewAllFinishQuality(String schoolYear);

    List<FinishQualityVo> viewFinishQualityByWeek(Integer startWeek, Integer endWeek, String schoolYear,List<Long> allGeneralUserIds);

    List<WeeklyPerformanceVo> viewAllWeeklyPerformance(String schoolYear, Long userId);

    List<WeeklyPerformanceVo> viewWeeklyPerformanceByWeek(Integer startWeek, Integer endWeek, String schoolYear,List<Long> allGeneralUserIds);
}
