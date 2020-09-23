package com.weekly.task.controller;

import com.weekly.common.utils.poi.ExcelUtil;
import com.weekly.framework.aspectj.lang.annotation.Log;
import com.weekly.framework.aspectj.lang.enums.BusinessType;
import com.weekly.framework.web.domain.AjaxResult;
import com.weekly.task.pojo.po.Task;
import com.weekly.task.pojo.vo.*;
import com.weekly.task.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/task")
@Api(description = "任务信息", tags = {"任务信息"})
public class TaskController {

    @Autowired
    private TaskService taskService;

    @ApiOperation("本周任务")
    @GetMapping("/showCurrentUserTask")
    public AjaxResult showCurrentUserTask(int taskWeek){
        return AjaxResult.success(taskService.showCurrentUserTask(taskWeek));
    }

    @ApiOperation("通过任务id显示单个任务")
    @GetMapping("/showTaskById")
    public AjaxResult showTaskById(Integer taskId){
        return AjaxResult.success(taskService.showTaskById(taskId));
    }

    @ApiOperation("显示某个用户单周任务列表")
    @GetMapping("/showUserTask")
    public AjaxResult showUserTask(Integer taskWeek, String nickname, String schoolYear) throws ParseException {
        return AjaxResult.success(taskService.showUserTask(taskWeek,nickname,schoolYear));
    }

    @Log(title = "首页导出个人任务", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("首页导出个人任务")
    public AjaxResult export(String schoolYear,Integer taskWeek,String nickname) throws ParseException {
//        return taskService.exportTask(schoolYear,taskWeek,nickname);
        List<TaskListVo> taskListVos = taskService.showUserTask(taskWeek, nickname, schoolYear);
        System.out.println(taskListVos);
        ExcelUtil<TaskListVo> util = new ExcelUtil<TaskListVo>(TaskListVo.class);
        return util.exportExcel(taskListVos, "task");
    }


//    @PreAuthorize("@ss.hasPermi('system:task:export')")
    @Log(title = "任务事项", businessType = BusinessType.EXPORT)
    @GetMapping("/export1")
    @ApiOperation("导出个人任务")
    public AjaxResult export1(Integer taskWeek)
    {
        List<TaskListVo> taskListVos = taskService.showCurrentUserTask(taskWeek);
        ExcelUtil<TaskListVo> util = new ExcelUtil<TaskListVo>(TaskListVo.class);
        return util.exportExcel(taskListVos, "task");
    }


    @ApiOperation("添加个人任务")
    @PostMapping("/AddTask")
    public AjaxResult AddTask(@RequestBody @Valid TaskAddVo taskAddVo){
        taskService.AddTask(taskAddVo);
        return AjaxResult.success();
    }

    @ApiOperation("事项填写")
    @PostMapping("/AddOwnTask")
    public AjaxResult AddOwnTask(@RequestBody @Valid TaskAddOwnVo taskAddOwnVo) throws ParseException {
        taskService.AddOwnTask(taskAddOwnVo);
        return AjaxResult.success();
    }


    @ApiOperation("删除个人任务")
    @DeleteMapping("/deleteTask")
    public AjaxResult deleteTask(Long taskId,String nickname,int taskWeek,String schoolYear){
        taskService.deleteTask(taskId,nickname,taskWeek,schoolYear);
        return AjaxResult.success();
    }

    @ApiOperation("更新个人任务")
    @PutMapping("/updateTask")
    public AjaxResult updateTask(@RequestBody @Valid TaskUpdateVo taskUpdateVo){
        taskService.updateTask(taskUpdateVo);
        return AjaxResult.success();
    }

    @ApiOperation("事项查询")
    @GetMapping("/viewTaskByMonth")
    public AjaxResult viewTaskByMonth(String month){
        return AjaxResult.success(taskService.viewTaskByMonth(month));
    }

    @ApiOperation("高级用户发布任务")
    @PostMapping("/releaseTask")
    public AjaxResult releaseTask(@RequestBody @Valid TaskReleaseVo taskReleaseVo){
        taskService.releaseTask(taskReleaseVo);
        return AjaxResult.success();
    }
}
