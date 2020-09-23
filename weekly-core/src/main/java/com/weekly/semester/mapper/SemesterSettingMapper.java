package com.weekly.semester.mapper;

import com.weekly.semester.pojo.vo.SemesterAddVo;
import com.weekly.semester.pojo.vo.SemesterSettingVo;
import com.weekly.semester.pojo.vo.SemesterUpdateVo;
import com.weekly.semester.pojo.vo.SemesterVo;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SemesterSettingMapper {

    List<SemesterSettingVo> showSemesterSetting();

    void addSemesterSetting(SemesterAddVo semesterAddVo);

    List<SemesterVo> getAllSchoolYearInSetting();

    List<String> getAllSchoolYear();

    Integer getSemesterWeeks(String schoolYear);

    String getSemesterByCurrentDate(LocalDate now);

    String getStartDateBySchoolYear(String schoolYear);

    void deleteSemesterSetting(Long semesterId);

    void updateSemesterSetting(SemesterUpdateVo semesterUpdateVo);
}
