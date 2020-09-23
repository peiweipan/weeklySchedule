package com.weekly.semester.controller;

import com.weekly.service.SemesterService;
import com.weekly.util.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/semester")
@Api(description = "学年学期信息", tags = {"学年学期信息"})
public class SemesterController {

    @Autowired
    private SemesterService semesterService;

    @GetMapping("/getCurrentSemester")
    @ApiOperation("获取当前学期")
    public String getCurrentSemester(){
        return semesterService.getSemesterByCurrentDate();
    }


    @GetMapping("/getWeeks")
    @ApiOperation("获取此学期的周数")
    public AjaxResult getSemesterWeeks(String schoolYear){
        return AjaxResult.success(semesterService.getSemesterWeeks(schoolYear));
    }
}
