package com.weekly.business.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.weekly.business.LoginBusiness;
import com.weekly.common.exception.TokenException;
import com.weekly.common.exception.WechatException;
import com.weekly.common.pojo.po.User;
import com.weekly.common.pojo.vo.WechatLoginParamsVo;
import com.weekly.configuration.WxMpCustomerConfig;
import com.weekly.service.UserService;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class LoginBusinessImpl implements LoginBusiness {

    @Autowired
    private UserService userService;

//    @Autowired
//    private AdministratorService administratorService;


    @Autowired
    private WxMpCustomerConfig wxMpCustomerConfig;

    @Override
    public User login(WechatLoginParamsVo paramsVo) throws TokenException {

        WxMaJscode2SessionResult result = findWxResult(paramsVo);

        User user = userService.getByOpenId(result.getOpenid());

        if (null == user) {
            user = register(result);
        }

////        更新accessKey
//        userService.updateAccessKey(user, result);

        return user;
    }


    @Override
    public User register(WxMaJscode2SessionResult result) {

        User user = new User();

        user.setUsername(getRandomString(7));

        user.setUnionId(result.getUnionid());
        user.setOpenId(result.getOpenid());

        user = userService.add(user);

        return user;
    }

//    @Override
//    public LoginVo login(AdminLoginParamsVo paramsVo) throws TokenException, UserException {
//
//        String pwd = paramsVo.getPwd();
//
//        Administrator administrator = administratorService.getByUserName(
//                paramsVo.getUserName()
//        );
//
//        if (null == administrator) {
//            throw new UserException(ServiceErrorCode.USER_NOT_EXISTS.code(), "该用户不存在");
//        }
//
//        if (!HashUtils.bcryptCheck(pwd, administrator.getPassword())) {
//            throw new UserException(ServiceErrorCode.USER_PWD_ERROR.code(), "账号密码错误");
//        }
//
//        return new LoginVo(doLogin(administrator.getId(), administrator.getUuid()).getToken());
//    }

//    @Override
//    public Administrator register(AdminLoginParamsVo paramsVo) throws TokenException {
//
//        Administrator administrator = new Administrator();
//
//        administrator.setUsername(paramsVo.getUserName());
//        administrator.setPassword(HashUtils.bcrypt(paramsVo.getPwd()));
//
//        return administratorService.add(administrator);
//
//    }

//    private UserBo doLogin(Long id, String appId) throws TokenException {
//        UserBo userBo = new UserBo();
//        userBo.setId(id);
//        userBo.setAppId(appId);
//        userBo.setSecret(UUID.randomUUID().toString());
//        try {
//            userBo.setToken(tokenHelper.sign(userBo.getAppId(), userBo.getSecret(), 15));
//            userHelper.exchangeToken(userBo);
//        } catch (TokenException e) {
//            throw new TokenException();
//        }
//        return userBo;
//    }

    public WxMaJscode2SessionResult findWxResult(WechatLoginParamsVo paramsVo) {

        WxMaService service = wxMpCustomerConfig.getService();

        WxMaJscode2SessionResult result;

        try {
            result = service.jsCode2SessionInfo(paramsVo.getCode());
        } catch (WxErrorException e) {
            throw new WechatException(e);
        }

        return result;
    }

    public String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

}
