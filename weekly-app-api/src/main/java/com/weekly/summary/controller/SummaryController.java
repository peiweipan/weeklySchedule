package com.weekly.summary.controller;

import com.weekly.util.AjaxResult;
import com.weekly.util.UserUtils;
import com.weekly.service.SummaryService;
import com.weekly.user.pojo.vo.SummaryPerformanceVo;
import com.weekly.user.pojo.vo.WeeklySummaryUpdateVo;
import com.weekly.user.pojo.vo.WeeklySummaryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/summary")
@Api(description = "本周综合信息", tags = {"本周综合信息"})
public class SummaryController {

    @Autowired
    private SummaryService summaryService;

    @GetMapping("/view")
    @ApiOperation("查看本周综合")
    public AjaxResult viewWeeklySummary(String nickname, Integer taskWeek, String schoolYear){
        return AjaxResult.success(summaryService.viewWeeklySummary(nickname,taskWeek,schoolYear));
    }

    @GetMapping("/viewAll")
    @ApiOperation("查看所有人的本周综合")
    public AjaxResult viewAllWeeklySummary(Integer taskWeek,String schoolYear){
        Long currentUserId = UserUtils.getCurrentUserId();
        return AjaxResult.success(summaryService.viewAllWeeklySummary(taskWeek,currentUserId,schoolYear));
    }

    @PutMapping("/updateSummary")
    @ApiOperation("用户修改本周综合")
    public AjaxResult updateWeeklySummaryByUser(@RequestBody WeeklySummaryUpdateVo weeklySummaryUpdateVo){
        Long currentUserId = UserUtils.getCurrentUserId();
        summaryService.updateWeeklySummaryByUser(weeklySummaryUpdateVo,currentUserId);
        return AjaxResult.success();
    }


    @GetMapping("/viewPerformance")
    @ApiOperation("查看学年绩效信息")
    public AjaxResult viewSummaryPerformance(Long year){
        Long currentUserId = UserUtils.getCurrentUserId();
        SummaryPerformanceVo summaryPerformanceVo = summaryService.viewSummaryPerformance(year, currentUserId);
        return AjaxResult.success(summaryPerformanceVo);
    }
}
