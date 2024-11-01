package com.liujiaming.work.controller;


import com.liujiaming.core.common.R;
import com.liujiaming.core.common.Result;
import com.liujiaming.work.service.IWorkTaskLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 任务日志表 前端控制器
 * </p>
 * todo 没找到前端页面在那里
 * @author liujiaming
 * @since 2024-05-15
 */
@RestController
@RequestMapping("/workTaskLog")
@Api(tags = "任务活动日志")
public class WorkTaskLogController {
    @Autowired
    private IWorkTaskLogService workTaskLogService;

    @PostMapping("/queryTaskLog/{taskId}")
    @ApiOperation("查询任务活动日志")
    public Result queryTaskLog(@PathVariable Integer taskId){
        return R.ok(workTaskLogService.queryTaskLog(taskId));
    }
}

