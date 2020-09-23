package com.weekly.user.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weekly.user.mapper.UserMapper;
import com.weekly.user.pojo.po.User;
import com.weekly.user.pojo.vo.SetAdminUserVo;
import com.weekly.user.pojo.vo.UserListVo;
import com.weekly.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UserListVo> showUserList(Long id) {
        User user = userMapper.getUserById(id);
        if(0 == user.getIsAdmin()){
            return userMapper.showOrdinaryUserList();
        }else {
            return userMapper.showAllUserList();
        }
    }

    @Override
    public Page<UserListVo> showUserList(Long id,  Page<UserListVo> userListVoPage) {
        User user = userMapper.getUserById(id);
        if(0 == user.getIsAdmin()){
            return userMapper.showOrdinaryUserList(userListVoPage);
        }else {
            return userMapper.showAllUserList(userListVoPage);
        }
    }

    @Override
    public List<User> getUserByIds(List<Long> userIds) {
        return userMapper.getUserByIds(userIds);
    }

    @Override
    public void setAdminUser(SetAdminUserVo setAdminUserVo) {
        userMapper.setAdminUser(setAdminUserVo);
    }
}
