package com.weekly.user.controller;

import com.weekly.user.pojo.vo.UserListVo1;
import com.weekly.util.AjaxResult;
import com.weekly.util.UserUtils;
import com.weekly.service.UserService;
import com.weekly.user.pojo.vo.PassWordVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@Api(description = "用户信息", tags = {"用户信息"})
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("获取用户账号名称")
    @GetMapping
    public AjaxResult getUserNickname(){
        Long currentUserId = UserUtils.getCurrentUserId();
        return AjaxResult.success("操作成功",userService.findUserNickname(currentUserId));
    }


    @ApiOperation("修改用户名称")
    @PutMapping("/changeNickname/{nickname}")
    public AjaxResult changeUserNickname(@PathVariable String nickname){
        Long currentUserId = UserUtils.getCurrentUserId();
        userService.changeUserNickname(nickname,currentUserId);
        return AjaxResult.success();
    }


    @ApiOperation("修改用户密码")
    @PutMapping("/password")
    public AjaxResult changePassWord(@RequestBody @Valid PassWordVo passWordVo){
        Long currentUserId = UserUtils.getCurrentUserId();
        userService.changePassWord(passWordVo,currentUserId);
        return AjaxResult.success();
    }

    @ApiOperation("下拉框获取用户")
    @GetMapping("/showUserList")
    public AjaxResult showUserList() {
        return AjaxResult.success(userService.showUserList(UserUtils.getCurrentUserId()));
    }

    @ApiOperation("用户是否为高级用户")
    @GetMapping("/ifAdmin")
    public AjaxResult ifAdmin(){
        Long currentUserId = UserUtils.getCurrentUserId();
        return AjaxResult.success(userService.ifAdimin(currentUserId));
    }
}
