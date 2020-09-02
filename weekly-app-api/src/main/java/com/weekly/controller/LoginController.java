package com.weekly.controller;

import com.weekly.auth.annotation.IgnoreSecurity;
import com.weekly.auth.helper.TokenHelper;
import com.weekly.auth.helper.UserHelper;
import com.weekly.auth.pojo.bo.UserBo;
import com.weekly.auth.pojo.vo.LoginVo;
import com.weekly.business.LoginBusiness;
import com.weekly.common.exception.TokenException;
import com.weekly.common.pojo.po.User;
import com.weekly.common.pojo.vo.WechatLoginParamsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/login")
@Api(description = "登录", tags = {"登录", "用户"})
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

        UserBo userBo = doLogin(user.getId(), user.getUuid());

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
