package com.liujiaming.hrm.controller;


import com.liujiaming.core.common.Result;
import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.servlet.upload.FileEntity;
import com.liujiaming.hrm.entity.BO.*;
import com.liujiaming.hrm.entity.PO.HrmRecruitCandidate;
import com.liujiaming.hrm.entity.VO.CandidatePageListVO;
import com.liujiaming.hrm.service.IHrmRecruitCandidateService;
import com.liujiaming.hrm.entity.BO.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 招聘候选人表 前端控制器
 * </p>
 *
 * @author huangmingbo
 * @since 2024-05-12
 */
@RestController
@RequestMapping("/hrmRecruitCandidate")
@Api(tags = "招聘管理-候选人接口")
public class HrmRecruitCandidateController {

    @Autowired
    private IHrmRecruitCandidateService recruitCandidateService;



    @PostMapping("/queryPageList")
    @ApiOperation("分页查询/搜索候选人列表页")
    public Result<BasePage<CandidatePageListVO>> queryCandidatePageList(@RequestBody QueryCandidatePageListBO queryCandidatePageListBO){
        BasePage<CandidatePageListVO> page = recruitCandidateService.queryCandidateList(queryCandidatePageListBO);
        return Result.ok(page);
    }


    @PostMapping("/queryById/{candidateId}")
    @ApiOperation("查询候选人基本信息")
    public Result<CandidatePageListVO> queryById(@PathVariable("candidateId") String candidateId){
        CandidatePageListVO candidate = recruitCandidateService.queryById(candidateId);
        return Result.ok(candidate);
    }


    /**
     *
     * @param hrmRecruitCandidate
     * @return
     */
    @PostMapping("/addCandidate")
    @ApiOperation("新建招聘候选人")
    public Result addCandidate(@Validated @RequestBody HrmRecruitCandidate hrmRecruitCandidate){
        recruitCandidateService.addCandidate(hrmRecruitCandidate);
        return Result.ok();
    }

    @PostMapping("/setCandidate")
    @ApiOperation("编辑候选人")
    public Result setCandidate(@Validated @RequestBody HrmRecruitCandidate hrmRecruitCandidate){
        recruitCandidateService.setCandidate(hrmRecruitCandidate);
        return Result.ok();
    }

    /**
     * 删除候选人
     */
    @PostMapping("/deleteById/{candidateId}")
    @ApiOperation("删除候选人")
    public Result deleteById(@PathVariable("candidateId") Integer candidateId){
        recruitCandidateService.deleteById(candidateId);
        return Result.ok();
    }

    @PostMapping("/deleteByIds")
    @ApiOperation("批量删除候选人")
    public Result deleteByIds(@RequestBody List<Integer> candidateIds){
        recruitCandidateService.deleteByIds(candidateIds);
        return Result.ok();
    }



    @PostMapping("/updateCandidateStatus")
    @ApiOperation("批量修改候选人状态")
    public Result updateCandidateStatus(@RequestBody UpdateCandidateStatusBO updateCandidateStatusBO){
        recruitCandidateService.updateCandidateStatus(updateCandidateStatusBO);
        return Result.ok();
    }

    @PostMapping("/updateCandidatePost")
    @ApiOperation("批量修改候选人职位")
    public Result updateCandidatePost(@RequestBody UpdateCandidatePostBO updateCandidatePostBO){
        recruitCandidateService.updateCandidatePost(updateCandidatePostBO);
        return Result.ok();
    }

    @PostMapping("/updateCandidateRecruitChannel")
    @ApiOperation("批量修改候选人招聘渠道")
    public Result updateCandidateRecruitChannel(@RequestBody UpdateCandidateRecruitChannelBO updateCandidateRecruitChannelBO) {
        recruitCandidateService.updateCandidateRecruitChannel(updateCandidateRecruitChannelBO);
        return Result.ok();
    }

    @PostMapping("/eliminateCandidate")
    @ApiOperation("淘汰/流失候选人")
    public Result eliminateCandidate(@RequestBody EliminateCandidateBO eliminateCandidateBO){
        recruitCandidateService.eliminateCandidate(eliminateCandidateBO);
        return Result.ok();
    }


    @PostMapping("/queryCleanCandidateIds")
    @ApiOperation("查询一键清理候选人,查询完之后调用修改状态接口")
    public Result<List<Integer>> queryCleanCandidateIds(@RequestBody QueryCleanCandidateBO queryCleanCandidateBO){
        List<Integer> candidateIds=recruitCandidateService.queryCleanCandidateIds(queryCleanCandidateBO);
        return Result.ok(candidateIds);
    }

    @PostMapping("/queryFile/{candidateId}")
    @ApiOperation("查询候选人附件")
    public Result<List<FileEntity>> queryFile(@PathVariable("candidateId") Integer candidateId){
        return recruitCandidateService.queryFile(candidateId);
    }

    @PostMapping("/queryCandidateStatusNum")
    @ApiOperation("查询每个候选人状态的数量")
    public Result<Map<Integer,Long>> queryCandidateStatusNum(){
        Map<Integer,Long> statusMap = recruitCandidateService.queryCandidateStatusNum();
        return Result.ok(statusMap);
    }



}

