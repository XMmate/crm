package com.liujiaming.oa.feign.service;

import com.liujiaming.core.common.Result;
import com.liujiaming.oa.constart.entity.BO.*;
import com.liujiaming.oa.constart.entity.BO.OaTaskListBO;
import com.liujiaming.oa.constart.entity.BO.WorkTaskLabelBO;
import com.liujiaming.oa.constart.entity.VO.OaTaskListVO;
import com.liujiaming.oa.feign.fallback.IOaTaskServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author liujiaming
 */
@Component
@FeignClient(name = "work",fallbackFactory = IOaTaskServiceFallback.class)
public interface IOaTaskService {

    @PostMapping("/workTask/deleteWorkTaskLabel")
    public Result deleteWorkTaskLabel(@RequestBody WorkTaskLabelBO workTaskLabelBO);

    @PostMapping("/workTask/queryTaskList")
    public Result<OaTaskListVO> queryTaskList(@RequestBody OaTaskListBO oaTaskListBO);
}
