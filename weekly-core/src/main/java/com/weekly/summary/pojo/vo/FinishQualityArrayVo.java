package com.weekly.summary.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinishQualityArrayVo {

    private String nickname;

    private int[] finishQuality;
}
