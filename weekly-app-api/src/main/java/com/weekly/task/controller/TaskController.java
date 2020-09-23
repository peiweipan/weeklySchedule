package com.weekly.task.controller;

import com.weekly.util.AjaxResult;
import com.weekly.util.UserUtils;
import com.weekly.service.TaskService;
import com.weekly.user.pojo.vo.TaskAddVo;
import com.weekly.user.pojo.vo.TaskListVo;
import com.weekly.user.pojo.vo.TaskReleaseVo;
import com.weekly.user.pojo.vo.TaskUpdateVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/task")
@Api(description = "任务信息", tags = {"任务信息"})
public class TaskController {

    @Autowired
    private TaskService taskService;

    @ApiOperation("显示单个用户单周任务列表")
    @GetMapping("/showUserTask")
    public AjaxResult showUserTask(Integer taskWeek,String nickname,String schoolYear){
        return AjaxResult.success(taskService.showUserTask(taskWeek,nickname,schoolYear));
    }

    @ApiOperation("显示所有用户单周任务列表")
    @GetMapping("/showAllUserTask")
    public AjaxResult showAllUserTask(Integer taskWeek,String schoolYear){
        Long currentUserId = UserUtils.getCurrentUserId();
        return AjaxResult.success(taskService.showAllUserTask(taskWeek,currentUserId,schoolYear));
    }

    @ApiOperation("显示任务细节")
    @GetMapping("/showTaskDetails")
    public AjaxResult showTaskDetails(Long taskId){
        return AjaxResult.success(taskService.showTaskDetails(taskId));
    }


    @ApiOperation("修改个人任务")
    @PutMapping("/updateTask")
    public AjaxResult updateTask(@RequestBody @Valid TaskUpdateVo taskUpdateVo){
        Long currentUserId = UserUtils.getCurrentUserId();
        taskService.updateTask(taskUpdateVo,currentUserId);
        return AjaxResult.success();
    }


    @ApiOperation("删除个人任务")
    @DeleteMapping("/deleteTask")
    public AjaxResult deleteTask(Long taskId,Integer taskWeek,Long userId,String schoolYear){
        Long currentUserId = UserUtils.getCurrentUserId();
        taskService.deleteTask(taskId,userId,taskWeek,currentUserId,schoolYear);
        return AjaxResult.success();
    }


    @ApiOperation("添加个人任务")
    @PostMapping("/AddTask")
    public AjaxResult AddTask(@RequestBody @Valid TaskAddVo taskAddVo){
        Long currentUserId = UserUtils.getCurrentUserId();
        taskService.AddTask(taskAddVo,currentUserId);
        return AjaxResult.success();
    }


    @ApiOperation("高级用户发布任务")
    @PostMapping("/releaseTask")
    public AjaxResult releaseTask(@RequestBody @Valid TaskReleaseVo taskReleaseVo){
        Long currentUserId = UserUtils.getCurrentUserId();
        taskService.releaseTask(taskReleaseVo,currentUserId);
        return AjaxResult.success();
    }

}
