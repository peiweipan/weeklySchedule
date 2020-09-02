package com.weekly.common.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

public class WechatLoginParamsVo {

    @NotBlank(message = "wxCode不能为空")
    @ApiModelProperty("wx.login code")
    private String code;

    public WechatLoginParamsVo() {
    }

    public WechatLoginParamsVo(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
