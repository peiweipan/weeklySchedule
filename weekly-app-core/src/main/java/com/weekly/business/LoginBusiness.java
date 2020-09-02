package com.weekly.business;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.weekly.common.exception.TokenException;
import com.weekly.common.pojo.po.User;
import com.weekly.common.pojo.vo.WechatLoginParamsVo;

public interface LoginBusiness {
    /**
     * 登录
     * @param paramsVo 微信登录参数
     * @return
     */
//    /**
//     * 登录
//     * @param paramsVo 后台登录参数
//     * @return
//     */
//    LoginVo login(AdminLoginParamsVo paramsVo) throws TokenException, UserException;

    User login(WechatLoginParamsVo paramsVo) throws TokenException;

    /**
     * 注册
     * @param result 微信参数
     * @return
     */
    User register(WxMaJscode2SessionResult result) throws TokenException;

//    /**
//     * 注册
//     * @param paramsVo 后台登录参数
//     * @return
//     */
//    Administrator register(AdminLoginParamsVo paramsVo) throws TokenException;

    WxMaJscode2SessionResult findWxResult(WechatLoginParamsVo paramsVo);

}
