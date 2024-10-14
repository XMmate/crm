package com.liujiaming.oa.controller;

import com.liujiaming.core.common.Result;
import com.liujiaming.core.utils.ExcelParseUtil;
import com.liujiaming.oa.constart.entity.BO.*;
import com.liujiaming.oa.constart.entity.BO.OaTaskListBO;
import com.liujiaming.oa.constart.entity.BO.WorkTaskLabelBO;
import com.liujiaming.oa.constart.entity.VO.OaTaskListVO;
import com.liujiaming.oa.feign.service.IOaTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author liujiaming
 */
@RestController
@RequestMapping("/oaTask")
@Api(tags = "oa任务")
public class OaTaskController {

    @Autowired
    private IOaTaskService oaTaskService;

    @PostMapping("/deleteWorkTaskLabel")
    @ApiOperation("删除项目任务标签")
    public Result deleteWorkTaskLabel(@RequestBody WorkTaskLabelBO workTaskLabelBO){
        return oaTaskService.deleteWorkTaskLabel(workTaskLabelBO);
    }

    /**（任务审批模块）
     * 我的任务 下属任务
     * @param oaTaskListBO 这里有详细的注释
     * @return
     */
    @PostMapping("/queryTaskList")
    @ApiOperation("查看oa任务列表")
    public Result<OaTaskListVO> queryTaskList(@RequestBody OaTaskListBO oaTaskListBO){
        Result<OaTaskListVO> listVO = oaTaskService.queryTaskList(oaTaskListBO);
        return listVO;
    }

    /**（任务审批模块）
     * 导出（我的任务）（下属任务）
     * @param oaTaskListBO 有参数说明
     * @param response
     */
    @PostMapping("/oaTaskExport")
    @ApiOperation("任务导出")
    public void oaTaskExport(@RequestBody OaTaskListBO oaTaskListBO,HttpServletResponse response) {
        oaTaskListBO.setIsExport(true);
        OaTaskListVO data = oaTaskService.queryTaskList(oaTaskListBO).getData();
        List<Map<String, Object>> list = data.getExportList();
        List<ExcelParseUtil.ExcelDataEntity> dataList = new ArrayList<>();
        dataList.add(ExcelParseUtil.toEntity("name", "任务名称"));
        dataList.add(ExcelParseUtil.toEntity("description", "任务描述"));
        dataList.add(ExcelParseUtil.toEntity("mainUserName", "负责人"));
        dataList.add(ExcelParseUtil.toEntity("startTime", "开始时间"));
        dataList.add(ExcelParseUtil.toEntity("stopTime", "结束时间"));
        dataList.add(ExcelParseUtil.toEntity("labelName", "标签"));
        dataList.add(ExcelParseUtil.toEntity("ownerUserName", "参与人"));
        dataList.add(ExcelParseUtil.toEntity("priority", "优先级"));
        dataList.add(ExcelParseUtil.toEntity("createUserName", "创建人"));
        dataList.add(ExcelParseUtil.toEntity("createTime", "创建时间"));
        dataList.add(ExcelParseUtil.toEntity("relateCrmWork", "关联业务"));
        ExcelParseUtil.exportExcel(list, new ExcelParseUtil.ExcelParseService() {
            @Override
            public void castData(Map<String, Object> record, Map<String, Integer> headMap) {

            }
            @Override
            public String getExcelName() {
                return "任务";
            }
        }, dataList);
    }

}
