package com.weekly.service.impl;

import com.weekly.common.pojo.po.User;
import com.weekly.mapper.UserMapper;
import com.weekly.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getByOpenId(String openId) {
        return userMapper.getByOpenId(openId);
    }

    @Override
    public User add(User user) {
        user.setUuid(UUID.randomUUID().toString());

        return userMapper.add(user);
    }
}
