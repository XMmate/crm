package com.liujiaming.oa.controller;

import com.liujiaming.core.common.R;
import com.liujiaming.core.common.Result;
import com.liujiaming.core.common.SubModelType;
import com.liujiaming.core.common.log.BehaviorEnum;
import com.liujiaming.core.common.log.SysLog;
import com.liujiaming.core.common.log.SysLogHandler;
import com.liujiaming.oa.common.log.OaEventLog;
import com.liujiaming.oa.entity.BO.*;
import com.liujiaming.oa.entity.BO.*;
import com.liujiaming.oa.entity.VO.QueryEventByIdVO;
import com.liujiaming.oa.service.IOaEventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 日程表 前端控制器
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-15
 */
@RestController
@RequestMapping("/oaEvent")
@Api(tags = "日程-日程")
@SysLog(subModel = SubModelType.OA_CALENDAR,logClass = OaEventLog.class)
public class OaEventController {

    @Autowired
    private IOaEventService oaEventService;

    @PostMapping("/save")
    @ApiOperation("新建日程")
    @SysLogHandler(behavior = BehaviorEnum.SAVE,object = "#setEventBO.event.title",detail = "'新建了日程:'+#setEventBO.event.title")
    public Result save(@RequestBody SetEventBO setEventBO){
        oaEventService.saveEvent(setEventBO);
        return Result.ok();
    }

    @PostMapping("/update")
    @ApiOperation("修改日程")
    @SysLogHandler(behavior = BehaviorEnum.UPDATE,object = "#setEventBO.event.title",detail = "'修改了日程:'+#setEventBO.event.title")
    public Result update(@RequestBody SetEventBO setEventBO){
        oaEventService.updateEvent(setEventBO);
        return Result.ok();
    }

    @PostMapping("/delete")
    @ApiOperation("删除日程")
    @SysLogHandler(behavior = BehaviorEnum.DELETE,object = "",detail = "删除了日程")
    public Result delete(@RequestBody DeleteEventBO deleteEventBO){
        oaEventService.delete(deleteEventBO);
        return Result.ok();
    }

    /**
     * 查询日程
     */
    @PostMapping("/queryList")
    @ApiOperation("查询日程列表")
    public Result<List<OaEventDTO>> queryList(@RequestBody QueryEventListBO queryEventListBO){
        List<OaEventDTO> oaEventList = oaEventService.queryList(queryEventListBO);
        return Result.ok(oaEventList);
    }

    @PostMapping("/queryListStatus")
    @ApiOperation("查询小日程")
    public Result<Set<String>> queryListStatus(@RequestBody QueryEventListBO queryEventListBO){
        Set<String> dateList = oaEventService.queryListStatus(queryEventListBO);
        return Result.ok(dateList);
    }

    @PostMapping("/queryById")
    @ApiOperation("查询日程详情")
    public Result<QueryEventByIdVO> queryById(@RequestBody QueryEventByIdBO queryEventByIdBO){
        QueryEventByIdVO queryEventByIdVO = oaEventService.queryById(queryEventByIdBO);
        return R.ok(queryEventByIdVO);
    }
}

