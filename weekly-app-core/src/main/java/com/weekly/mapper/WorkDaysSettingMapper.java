package com.weekly.mapper;

import org.springframework.stereotype.Repository;

@Repository
public interface WorkDaysSettingMapper {

    Integer getWorkDaysBySetting(String schoolYear,Integer week);
}
