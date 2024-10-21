package com.liujiaming.examine.controller;

import com.liujiaming.core.common.ApiExplain;
import com.liujiaming.core.common.Result;
import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.feign.oa.entity.ExamineVO;
import com.liujiaming.examine.entity.BO.ExaminePageBO;
import com.liujiaming.examine.entity.VO.ExamineRecordInfoVO;
import com.liujiaming.examine.service.IExamineService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author liujiaming
 * @date 2024/12/24
 */
@RestController
@RequestMapping("/examineWaiting")
@ApiModel("待审批/审批模块")
public class MyExamineController {
    @Autowired
    private IExamineService examineService;

    @PostMapping("/queryOaExamineList")
    @ApiOperation("查询OA审批流列表")
    public Result<BasePage<ExamineVO>> queryOaExamineList(@RequestBody ExaminePageBO examinePageBo) {
        BasePage<ExamineVO> voBasePage = examineService.queryOaExamineList(examinePageBo);
        return Result.ok(voBasePage);
    }

    /**
     * 待我审批（办公 合同 回款 发票）等
     * @param examinePageBo
     * @return
     */
    @PostMapping("/queryCrmExamineList")
    @ApiOperation("查询CRM待审批流列表")
    public Result<BasePage<ExamineRecordInfoVO>> queryCrmExamineList(@RequestBody ExaminePageBO examinePageBo) {
        BasePage<ExamineRecordInfoVO> voBasePage = examineService.queryCrmExamineList(examinePageBo);
        return Result.ok(voBasePage);
    }


    @PostMapping("/queryOaExamineIdList")
    @ApiExplain("查询OA审批流关联业务主键列表")
    public Result<List<Integer>> queryOaExamineIdList(@RequestParam(value = "status",required = false)Integer status, @RequestParam("categoryId")Integer categoryId) {
        return Result.ok(examineService.queryOaExamineIdList(status,categoryId));
    }

    @PostMapping("/queryCrmExamineIdList")
    @ApiExplain("根据条件查询审批流程表ID列表")
    public Result<List<Integer>> queryCrmExamineIdList(@RequestParam("label")Integer label,@RequestParam(value = "status",required = false)Integer status) {
        return Result.ok(examineService.queryCrmExamineIdList(label,status));
    }

}
