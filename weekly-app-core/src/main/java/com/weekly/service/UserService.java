package com.weekly.service;

import com.weekly.user.pojo.po.User;
import com.weekly.user.pojo.vo.PassWordVo;
import com.weekly.user.pojo.vo.UserListVo1;

import java.util.List;

public interface UserService {

    User getByOpenId(String openId);

    void add(User user);

    String findUserNickname(Long id);

    void changeUserNickname(String nickname,Long id);

    void changePassWord(PassWordVo passWordVo,Long id);

    User getUserById(Long userId);

    List<UserListVo1> showUserList(Long id);

    List<User> getUserByIds(List<Long> userIds);

    Integer ifAdimin(Long id);

    void updateUsername(String username,Long id);
}
