package com.weekly.common.exception;

import com.weekly.common.constant.errcode.AccessErrorCode;

/**
 * @author chzz
 * @version V1.0
 * @Title:
 * @Package com.mountain.common.exception.account
 * @Description: 系统－认证校验异常类
 * @date 2019/2/23
 */
public class TokenException extends AccessException {

    public TokenException(int status, String msg) {
        super(status, msg);
    }

    public TokenException() {
        super(AccessErrorCode.GENERAL.code(), "Token 异常");
    }
}
