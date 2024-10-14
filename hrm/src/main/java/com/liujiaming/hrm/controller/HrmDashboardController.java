package com.liujiaming.hrm.controller;

import com.liujiaming.core.common.Result;
import com.liujiaming.hrm.entity.BO.QueryNotesByTimeBO;
import com.liujiaming.hrm.entity.BO.QueryNotesStatusBO;
import com.liujiaming.hrm.entity.PO.HrmNotes;
import com.liujiaming.hrm.entity.VO.*;
import com.liujiaming.hrm.entity.VO.*;
import com.liujiaming.hrm.service.IHrmDashboardService;
import com.liujiaming.hrm.service.IHrmNotesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/hrmDashboard")
@Api(tags = "人力资源仪表盘")
public class HrmDashboardController {

    @Autowired
    private IHrmDashboardService dashboardService;

    @Autowired
    private IHrmNotesService notesService;

    /**
     *
     * 在职 入职 待入职 离职 待离职 调岗
     * @return
     */
    @PostMapping("/employeeSurvey")
    @ApiOperation("人事概况")
    public Result<Map<Integer, Long>> employeeSurvey() {
        Map<Integer, Long> map = dashboardService.employeeSurvey();
        return Result.ok(map);
    }

    /**
     * 招聘动态
     *正在招聘职位 评选中 待入职 已入职
     * @return
     */
    @PostMapping("/recruitSurvey")
    @ApiOperation("招聘动态")
    public Result<RecruitSurveyCountVO> recruitSurvey() {
        RecruitSurveyCountVO recruitSurveyCountVO = dashboardService.recruitSurvey();
        return Result.ok(recruitSurveyCountVO);
    }

    /**
     *上月薪资概况
     * 计薪人员  实发工资 部门薪资占比
     * @return
     */

    @PostMapping("/lastSalarySurvey")
    @ApiOperation("上月薪资概况")
    public Result<LastSalarySurveyVO> lastSalarySurvey() {
        LastSalarySurveyVO lastSalarySurveyVO = dashboardService.lastSalarySurvey();
        return Result.ok(lastSalarySurveyVO);
    }


    @PostMapping("/appraisalCountSurvey")
    @ApiOperation("绩效概况统计")
    public Result<Map<Integer,Integer>> appraisalCountSurvey() {
        Map<Integer,Integer> map = dashboardService.appraisalCountSurvey();
        return Result.ok(map);
    }

    @PostMapping("/appraisalSurvey/{status}")
    @ApiOperation("绩效概况")
    public Result<List<AppraisalSurveyVO>> appraisalSurvey(@PathVariable String status) {
        List<AppraisalSurveyVO> appraisalSurveyVOList = dashboardService.appraisalSurvey(status);
        return Result.ok(appraisalSurveyVOList);
    }


    /**
     *待审核薪资 待离职 合同到期 待转正 待入职  生日
     * @return
     */
    @PostMapping("/toDoRemind")
    @ApiOperation("待办提醒")
    public Result<DoRemindVO>  toDoRemind(){
        DoRemindVO doRemindVO = dashboardService.toDoRemind();
        return Result.ok(doRemindVO);
    }

    @PostMapping("/addNotes")
    @ApiOperation("添加备忘录")
    public Result addNotes(@RequestBody HrmNotes notes){
        notesService.addNotes(notes);
        return Result.ok();
    }

    @PostMapping("/deleteNotes/{notesId}")
    @ApiOperation("根据id删除备忘录")
    public Result deleteNotes(@PathVariable String notesId){
        notesService.removeById(notesId);
        return Result.ok();
    }


    /**
     * 查询当天事项
     * 这里包括了很多，自己添加的备忘录，员工的入职信息，员工生日等
     * 根据日期来查询
     * @param queryNotesByTimeBO
     * @return
     */

    @PostMapping("/queryNotesByTime")
    @ApiOperation("根据日期查询当天事项")
    public Result<List<NotesVO>> queryNotesByTime(@RequestBody QueryNotesByTimeBO queryNotesByTimeBO){
        List<NotesVO> notesVOList = dashboardService.queryNotesByTime(queryNotesByTimeBO);
        return Result.ok(notesVOList);
    }

    @PostMapping("/queryNotesStatus")
    @ApiOperation("查询有事项的日期，在日历板上标出来")
    public Result<Set<String>> queryNotesStatus(@RequestBody QueryNotesStatusBO queryNotesByTimeBO){
        Set<String> timeList = dashboardService.queryNotesStatus(queryNotesByTimeBO);
        return Result.ok(timeList);
    }


    @PostMapping("/myTeam")
    @ApiOperation("我的团队(上级角色)")
    public Result<Map<Integer, Long>> myTeam() {
        Map<Integer, Long> map = dashboardService.myTeam();
        return Result.ok(map);
    }

    @PostMapping("/teamSurvey")
    @ApiOperation("团队概况(上级角色)")
    public Result<TeamSurveyVO> teamSurvey() {
        TeamSurveyVO teamSurveyVO = dashboardService.teamSurvey();
        return Result.ok(teamSurveyVO);
    }

    @PostMapping("/mySurvey")
    @ApiOperation("我的概况(员工端)")
    public Result<MySurveyVO> mySurvey() {
        MySurveyVO data = dashboardService.mySurvey();
        return Result.ok(data);
    }

    @PostMapping("/myNotesByTime")
    @ApiOperation("查询当日事项(员工端)")
    public Result<List<NotesVO>> myNotesByTime(@RequestBody QueryNotesByTimeBO queryNotesByTimeBO){
        List<NotesVO> notesVOList = dashboardService.myNotesByTime(queryNotesByTimeBO);
        return Result.ok(notesVOList);
    }

    @PostMapping("/myNotesStatus")
    @ApiOperation("查询当日事项(员工端)")
    public Result<Set<String>> myNotesStatus(@RequestBody QueryNotesStatusBO queryNotesByTimeBO){
        Set<String> timeList = dashboardService.myNotesStatus(queryNotesByTimeBO);
        return Result.ok(timeList);
    }

}
