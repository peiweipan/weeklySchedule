package com.weekly.common.exception;

/**
 * @version V1.0
 * @author: csz
 * @Title
 * @Package: com.yhc.deshe.exception.access.wechat
 * @Description:
 * @date: 2020/02/06
 */
public class WechatException extends ComponentException {

    public WechatException(Throwable e) {
        super(e);
    }

    public WechatException(Throwable cause, String message) {
        super(cause, message);
    }
}
