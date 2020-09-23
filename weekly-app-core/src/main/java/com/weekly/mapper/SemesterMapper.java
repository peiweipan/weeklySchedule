package com.weekly.mapper;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;

@Repository
public interface SemesterMapper {

    String getSemesterByCurrentDate(LocalDate now);

    Integer getSemesterWeeks(String schoolYear);
}
