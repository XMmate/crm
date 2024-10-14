package com.liujiaming.work.controller;


import com.liujiaming.core.common.R;
import com.liujiaming.core.common.Result;
import com.liujiaming.work.entity.PO.WorkTaskClass;
import com.liujiaming.work.service.IWorkTaskClassService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 任务分类表 前端控制器
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-15
 */
@RestController
@RequestMapping("/workTaskClass")
@Api(tags = "任务列表")
public class WorkTaskClassController {
    @Autowired
    private IWorkTaskClassService workTaskClassService;

    @PostMapping("/queryClassNameByWorkId/{workId}")
    @ApiOperation("根据项目id查询分类列表")
    public Result queryClassNameByWorkId(@PathVariable @NotNull Integer workId){
        return R.ok(workTaskClassService.queryClassNameByWorkId(workId));
    }

    @PostMapping("/saveWorkTaskClass")
    @ApiOperation("新建任务列表")
    public Result saveWorkTaskClass(@RequestBody WorkTaskClass workTaskClass){
        workTaskClassService.saveWorkTaskClass(workTaskClass);
        return R.ok();
    }

    @PostMapping("/updateWorkTaskClass")
    @ApiOperation("编辑任务列表")
    public Result updateWorkTaskClass(@RequestBody WorkTaskClass workTaskClass){
        workTaskClassService.updateWorkTaskClass(workTaskClass);
        return R.ok();
    }
}

