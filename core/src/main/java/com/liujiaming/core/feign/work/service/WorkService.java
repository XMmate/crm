package com.liujiaming.core.feign.work.service;

import com.liujiaming.core.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "work",contextId = "taskJob")
public interface WorkService {


    /**
     * 更新任务
     * @return
     */
    @PostMapping("/workTask/updateTaskJob")
    Result updateTaskJob();

    /**
     * 初始化项目模块
     * @return
     */
    @PostMapping("/work/initWorkData")
    Result<Boolean> initWorkData();
}
