package com.weekly.auth.pojo.vo;

import io.swagger.annotations.ApiModelProperty;

public class LoginVo {

    @ApiModelProperty("登录凭证")
    private String token;

    private String username;

    public LoginVo() {
    }

    public LoginVo(String token,String username) {
        this.token = token;
        this.username = username;
    }

    public LoginVo(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
