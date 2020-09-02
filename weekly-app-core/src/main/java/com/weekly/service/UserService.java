package com.weekly.service;

import com.weekly.common.pojo.po.User;

public interface UserService {

    User getByOpenId(String openId);

    User add(User user);
}
