package com.weekly.user.pojo.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class AddSysUserMQVo implements Serializable {

    private String userName;

    private String password;
}
