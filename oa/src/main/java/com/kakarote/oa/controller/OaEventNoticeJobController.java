package com.kakarote.oa.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.kakarote.core.common.Result;
import com.kakarote.oa.entity.BO.QueryEventListBO;
import com.kakarote.oa.entity.PO.OaEvent;
import com.kakarote.oa.mapper.OaEventMapper;
import com.kakarote.oa.service.IOaEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/oaEventJob")
public class OaEventNoticeJobController {

    @Autowired
    private IOaEventService oaEventService;

    @Autowired
    private OaEventMapper eventMapper;
    /**
     * 定时器日程提醒 供远程job服务调用的
     */
    @PostMapping("/eventNoticeCron")
    public Result eventNoticeCron(){
        //当前日期的开始和结束时间
        DateTime nowDate = DateUtil.date();
        long startTime = DateUtil.beginOfDay(nowDate).getTime();
        long endTime = DateUtil.endOfDay(nowDate).getTime();
        QueryEventListBO queryEventListBO = new QueryEventListBO();
        queryEventListBO.setStartTime(startTime);
        queryEventListBO.setEndTime(endTime);
        List<OaEvent> oaEventList = eventMapper.queryList(queryEventListBO);
        oaEventService.eventNotice(oaEventList);
        return Result.ok();
    }
}
