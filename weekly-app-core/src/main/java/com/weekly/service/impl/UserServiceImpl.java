package com.weekly.service.impl;

import com.firefly.errcode.enums.UserErrorCode;
import com.firefly.errcode.exception.UserException;
import com.weekly.user.pojo.po.User;
import com.weekly.mapper.UserMapper;
import com.weekly.service.UserService;
import com.weekly.user.pojo.vo.PassWordVo;
import com.weekly.user.pojo.vo.UserListVo1;
import com.weekly.util.PassWordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getByOpenId(String openId) {
        return userMapper.getByOpenId(openId);
    }

    @Override
    public void add(User user) {
        userMapper.add(user);
    }

    @Override
    public String findUserNickname(Long id) {
        return userMapper.findUserNickname(id);
    }

    @Override
    public void changeUserNickname(String nickname,Long id) {
        List<String> allUserNickname = userMapper.getAllUserNickname();
        if(allUserNickname.contains(nickname)){
            throw new UserException(UserErrorCode.A0111);
        }else {
            userMapper.changeUserNickname(nickname,id);
        }
    }

    @Override
    public void changePassWord(PassWordVo passWordVo,Long id) {

        User user = userMapper.getUserById(id);

        if(PassWordUtils.matchesPassword(passWordVo.getOldPassWord(),user.getPassword())){
            String newPW = PassWordUtils.encryptPassword(passWordVo.getNewPassWord());
            userMapper.changePassWord(newPW,id);
        }else{
            throw new UserException(UserErrorCode.A0120);
        }
    }

    @Override
    public User getUserById(Long userId) {
        return userMapper.getUserById(userId);
    }

    @Override
    public List<UserListVo1> showUserList(Long id) {

        User user = userMapper.getUserById(id);

        if(0 == user.getIsAdmin()){
            return userMapper.showOrdinaryUserList();
        }else {
            return userMapper.showAllUserList();
        }
    }

    @Override
    public List<User> getUserByIds(List<Long> userIds) {
        return userMapper.getUserByIds(userIds);
    }

    @Override
    public Integer ifAdimin(Long id) {
        User user = userMapper.getUserById(id);
        return user.getIsAdmin();
    }

    @Override
    public void updateUsername(String username,Long id) {
        userMapper.updateUsername(username,id);
    }
}
