package com.weekly.workdays.service.impl;

import com.weekly.workdays.mapper.WorkDaysSettingMapper;
import com.weekly.workdays.pojo.vo.AddSettingVo;
import com.weekly.workdays.service.WorkDaysSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkDaysSettingServiceImpl implements WorkDaysSettingService {

    @Autowired
    private WorkDaysSettingMapper workDaysSettingMapper;

    @Override
    public void addSetting(AddSettingVo addSettingVo) {
        workDaysSettingMapper.addSetting(addSettingVo);
    }
}
