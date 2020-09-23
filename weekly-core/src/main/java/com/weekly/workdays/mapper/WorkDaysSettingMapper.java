package com.weekly.workdays.mapper;

import com.weekly.workdays.pojo.vo.AddSettingVo;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkDaysSettingMapper {

    Integer getWorkDaysBySetting(String schoolYear,int week);

    void addSetting(AddSettingVo addSettingVo);
}
