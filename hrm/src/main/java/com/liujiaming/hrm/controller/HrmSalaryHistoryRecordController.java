package com.liujiaming.hrm.controller;


import com.liujiaming.core.common.Result;
import com.liujiaming.core.entity.BasePage;
import com.liujiaming.hrm.entity.BO.QueryHistorySalaryDetailBO;
import com.liujiaming.hrm.entity.BO.QueryHistorySalaryListBO;
import com.liujiaming.hrm.entity.VO.QueryHistorySalaryDetailVO;
import com.liujiaming.hrm.entity.VO.QueryHistorySalaryListVO;
import com.liujiaming.hrm.service.IHrmSalaryMonthRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-26
 */
@RestController
@RequestMapping("/hrmSalaryHistoryRecord")
@Api(tags = "薪资管理-历史薪资")
public class HrmSalaryHistoryRecordController {

    @Autowired
    private IHrmSalaryMonthRecordService salaryMonthRecordService;

    @PostMapping("/queryHistorySalaryList")
    @ApiOperation("查询历史薪资列表")
    public Result<BasePage<QueryHistorySalaryListVO>> queryHistorySalaryList(@RequestBody QueryHistorySalaryListBO queryHistorySalaryListBO){
        BasePage<QueryHistorySalaryListVO> page = salaryMonthRecordService.queryHistorySalaryList(queryHistorySalaryListBO);
        return Result.ok(page);
    }

    @PostMapping("/queryHistorySalaryDetail")
    @ApiOperation("查询历史薪资详情")
    public Result<QueryHistorySalaryDetailVO> queryHistorySalaryDetail(@RequestBody QueryHistorySalaryDetailBO queryHistorySalaryDetailBO){
        QueryHistorySalaryDetailVO historySalaryDetail = salaryMonthRecordService.queryHistorySalaryDetail(queryHistorySalaryDetailBO);
        return Result.ok(historySalaryDetail);
    }

}

