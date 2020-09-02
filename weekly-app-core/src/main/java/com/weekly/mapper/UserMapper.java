package com.weekly.mapper;

import com.weekly.common.pojo.po.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    User getByOpenId(String openId);

    User add(User user);
}
