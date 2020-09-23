package com.weekly.user.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PassWordVo {

    @NotBlank
    @ApiModelProperty("原密码")
    private String oldPassWord;

    @NotBlank
    @ApiModelProperty("新密码")
    private String newPassWord;

}
