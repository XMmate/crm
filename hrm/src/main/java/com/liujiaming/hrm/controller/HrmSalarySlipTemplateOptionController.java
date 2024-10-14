package com.liujiaming.hrm.controller;


import com.liujiaming.core.common.Result;
import com.liujiaming.hrm.entity.PO.HrmSalarySlipTemplateOption;
import com.liujiaming.hrm.service.IHrmSalarySlipTemplateOptionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 工资条模板项 前端控制器
 * </p>
 *
 * @author hmb
 * @since 2024-11-03
 */
@RestController
@RequestMapping("/hrmSalarySlipTemplateOption")
@Api(tags = "工资条-工资条模板项接口")
public class HrmSalarySlipTemplateOptionController {
    @Autowired
    private IHrmSalarySlipTemplateOptionService slipTemplateOptionService;
    @PostMapping("/queryTemplateOptionByTemplateId/{templateId}")
    @ApiOperation("查询工资模板项")
    public Result<List<HrmSalarySlipTemplateOption>> queryTemplateOptionByTemplateId(@PathVariable Integer templateId){
        List<HrmSalarySlipTemplateOption> list = slipTemplateOptionService.queryTemplateOptionByTemplateId(templateId);
        return Result.ok(list);
    }

}

