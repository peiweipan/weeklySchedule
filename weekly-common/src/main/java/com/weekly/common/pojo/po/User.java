package com.weekly.common.pojo.po;

import lombok.Data;

@Data
public class User {

    private Long id;

    private String uuid;

    private String openId;

    private String unionId;

    private String accessKey;

    private String username;

    private String password;

    private String wechatNickname;

    private String avatarUrl;

    private Boolean isdeleted;
}
