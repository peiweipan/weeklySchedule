package com.weekly.summary.pojo.vo;

import com.weekly.framework.aspectj.lang.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinishQualityVo {

    @ApiModelProperty("用户id")
    private Integer userId;

    @Excel(name = "名字")
    @ApiModelProperty("用户名称")
    private String nickname;

    @Excel(name = "第几周")
    @ApiModelProperty("周数")
    private Integer taskWeek;

    @Excel(name = "完成质量")
    @ApiModelProperty("完成质量")
    private Integer finishQuality;
}
