package com.liujiaming.oa.controller;


import cn.hutool.core.util.StrUtil;
import com.liujiaming.core.common.ApiExplain;
import com.liujiaming.core.common.R;
import com.liujiaming.core.common.Result;
import com.liujiaming.oa.entity.BO.ExamineFieldBO;
import com.liujiaming.oa.service.IOaExamineFieldService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 自定义字段表 前端控制器
 * </p>
 *
 * @author liujiaming
 * @since 2024-06-22
 */
@RestController
@RequestMapping("/oaExamineField")
public class OaExamineFieldController {

    @Autowired
    private IOaExamineFieldService examineFieldService;

    /**
     * @author liujiaming
     * 根据categoryId查询新增或编辑字段
     */
    @ApiOperation("查询新增或编辑字段")
    @PostMapping("/queryField/{categoryId}")
    public Result<List> queryField(@PathVariable Integer categoryId,
                                                   @RequestParam(value = "type",required = false) String type){
        if (StrUtil.isNotEmpty(type)) {
            return R.ok(examineFieldService.queryField(categoryId));
        }
        return R.ok(examineFieldService.queryFormPositionField(categoryId));
    }

    @ApiOperation("保存自定义字段")
    @PostMapping("/saveField")
    public Result saveField(@RequestBody ExamineFieldBO examineFieldBO){
        examineFieldService.saveField(examineFieldBO);
        return Result.ok();
    }

    @ApiExplain("保存默认字段")
    @PostMapping("/saveDefaultField")
    public Result saveDefaultField(@RequestParam("categoryId") Long categoryId){
        examineFieldService.saveDefaultField(categoryId);
        return Result.ok();
    }

    @ApiOperation("修改自定义字段")
    @PostMapping("/updateFieldCategoryId")
    public Result<Boolean> updateFieldCategoryId(@RequestParam("newCategoryId") Long newCategoryId,@RequestParam("oldCategoryId") Long oldCategoryId){
        return Result.ok(examineFieldService.updateFieldCategoryId(newCategoryId,oldCategoryId));
    }


}

