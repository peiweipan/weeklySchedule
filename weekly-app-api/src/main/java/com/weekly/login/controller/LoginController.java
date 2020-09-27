package com.weekly.login.controller;

import com.weekly.annotation.IgnoreSecurity;
import com.weekly.helper.TokenHelper;
import com.weekly.helper.UserHelper;
import com.weekly.pojo.bo.UserBo;
import com.weekly.pojo.vo.LoginVo;
import com.weekly.business.LoginBusiness;
import com.weekly.common.exception.TokenException;
import com.weekly.user.pojo.po.User;
import com.weekly.common.pojo.vo.WechatLoginParamsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/login")
@Api(description = "登录", tags = {"登录"})
public class LoginController {
    @Autowired
    private LoginBusiness loginBusiness;

    @Autowired
    private TokenHelper tokenHelper;

    @Autowired
    private UserHelper userHelper;

    @ApiOperation("小程序登录")
    @PostMapping
    @IgnoreSecurity
    public LoginVo login(@RequestBody WechatLoginParamsVo paramsVo) throws TokenException {
        User user = loginBusiness.login(paramsVo);

//        UserBo userBo = doLogin(user.getId(), user.getUuid());
        UserBo userBo = doLogin(user.getId(), user.getOpenId());

        return new LoginVo(userBo.getToken(), user.getUsername());
    }


    private UserBo doLogin(Long id, String appId) throws TokenException {
        UserBo userBo = new UserBo();
        userBo.setId(id);
        userBo.setAppId(appId);
        userBo.setSecret(UUID.randomUUID().toString());
        try {
            userBo.setToken(tokenHelper.sign(userBo.getAppId(), userBo.getSecret(), 15));
            userHelper.exchangeToken(userBo);
        } catch (TokenException e) {
            throw new TokenException();
        }
        return userBo;
    }

}
