package com.weekly.user.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SetAdminUserVo {

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("设置高级用户：0为普通用户，1为高级用户")
    private Integer isAdmin;
}
