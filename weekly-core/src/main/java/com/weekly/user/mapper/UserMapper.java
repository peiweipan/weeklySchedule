package com.weekly.user.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weekly.user.pojo.po.User;
import com.weekly.user.pojo.vo.SetAdminUserVo;
import com.weekly.user.pojo.vo.UserListVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

     Long getUserIdbyName(String nickname);

     User getUserById(Long id);

     List<UserListVo> showOrdinaryUserList();

     List<UserListVo> showAllUserList();

     Page<UserListVo> showOrdinaryUserList(Page<UserListVo> userListVoPage);

     Page<UserListVo> showAllUserList(Page<UserListVo> userListVoPage);

     List<User> getUserByIds(List<Long> userIds);

     void setAdminUser(SetAdminUserVo setAdminUserVo);

     List<Long> getAllGeneralUserId();
}
