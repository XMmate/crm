package com.liujiaming.hrm.controller;


import com.liujiaming.core.common.Result;
import com.liujiaming.hrm.entity.BO.QueryRecordListBO;
import com.liujiaming.hrm.entity.VO.QueryRecordListVO;
import com.liujiaming.hrm.service.IHrmActionRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * hrm员工操作记录表 前端控制器
 * </p>
 * @author huangmingbo
 * @since 2024-05-12
 */

/**
 * 前端没找到在那里
 */
@RestController
@RequestMapping("/hrmActionRecord")
@Api(tags = "人力资源-操作记录接口")
public class HrmActionRecordController {

    @Autowired
    private IHrmActionRecordService actionRecordService;


    @PostMapping("/queryRecordList")
    @ApiOperation("查询操作记录列表")
    public Result<List<QueryRecordListVO>> queryRecordList(@RequestBody QueryRecordListBO queryRecordListBO) {
        List<QueryRecordListVO> list = actionRecordService.queryRecordList(queryRecordListBO);
        return Result.ok(list);
    }

}

