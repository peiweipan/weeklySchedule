package com.weekly.workdays.controller;

import com.weekly.workdays.pojo.vo.AddSettingVo;
import com.weekly.workdays.service.WorkDaysSettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/workDaysSetting")
@Api(description = "每周工作天数设置", tags = {"每周工作天数设置"})
public class WorkDaysSettingController {

    @Autowired
    private WorkDaysSettingService workDaysSettingService;

    @PostMapping("/addSetting")
    @ApiOperation("工作天数设置")
    public void addSetting(@RequestBody @Valid AddSettingVo addSettingVo){
        workDaysSettingService.addSetting(addSettingVo);
    }
}
