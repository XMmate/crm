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
     *根据审批类型查询审批需要设置的字段 例如请假审批需要什么字段
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
     * (企业后台)调用这个接口
     * lable=0 办公
     * lable为空就是业务
     * @param examinePageBo
     * @return
     */
    @PostMapping("/queryList")
    @ApiOperation("按条件查询(办公)(业务)审批流列表")
    public Result<BasePage<ExamineVO>> queryList(@RequestBody ExaminePageBO examinePageBo) {
        examinePageBo.setIsPart(false);
        BasePage<ExamineVO> voBasePage = examineService.queryList(examinePageBo);
        return Result.ok(voBasePage);
    }

    /**（任务审批模块）
     * 新建审批时弹出的审批类型表
     * @param examinePageBo
     * @return
     */
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

    /**
     * 根据examineId启用/停用/删除审批流 系统限制只能有一个在启用
     * （企业后台）
     * @param status 1是启用 2是停用 3是删除
     * @param examineId 审批流id
     * @return
     */
    @PostMapping("/updateStatus")
    @ApiOperation("根据examineId启用/停用/删除审批流")
    public Result updateStatus(@RequestParam("status") Integer status, @RequestParam("examineId") Long examineId) {
        examineService.updateStatus(examineId,status,true);
        return Result.ok();
    }

    /**
     * 新建审批流数据
     * 创建审批流（企业后台）
     * @param examineSaveBO
     * @return
     */
    @PostMapping("/addExamine")
    @ApiOperation("保存新建审批流数据")
    public Result<Examine> addExamine(@RequestBody ExamineSaveBO examineSaveBO) {
        return Result.ok(examineService.addExamine(examineSaveBO));
    }

    /**
     * 根据examineId获取单条审批流的详情信息
     * 使用地方（企业后台）
     * @param examineId
     * @return
     */
    @PostMapping("/queryExamineFlow")
    @ApiOperation("获取审批流详情")
    public Result<List<ExamineFlowVO>> queryExamineFlow(@RequestParam("examineId") Long examineId){
        List<ExamineFlowVO> examineFlowVOList = examineService.queryExamineFlow(examineId);
        return Result.ok(examineFlowVOList);
    }

    /**
     *todo 不清楚干什么的
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
    @ApiOperation("获取填写审批流程需要的字段")
    public Result<ExaminePreviewVO> previewExamineFlow(@RequestBody ExaminePreviewBO examinePreviewBO){
        ExaminePreviewVO examineFlowVO = examineService.previewExamineFlow(examinePreviewBO);
        return Result.ok(examineFlowVO);
    }
}

