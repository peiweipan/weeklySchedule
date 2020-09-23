package com.weekly.semester.service;

import com.weekly.semester.pojo.vo.SemesterAddVo;
import com.weekly.semester.pojo.vo.SemesterSettingVo;
import com.weekly.semester.pojo.vo.SemesterUpdateVo;
import com.weekly.semester.pojo.vo.SemesterVo;

import java.util.List;

public interface SemesterSettingService {

    List<SemesterSettingVo> showSemesterSetting();

    void addSemesterSetting(SemesterAddVo semesterAddVo);

    Integer getSemesterWeeks(String schoolYear);

    String getSemesterByCurrentDate();

    String getStartDateBySchoolYear(String schoolYear);

    List<SemesterVo> getAllSchoolYearInSetting();

    void deleteSemesterSetting(Long semesterId);

    void updateSemesterSetting(SemesterUpdateVo semesterUpdateVo);
}
