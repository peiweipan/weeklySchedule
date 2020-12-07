package com.weekly.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.weekly.user.pojo.po.User;
import com.weekly.user.pojo.vo.PassWordVo;
import com.weekly.user.pojo.vo.UserListVo1;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper extends BaseMapper<User> {

    User getByOpenId(String openId);

    void add(User user);

    String findUserNickname(Long id);

    Long getUserIdbyName(String nickname);

    void changeUserNickname(String nickname,Long id);

    List<String> getAllUserNickname();

    void changePassWord(String password,Long id);

    String findPassWord(Long id);

    User getUserById(Long id);

    List<UserListVo1> showOrdinaryUserList();

    List<UserListVo1> showAllUserList();

    List<Long> getAllGeneralUserId();

    List<User> getUserByIds(List<Long> userIds);

    void updateUsername(String username,Long id);

}
