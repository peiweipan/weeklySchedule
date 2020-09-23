package com.weekly.service.impl;

import com.weekly.mapper.SemesterMapper;
import com.weekly.service.SemesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SemesterServiceImpl implements SemesterService {

    @Autowired
    private SemesterMapper semesterMapper;

    @Override
    public String getSemesterByCurrentDate() {
        LocalDate now = LocalDate.now();
        String semesterByCurrentDate = semesterMapper.getSemesterByCurrentDate(now);
        return semesterByCurrentDate;
    }

    @Override
    public Integer getSemesterWeeks(String schoolYear) {
        if(null == schoolYear || schoolYear.length() <= 0){
            schoolYear = getSemesterByCurrentDate();
        }
        return semesterMapper.getSemesterWeeks(schoolYear);
    }
}
