package com.weekly.summary.pojo.vo;

import lombok.Data;

import java.util.List;

@Data
public class ALLYearPerformanceVo {

    private List<HalfYearPerformanceVo> firstHalfYear;

    private List<HalfYearPerformanceVo> secondHalfYear;
}
