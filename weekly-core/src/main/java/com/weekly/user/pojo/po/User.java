package com.weekly.user.pojo.po;

import lombok.Data;

@Data
public class User {

    private Long id;

    private String uuid;

    private String openId;

//    private String unionId;

//    private String accessKey;

    //    @TableField("user")
    private String username;

    private String password;

    private String nickname;

    private String wechatNickname;

//    private String avatarUrl;

//    private Boolean isdeleted;

    private Integer isAdmin;
}
