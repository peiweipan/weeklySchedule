package com.weekly.semester.controller;

import com.weekly.framework.web.domain.AjaxResult;
import com.weekly.semester.pojo.vo.SemesterAddVo;
import com.weekly.semester.pojo.vo.SemesterUpdateVo;
import com.weekly.semester.service.SemesterSettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/semester")
@Api(description = "学年学期信息设置", tags = {"学年学期信息设置"})
public class SemesterSettingController {

    @Autowired
    private SemesterSettingService semesterSettingService;

    @GetMapping("/showSemester")
    @ApiOperation("查看学期设置")
    private AjaxResult showSemesterSetting(){
        return AjaxResult.success(semesterSettingService.showSemesterSetting());
    }


    @PostMapping("/addSemester")
    @ApiOperation("设置学期")
    public AjaxResult addSemesterSetting(@RequestBody @Valid SemesterAddVo semesterAddVo){
        semesterSettingService.addSemesterSetting(semesterAddVo);
        return AjaxResult.success();
    }

    @PutMapping("/updateSemester")
    @ApiOperation("编辑学期设置")
    public AjaxResult updateSemesterSetting(@RequestBody @Valid SemesterUpdateVo semesterUpdateVo){
        semesterSettingService.updateSemesterSetting(semesterUpdateVo);
        return AjaxResult.success();
    }


    @DeleteMapping("/deleteSemester")
    @ApiOperation("删除学期设置")
    public AjaxResult deleteSemesterSetting(Long semesterId){
        semesterSettingService.deleteSemesterSetting(semesterId);
        return AjaxResult.success();
    }

    @GetMapping("/getWeeks")
    @ApiOperation("获取此学期的周数")
    public AjaxResult getSemesterWeeks(String schoolYear){
        return AjaxResult.success(semesterSettingService.getSemesterWeeks(schoolYear));
    }

    @GetMapping("/getSchoolYear")
    @ApiOperation("学年学期下拉框")
    public AjaxResult getAllSchoolYearInSetting(){
        return AjaxResult.success(semesterSettingService.getAllSchoolYearInSetting());
    }
}
