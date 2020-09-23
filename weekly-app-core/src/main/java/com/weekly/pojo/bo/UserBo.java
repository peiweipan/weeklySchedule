package com.weekly.pojo.bo;

import io.swagger.annotations.ApiParam;
import lombok.Data;

/**
 * @author chzz
 * @version V1.0
 * @Title:
 * @Package com.mountain.bo
 * @Description: TODO 系统－用户bo
 * @date 2019/7/22
 */
@Data
public class UserBo {
    @ApiParam(value = "用户id")
    private Long id;
    @ApiParam(value = "用户名")
    private String name;
    @ApiParam(value = "appId")
    private String appId;
    @ApiParam(value = "备注")
    private String remark;
    @ApiParam(value = "token")
    private String token;
    @ApiParam(value = "secret")
    private String secret;
}
