package com.weekly.mapper;

import com.weekly.user.pojo.po.WeeklySummary;
import com.weekly.user.pojo.vo.HalfPerformanceVo;
import com.weekly.user.pojo.vo.WeeklySummaryUpdateVo;
import com.weekly.user.pojo.vo.WeeklySummaryVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SummaryMapper {
    WeeklySummaryVo viewWeeklySummary(Long userId, Integer taskWeek,String schoolYear);

    WeeklySummaryVo getWeeklySummary(String username, Integer taskWeek);

    void autoUpdateWeeklySummary(WeeklySummaryVo weeklySummaryVo);

    void createEmptySummary(Long userId,Integer taskWeek,String schoolYear,Integer workDays);

    void updateWeeklySummaryByUser(WeeklySummaryUpdateVo weeklySummaryUpdateVo);

    List<WeeklySummaryVo>  viewAllGeneralUserWeeklySummary(Integer taskWeek,String schoolYear,List<Long> allGeneralUserId);

    List<WeeklySummaryVo> viewAllWeeklySummary(Integer taskWeek,String schoolYear);

    HalfPerformanceVo sumPerformanceByYear(Long userId,String schoolYear);
}
