package com.weekly.auth.helper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.weekly.auth.pojo.bo.UserBo;
import com.weekly.common.constant.errcode.AccessErrorCode;
import com.weekly.common.exception.TokenException;
import jodd.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class UserHelper {

    @Autowired
    StringRedisTemplate redis;
    final String prefix = "DESHE_TOKEN_";
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 删除 rediskey
     * @param key
     */
    private void deleteKey(String key) {
        if(redis.hasKey(key)) {
            redis.delete(key);
        }
    }

    /**
     * 根据appID获取token
     * @param appId
     * @return
     */
    public String getToken(String appId){
        return redis.opsForValue().get(prefix + appId);
    }

    /**
     * 判断是否存在在redis里面
     * @param token
     * @return
     */
    public Boolean hasKey(String token){
        return redis.hasKey(prefix + token);
    }

    /**
     * 通过用户换 token
     * @param userInfo
     * @return
     */
    public String exchangeToken(UserBo userInfo) {
        String token = userInfo.getToken();
        logger.info("用户 {} appId:{} 请求获取token", userInfo.getName(), userInfo.getAppId());
        redis.opsForValue().set(prefix + userInfo.getAppId(), token, 7, TimeUnit.DAYS);
        redis.opsForValue().set(prefix + token, JSONObject.toJSONString(userInfo), 7, TimeUnit.DAYS);
        return token;
    }

    /**
     * 刷新 token 内的数据
     * @param userInfo
     * @param token
     * @return
     */
    public String refreshToken(UserBo userInfo, String token) {
        logger.info("用户 {} appId:{} 刷新了token", userInfo.getName(), userInfo.getAppId());
        redis.opsForValue().set(prefix + token, JSONObject.toJSONString(userInfo), 7, TimeUnit.DAYS);
        return token;
    }

    public UserBo getUserInfo(String token) throws TokenException {
        return getUserInfo(token, true);
    }

    /**
     * 通过 token 换用户
     * @param token
     * @param throwException
     * @return
     */
    public UserBo getUserInfo(String token, Boolean throwException) throws TokenException {
        if(StringUtil.isBlank(token)) {
            if (throwException){
                throw new TokenException(AccessErrorCode.TOKEN_EMPTY.code(), "TOKEN不能为空");
            }
        }

        token = prefix + token;
        if(redis.hasKey(token) == false) {
            if (!throwException) {
                return null;
            }
            throw new TokenException(AccessErrorCode.TOKEN_NOLOGIN.code(), "该TOKEN不存在");
        }

        String json = redis.opsForValue().get(token);
        logger.info("通过token换取用户{}", json);
        UserBo userInfo = JSON.parseObject(json, new TypeReference<UserBo>(){});

        if(redis.hasKey(token + "_cache") == false) {
            redis.opsForValue().set(token + "_cache", "true", 1, TimeUnit.DAYS);
            redis.opsForValue().set(token, json, 7, TimeUnit.DAYS);
        }
        return userInfo;
    }

    /**
     * 用户登出
     */
    public void logout(String token) {
        deleteKey(prefix + token);
        deleteKey(prefix + token + "_cache");
    }

    /**
     * 获取权限url
     * @param token
     * @return
     */
    public String getValidUrl(String token) {
        return redis.opsForValue().get(token);
    }

    public Boolean setValidUrl(String token, List<String> urlList) {
        //权限集合转json
        String json = JSON.toJSONString(urlList);
        if (token == null) {
            return false;
        }
        //存储到redis
        redis.opsForValue().set(token,json);
        return true;
    }
}
