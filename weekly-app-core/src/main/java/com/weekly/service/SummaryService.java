package com.weekly.service;

import com.weekly.user.pojo.po.WeeklySummary;
import com.weekly.user.pojo.vo.SummaryPerformanceVo;
import com.weekly.user.pojo.vo.WeeklySummaryUpdateVo;
import com.weekly.user.pojo.vo.WeeklySummaryVo;

import java.util.List;


public interface SummaryService {

    WeeklySummaryVo viewWeeklySummary(String nickname, Integer taskWeek,String schoolYear);

    WeeklySummaryVo getWeeklySummary(Long userId,Integer taskWeek,String schoolYear);

//    WeeklySummaryVo computeSummaryParam(Long userId,Integer taskWeek,String schoolYear);

    WeeklySummaryVo computeSummaryParam(Long userId,Integer taskWeek,String schoolYear,WeeklySummaryVo weeklySummary);

    void autoUpdateWeeklySummary(Long userId,Integer taskWeek,String schoolYear);

    void updateWeeklySummaryByUser(WeeklySummaryUpdateVo weeklySummaryUpdateVo,Long currentUserId);

    List<WeeklySummaryVo> viewAllWeeklySummary(Integer taskWeek,Long currentUserId,String schoolYear);

    SummaryPerformanceVo viewSummaryPerformance(Long year,Long currentUserId);
}
