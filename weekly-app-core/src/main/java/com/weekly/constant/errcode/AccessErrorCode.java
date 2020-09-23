package com.weekly.constant.errcode;

/**
 * 访问类型的异常
 */
public enum AccessErrorCode implements ErrorCodeType {

    GENERAL(1200000), // 通用

    TOKEN(1201000), // Token 异常
    TOKEN_EMPTY(1201001), // Token 为空
    TOKEN_EXPIRED(1201002), // Token 已过期
    TOKEN_NOLOGIN(1201003), // 用户未登录,


    COOKIE(1202000), // Cookie 异常

    SESSION(1203000), // Session 异常

    AUTHORITY(1204000), // 权限异常

    WECHAT(1205000), //微信 异常
    ;

    private final int code;

    AccessErrorCode(int code) {
        this.code = code;
    }

    public int code() {
        return code;
    }
}
