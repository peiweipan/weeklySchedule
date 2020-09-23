package com.weekly.user.controller;

import com.weekly.common.utils.ServletUtils;
import com.weekly.framework.security.LoginUser;
import com.weekly.framework.security.service.TokenService;
import com.weekly.framework.web.domain.AjaxResult;
import com.weekly.user.pojo.vo.SetAdminUserVo;
import com.weekly.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Api(description = "用户信息", tags = {"用户信息"})
public class UserController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    @ApiOperation("下拉框获取用户")
    @GetMapping("/showUserList")
    public AjaxResult showUserList(){
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        Long userId = loginUser.getUser().getUserId();
        return AjaxResult.success(userService.showUserList(userId));
    }

    @ApiOperation("设置高级用户")
    @PutMapping("/setAdminUser")
    public AjaxResult setAdminUser(@RequestBody SetAdminUserVo setAdminUserVo){
        userService.setAdminUser(setAdminUserVo);
        return AjaxResult.success();
    }

}
