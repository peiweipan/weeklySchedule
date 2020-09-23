package com.weekly.project.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.weekly.common.event.RegisterBO;
import com.weekly.project.system.domain.SysUser;
import com.weekly.project.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class SysUserAddByMQServiceImpl {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ISysUserService sysUserService;

    public void receiveMessage(String message){
        RegisterBO user = JSONObject.parseObject(message, RegisterBO.class);
        System.out.println("消息来了：" + user);

        SysUser sysUser = new SysUser();
        sysUser.setUserName(user.getUsername());
        sysUser.setNickName(user.getNickname());
        sysUser.setPassword(user.getPassword());
        sysUser.setUuid(user.getUuid());
        sysUser.setOpenId(user.getOpenId());
        sysUser.setRoleIds(user.getRoles());
        System.out.println(sysUser);

        sysUserService.insertUser(sysUser);
    }
}
