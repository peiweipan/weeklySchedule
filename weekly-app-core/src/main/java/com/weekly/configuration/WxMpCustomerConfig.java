package com.weekly.configuration;

import cn.binarywang.wx.miniapp.api.WxMaQrcodeService;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.bean.WxMaSubscribeData;
import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import com.weekly.common.pojo.po.User;
import me.chanjar.weixin.common.error.WxErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author ricky
 * @version V1.0
 * @Title:
 * @Package com.mountain.common.config
 * @Description:
 * @date 2019/12/18
 */
@Configuration("wxMpCustomerConfig")
@ConfigurationProperties(prefix = "wechat.mini-program.customer")
public class WxMpCustomerConfig {

    private String appId;

    private String appSecret;

    //模板消息id
    private String templateId;

    //跳转小程序页面路径
    private String pageUrl;

    //签到地址
    private String signUrl;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public WxMaService getService() {
        WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
        config.setAppid(getAppId());
        config.setSecret(getAppSecret());

        WxMaService service = new WxMaServiceImpl();
        service.setWxMaConfig(config);
        return service;
    }

    public void sendWxMessage(User user, String phrase, String thing) {

        WxMaSubscribeMessage subscribeMessage = new WxMaSubscribeMessage();

        //跳转小程序页面路径
        subscribeMessage.setPage(getPageUrl());
        //模板消息id
        subscribeMessage.setTemplateId(getTemplateId());
        //给谁推送 用户的openid （可以调用根据code换openid接口)
        subscribeMessage.setToUser(user.getOpenId());
        //==========================================创建一个参数集合========================================================
        ArrayList<WxMaSubscribeData> wxMaSubscribeData = new ArrayList<>();
        wxMaSubscribeData.add(new WxMaSubscribeData(
                "name2", user.getWechatNickname()
        ));
        wxMaSubscribeData.add(new WxMaSubscribeData(
                "phrase3", phrase
        ));
        wxMaSubscribeData.add(new WxMaSubscribeData(
                "thing5", thing
        ));
        subscribeMessage.setData(wxMaSubscribeData);
        //=========================================封装参数集合完毕========================================================

        try {
            //获取微信小程序配置：
            final WxMaService wxService = getService();
            //进行推送
            wxService.getMsgService().sendSubscribeMsg(subscribeMessage);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("推送失败");
        }

    }

    public byte[] createQrcode(Map<String, String> map) {
        WxMaService wxMaService = getService();
        WxMaQrcodeService wxMaQrcodeService = wxMaService.getQrcodeService();

        String path = getSignUrl();

        if (!map.isEmpty()) {
            path = parsePath(path, map);
        }

        try {
            return wxMaQrcodeService.createQrcodeBytes(path, 430);
        } catch (WxErrorException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    private static String parsePath(String path, Map<String, String> map) {

        path += "?";

        StringBuilder pathBuilder = new StringBuilder("");
        for (String value : map.keySet()) {
            pathBuilder.append(value).append("=").append(map.get(value)).append("&");
        }
        String string = pathBuilder.toString();
        if (string.endsWith("&")) {
            string = string.substring(0, string.length() - 1);
        }
        path += string;

        return path;
    }

    private String parseVale(Map<String, String> map) {

        StringBuilder pathBuilder = new StringBuilder("");
        for (String value : map.keySet()) {
            pathBuilder.append(value).append("=").append(map.get(value)).append(",");
        }
        String string = pathBuilder.toString();
        if (string.endsWith(",")) {
            string = string.substring(0,string.length() - 1);
        }
        return string;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String getSignUrl() {
        return signUrl;
    }

    public void setSignUrl(String signUrl) {
        this.signUrl = signUrl;
    }
}
