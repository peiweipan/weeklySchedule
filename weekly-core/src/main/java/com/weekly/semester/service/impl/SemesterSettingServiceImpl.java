package com.weekly.semester.service.impl;

import com.weekly.common.exception.BaseException;
import com.weekly.semester.mapper.SemesterSettingMapper;
import com.weekly.semester.pojo.vo.SemesterAddVo;
import com.weekly.semester.pojo.vo.SemesterSettingVo;
import com.weekly.semester.pojo.vo.SemesterUpdateVo;
import com.weekly.semester.pojo.vo.SemesterVo;
import com.weekly.semester.service.SemesterSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SemesterSettingServiceImpl implements SemesterSettingService {

    @Autowired
    private SemesterSettingMapper semesterSettingMapper;

    @Override
    public List<SemesterSettingVo> showSemesterSetting() {
        List<SemesterSettingVo> semesterSettingVos = semesterSettingMapper.showSemesterSetting();
        return semesterSettingVos;
    }

    @Override
    public void addSemesterSetting(SemesterAddVo semesterAddVo) {
        List<String> allSchoolYearInSetting = semesterSettingMapper.getAllSchoolYear();
        if (allSchoolYearInSetting.contains(semesterAddVo.getSchoolYearAndSemester())){
            throw new BaseException("此学期设置已存在");
        }else {
            semesterSettingMapper.addSemesterSetting(semesterAddVo);
        }
    }

    @Override
    public Integer getSemesterWeeks(String schoolYear) {
        if(null == schoolYear || schoolYear.length() <= 0){
            schoolYear = getSemesterByCurrentDate();
        }
        return semesterSettingMapper.getSemesterWeeks(schoolYear);
    }

    @Override
    public String getSemesterByCurrentDate() {
        LocalDate now = LocalDate.now();
        String semesterByCurrentDate = semesterSettingMapper.getSemesterByCurrentDate(now);
        return semesterByCurrentDate;
    }

    @Override
    public String getStartDateBySchoolYear(String schoolYear) {
        return semesterSettingMapper.getStartDateBySchoolYear(schoolYear);
    }

    @Override
    public List<SemesterVo> getAllSchoolYearInSetting() {
        return semesterSettingMapper.getAllSchoolYearInSetting();
    }

    @Override
    public void deleteSemesterSetting(Long semesterId) {
        semesterSettingMapper.deleteSemesterSetting(semesterId);
    }

    @Override
    public void updateSemesterSetting(SemesterUpdateVo semesterUpdateVo) {
        List<SemesterVo> allSchoolYearInSetting1 = getAllSchoolYearInSetting();
        List<String> collect = allSchoolYearInSetting1.stream()
                .filter(a -> a.getSemesterId() != semesterUpdateVo.getSemesterId())
                .map(SemesterVo::getSchoolYear)
                .collect(Collectors.toList());
        if (collect.contains(semesterUpdateVo.getSchoolYearAndSemester())){
            throw new BaseException("此学期设置已存在");
        }else {
            semesterSettingMapper.updateSemesterSetting(semesterUpdateVo);
        }
    }
}
