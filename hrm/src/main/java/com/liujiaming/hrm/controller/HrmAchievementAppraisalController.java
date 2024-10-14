package com.liujiaming.hrm.controller;

import com.liujiaming.core.common.Result;
import com.liujiaming.core.entity.BasePage;
import com.liujiaming.hrm.entity.BO.*;
import com.liujiaming.hrm.entity.VO.*;
import com.liujiaming.hrm.entity.BO.*;
import com.liujiaming.hrm.entity.VO.*;
import com.liujiaming.hrm.service.IHrmAchievementAppraisalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * <p>
 * 绩效考核 前端控制器
 * </p>
 *
 * @author huangmingbo
 * @since 2024-05-12
 */
@RestController
@RequestMapping("/hrmAchievementAppraisal")
@Api(tags = "绩效考核-管理端")
public class HrmAchievementAppraisalController {


    @Autowired
    private IHrmAchievementAppraisalService achievementAppraisalService;

    /**
     * 添加或修改考核
     * 没到默认是不开启的 要手动开启考核
     */
    @PostMapping("/addAppraisal")
    @ApiOperation("添加考核")
    public Result addAppraisal(@Valid @RequestBody SetAppraisalBO setAppraisalBO){
        achievementAppraisalService.addAppraisal(setAppraisalBO);
        return Result.ok();
    }

    @PostMapping("/setAppraisal")
    @ApiOperation("修改考核内容")
    public Result setAppraisal(@Valid @RequestBody SetAppraisalBO setAppraisalBO){
        achievementAppraisalService.setAppraisal(setAppraisalBO);
        return Result.ok();
    }


    @PostMapping("/delete/{appraisalId}")
    @ApiOperation("根据ID删除考核")
    public Result deleteAppraisal(@PathVariable Integer appraisalId){
        achievementAppraisalService.deleteAppraisal(appraisalId);
        return Result.ok();
    }

    @PostMapping("/stopAppraisal/{appraisalId}")
    @ApiOperation("根据ID终止考核")
    public Result stopAppraisal(@PathVariable Integer appraisalId){
        achievementAppraisalService.stopAppraisal(appraisalId);
        return Result.ok();
    }

    /**
     * 开启考核/结束考核
     * @param updateAppraisalStatusBO
     * @return
     */
    @PostMapping("/updateAppraisalStatus")
    @ApiOperation("修改考核状态")
    public Result updateAppraisalStatus(@RequestBody UpdateAppraisalStatusBO updateAppraisalStatusBO){
        achievementAppraisalService.updateAppraisalStatus(updateAppraisalStatusBO);
        return Result.ok();
    }


    /**
     * 查询每个绩效状态的数量
     *       //  0 未开启考核 1 目标填写/目标确认中 2 结果评定中 3 结果确认中 4 归档 5 进行中的考核
     * @return
     */
    @PostMapping("/queryAppraisalStatusNum")
    @ApiOperation("查询每个绩效状态的数量")
    public Result<Map<Integer, Long>> queryAppraisalStatusNum(){
        Map<Integer, Long> map = achievementAppraisalService.queryAppraisalStatusNum();
        return Result.ok(map);
    }

    @PostMapping("/queryAppraisalPageList")
    @ApiOperation("查询绩效考核列表")
    public Result<BasePage<AppraisalPageListVO>> queryAppraisalPageList(@RequestBody QueryAppraisalPageListBO queryAppraisalPageListBO){
        BasePage<AppraisalPageListVO> page = achievementAppraisalService.queryAppraisalPageList(queryAppraisalPageListBO);
        return Result.ok(page);
    }

    @PostMapping("/queryAppraisalById/{appraisalId}")
    @ApiOperation("查询考核详情")
    public Result<AppraisalInformationBO> queryAppraisalById(@PathVariable Integer appraisalId){
        AppraisalInformationBO informationBO= achievementAppraisalService.queryAppraisalById(appraisalId);
        return Result.ok(informationBO);
    }


    @PostMapping("/queryEmployeeListByAppraisalId")
    @ApiOperation("通过绩效id查询员工列表")
    public Result<BasePage<EmployeeListByAppraisalIdVO>> queryEmployeeListByAppraisalId(@RequestBody QueryEmployeeListByAppraisalIdBO employeeListByAppraisalIdBO){
        BasePage<EmployeeListByAppraisalIdVO> page = achievementAppraisalService.queryEmployeeListByAppraisalId(employeeListByAppraisalIdBO);
        return Result.ok(page);
    }

    @PostMapping("/queryAppraisalEmployeeList")
    @ApiOperation("员工绩效列表")
    public Result<BasePage<AppraisalEmployeeListVO>> queryAppraisalEmployeeList(@RequestBody QueryAppraisalEmployeeListBO employeeListBO){
        BasePage<AppraisalEmployeeListVO> page = achievementAppraisalService.queryAppraisalEmployeeList(employeeListBO);
        return Result.ok(page);
    }

    @PostMapping("/queryEmployeeAppraisal")
    @ApiOperation("员工绩效详情列表")
    public Result<BasePage<EmployeeAppraisalVO>> queryEmployeeAppraisal(@RequestBody QueryEmployeeAppraisalBO queryEmployeeAppraisalBO){
        BasePage<EmployeeAppraisalVO> page = achievementAppraisalService.queryEmployeeAppraisal(queryEmployeeAppraisalBO);
        return Result.ok(page);
    }

    @PostMapping("/queryEmployeeAppraisalCount/{employeeId}")
    @ApiOperation("员工绩效详情统计")
    public Result<Map<String,Object>> queryEmployeeAppraisalCount(@PathVariable Integer employeeId){
        Map<String,Object> map = achievementAppraisalService.queryEmployeeAppraisalCount(employeeId);
        return Result.ok(map);
    }

    @PostMapping("/queryEmployeeDetail/{employeeAppraisalId}")
    @ApiOperation("查询员工考核详情")
    public Result<EmployeeAppraisalDetailVO> queryEmployeeDetail(@PathVariable Integer employeeAppraisalId){
        EmployeeAppraisalDetailVO employeeAppraisalDetailVO =  achievementAppraisalService.queryEmployeeDetail(employeeAppraisalId);
        return Result.ok(employeeAppraisalDetailVO);

    }

}

