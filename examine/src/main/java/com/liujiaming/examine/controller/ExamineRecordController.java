package com.liujiaming.examine.controller;


import com.liujiaming.core.common.ApiExplain;
import com.liujiaming.core.common.Result;
import com.liujiaming.core.feign.examine.entity.ExamineRecordReturnVO;
import com.liujiaming.core.feign.examine.entity.ExamineRecordSaveBO;
import com.liujiaming.examine.entity.BO.ExamineBO;
import com.liujiaming.examine.entity.PO.ExamineRecordLog;
import com.liujiaming.examine.entity.VO.ExamineRecordLogVO;
import com.liujiaming.examine.entity.VO.ExamineRecordVO;
import com.liujiaming.examine.service.IExamineRecordLogService;
import com.liujiaming.examine.service.IExamineRecordService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 审核记录表 前端控制器
 * </p>
 *
 * @author liujiaming
 * @since 2024-11-19
 */
@RestController
@RequestMapping("/examineRecord")
public class ExamineRecordController {

    @Autowired
    private IExamineRecordService examineRecordService;

    @Autowired
    private IExamineRecordLogService examineRecordLogService;

    @PostMapping("/addExamineRecord")
    @ApiExplain("添加审批记录")
    public Result<ExamineRecordReturnVO> addExamineRecord(@RequestBody ExamineRecordSaveBO examineRecordSaveBO) {
        ExamineRecordReturnVO examineRecordVO = examineRecordService.addExamineRecord(examineRecordSaveBO);
        return Result.ok(examineRecordVO);
    }
    /**
     * 通过 或 拒绝 撤回
     * @param examineBO
     * @return
     */
    @PostMapping("/auditExamine")
    @ApiOperation("进行审批")
    public Result auditExamine(@RequestBody ExamineBO examineBO) {
        examineRecordService.auditExamine(examineBO);
        return Result.ok();
    }

    @PostMapping("/queryExamineRecordLog")
    @ApiOperation("获取完整审批历史记录")
    public Result<List<ExamineRecordLogVO>> queryExamineRecord(@RequestParam(value = "ownerUserId",required = false) String ownerUserId, @RequestParam("recordId") Integer recordId) {
        return Result.ok(examineRecordLogService.queryExamineRecordLog(recordId,ownerUserId));
    }


    @PostMapping("/queryExamineRecordInfo")
    @ApiOperation("获取未结束审批流程的待审核人")
    public Result<ExamineRecordReturnVO> queryExamineRecordInfo(@RequestParam("recordId") Integer recordId) {
        return Result.ok(examineRecordService.queryExamineRecordInfo(recordId));
    }


    @PostMapping("/queryExamineLogById")
    @ApiExplain("获取指定审批历史记录")
    public Result<ExamineRecordLog> queryExamineLogById(@RequestParam("examineLogId") Integer examineLogId) {
        return Result.ok(examineRecordLogService.getById(examineLogId));
    }

    @PostMapping("/deleteExamineRecord")
    @ApiExplain("同步删除审批数据")
    public Result<Boolean> deleteExamineRecord(@RequestParam("recordId") Integer recordId) {
        return Result.ok(examineRecordService.deleteExamineRecord(recordId));
    }

    @PostMapping("/updateExamineRecord")
    @ApiExplain("同步修改审批状态")
    public Result<Boolean> updateExamineRecord(@RequestParam("recordId") Integer recordId,@RequestParam("examineStatus") Integer examineStatus) {
        return Result.ok(examineRecordService.updateExamineRecord(recordId,examineStatus));
    }


    @PostMapping("/deleteExamineRecordAndLog")
    @ApiExplain("初始化用删除审批历史数据")
    public Result<Boolean> deleteExamineRecordAndLog(@RequestParam("label") Integer label) {
        return Result.ok(examineRecordService.deleteExamineRecordAndLog(label));
    }

    @PostMapping("/queryExamineRecordList")
    @ApiOperation("获取审批详情")
    public Result<ExamineRecordVO> queryExamineRecordList(@RequestParam("recordId") Integer recordId, @RequestParam(value = "ownerUserId",required = false) Long ownerUserId) {
        return Result.ok(examineRecordService.queryExamineRecordList(recordId,ownerUserId));
    }


}

