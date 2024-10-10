package com.kakarote.examine.controller;


import com.kakarote.core.common.ApiExplain;
import com.kakarote.core.common.Result;
import com.kakarote.core.entity.BasePage;
import com.kakarote.core.feign.crm.entity.ExamineField;
import com.kakarote.examine.entity.BO.ExaminePageBO;
import com.kakarote.examine.entity.BO.ExaminePreviewBO;
import com.kakarote.examine.entity.BO.ExamineSaveBO;
import com.kakarote.examine.entity.PO.Examine;
import com.kakarote.examine.entity.VO.ExamineFlowConditionDataVO;
import com.kakarote.examine.entity.VO.ExamineFlowVO;
import com.kakarote.examine.entity.VO.ExaminePreviewVO;
import com.kakarote.examine.entity.VO.ExamineVO;
import com.kakarote.examine.service.IExamineService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 审批表 前端控制器
 * </p>
 *
 * @author liujiaming
 * @since 2024-11-13
 */
@RestController
@RequestMapping("/examines")
public class ExamineController {

    @Autowired
    private IExamineService examineService;

    /**
     *查询审批需要设置的字段 例如请假审批需要什么字段
     * @param label "暂时不知道这是什么"
     * @param categoryId 类型id
     * @return
     */
    @PostMapping("/queryField")
    @ApiOperation("查询审批可供设置的字段")
    public Result<List<ExamineField>> queryField(@RequestParam("label") Integer label, @RequestParam(value = "categoryId", required = false) Integer categoryId) {
        List<ExamineField> fieldList = examineService.queryField(label, categoryId);
        return Result.ok(fieldList);
    }

    @PostMapping("/queryExamineById")
    @ApiOperation("查询审批类型详情")
    public Result<Examine> queryExamineById(@RequestParam("examineId") Long examineId) {
        return Result.ok(examineService.getById(examineId));
    }

    /**
     * 企业后台调用这个接口
     * lable=0 办公
     * lable为空就是业务
     * @param examinePageBo
     * @return
     */
    @PostMapping("/queryList")
    @ApiOperation("查询全部审批流列表")
    public Result<BasePage<ExamineVO>> queryList(@RequestBody ExaminePageBO examinePageBo) {
        examinePageBo.setIsPart(false);
        BasePage<ExamineVO> voBasePage = examineService.queryList(examinePageBo);
        return Result.ok(voBasePage);
    }

    @PostMapping("/queryPartList")
    @ApiOperation("查询正常审批审批类型列表")
    public Result<BasePage<ExamineVO>> queryPartList(@RequestBody ExaminePageBO examinePageBo) {
        examinePageBo.setIsPart(true);
        BasePage<ExamineVO> voBasePage = examineService.queryList(examinePageBo);
        return Result.ok(voBasePage);
    }


    @PostMapping("/queryNormalExamine")
    @ApiExplain("查询可用审批个数")
    public Result<List<Examine>> queryNormalExamine(@RequestParam("label") Integer label) {
        List<Examine> examineList = examineService.lambdaQuery().eq(Examine::getLabel, label).eq(Examine::getStatus, 1).list();
        return Result.ok(examineList);
    }

    @PostMapping("/updateStatus")
    @ApiOperation("修改审批状态")
    public Result updateStatus(@RequestParam("status") Integer status, @RequestParam("examineId") Long examineId) {
        examineService.updateStatus(examineId,status,true);
        return Result.ok();
    }

    @PostMapping("/addExamine")
    @ApiOperation("保存审批数据")
    public Result<Examine> addExamine(@RequestBody ExamineSaveBO examineSaveBO) {
        return Result.ok(examineService.addExamine(examineSaveBO));
    }

    @PostMapping("/queryExamineFlow")
    @ApiOperation("获取审批详情")
    public Result<List<ExamineFlowVO>> queryExamineFlow(@RequestParam("examineId") Long examineId){
        List<ExamineFlowVO> examineFlowVOList = examineService.queryExamineFlow(examineId);
        return Result.ok(examineFlowVOList);
    }

    /**
     *
     * @param examinePreviewBO
     * @return
     */
    @PostMapping("/previewFiledName")
    @ApiOperation("获取审批流程条件，字段")
    public Result<List<ExamineFlowConditionDataVO>> previewFiledName(@RequestBody ExaminePreviewBO examinePreviewBO){
        List<ExamineFlowConditionDataVO> filedNameList = examineService.previewFiledName(examinePreviewBO.getLabel(),examinePreviewBO.getRecordId(),examinePreviewBO.getExamineId());
        return Result.ok(filedNameList);
    }

    /**填写审批流程需要的字段 一级审批 二级审批
     * @param examinePreviewBO
     * @return
     */
    @PostMapping("/previewExamineFlow")
    @ApiOperation("填写审批流程需要的字段")
    public Result<ExaminePreviewVO> previewExamineFlow(@RequestBody ExaminePreviewBO examinePreviewBO){
        ExaminePreviewVO examineFlowVO = examineService.previewExamineFlow(examinePreviewBO);
        return Result.ok(examineFlowVO);
    }
}

