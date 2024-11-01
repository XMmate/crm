package com.liujiaming.crm.controller;


import com.liujiaming.core.common.Result;
import com.liujiaming.crm.entity.VO.CrmActionRecordVO;
import com.liujiaming.crm.service.ICrmActionRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 字段操作记录表 前端控制器
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-25
 */
@RestController
@RequestMapping("/crmActionRecord")
@Api(tags = "")
public class CrmActionRecordController {

    @Autowired
    private ICrmActionRecordService crmActionRecordService;

    @PostMapping("/queryRecordOptions")
    @ApiOperation("查询跟进方式列表")
    public Result<List<String>> queryRecordOptions() {
        List<String> strings = crmActionRecordService.queryRecordOptions();
        return Result.ok(strings);
    }

    @PostMapping("/queryRecordList")
    @ApiOperation("查看操作记录")
    public Result<List<CrmActionRecordVO>> queryRecordList(@RequestParam("actionId") Integer actionId, @RequestParam("types") Integer types) {
        List<CrmActionRecordVO> recordVOS = crmActionRecordService.queryRecordList(actionId, types);
        return Result.ok(recordVOS);
    }
}

