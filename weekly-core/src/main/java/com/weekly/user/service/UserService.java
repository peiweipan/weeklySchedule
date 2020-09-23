package com.weekly.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weekly.common.form.PageParamsForm;
import com.weekly.user.pojo.po.User;
import com.weekly.user.pojo.vo.SetAdminUserVo;
import com.weekly.user.pojo.vo.UserListVo;

import java.util.List;

public interface UserService {

    List<UserListVo> showUserList(Long id);

    Page<UserListVo> showUserList(Long id, Page<UserListVo> userListVoPage);

    List<User> getUserByIds(List<Long> userIds);

    void setAdminUser(SetAdminUserVo setAdminUserVo);
}
