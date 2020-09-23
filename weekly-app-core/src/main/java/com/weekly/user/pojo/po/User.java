package com.weekly.user.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.lang.annotation.Documented;

@Data
//@TableName("sys_user")
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
