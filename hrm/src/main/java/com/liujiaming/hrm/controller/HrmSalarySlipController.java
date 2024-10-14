package com.liujiaming.hrm.controller;


import com.liujiaming.core.common.Result;
import com.liujiaming.core.entity.BasePage;
import com.liujiaming.hrm.entity.BO.QuerySalarySlipListBO;
import com.liujiaming.hrm.entity.VO.QuerySalarySlipListVO;
import com.liujiaming.hrm.service.IHrmSalarySlipService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 工资条 前端控制器
 * </p>
 *
 * @author hmb
 * @since 2024-11-03
 */
@RestController
@RequestMapping("/hrmSalarySlip")
@Api(tags = "工资条-工资条接口")
public class HrmSalarySlipController {

    @Autowired
    private IHrmSalarySlipService salarySlipService;

    @PostMapping("/querySalarySlipList")
    @ApiOperation("（员工端）我的工资条")
    public Result<BasePage<QuerySalarySlipListVO>> querySalarySlipList(@RequestBody QuerySalarySlipListBO querySalarySlipListBO){
        BasePage<QuerySalarySlipListVO> page = salarySlipService.querySalarySlipList(querySalarySlipListBO);
        return Result.ok(page);
    }

}

