package com.liujiaming.core.feign.jxc.service;


import com.liujiaming.core.common.Result;
import com.liujiaming.core.feign.crm.entity.ExamineField;
import com.liujiaming.core.feign.jxc.entity.JxcExamine;
import com.liujiaming.core.feign.jxc.entity.JxcState;
import com.liujiaming.core.feign.jxc.fallback.JxcExamineServiceFalllback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;


@FeignClient(name = "jxc",contextId = "jxcExamine",fallbackFactory = JxcExamineServiceFalllback.class)
public interface JxcExamineService {
    @PostMapping("/jxcExamine/examine")
    Result examine(@RequestParam("label") Integer label, @RequestParam("state") Integer state, @RequestParam("id") Integer id);

    @PostMapping("/jxcExamine/examineFieldDataMap")
    Result<Map<String, Object>> examineFieldDataMap(@RequestParam("label") Integer label, @RequestParam("id") Integer id);

    @PostMapping("/jxcExamine/examineMessage")
    Result examineMessage(@RequestBody JxcExamine jxcExamine);

    @PostMapping("/jxcExamine/queryJxcById")
    Result<JxcState> queryJxcById(@RequestParam("label") Integer label,@RequestParam("id") Integer id);

    @PostMapping("/jxcExamine/initJxcData")
    Result<Boolean> initJxcData() ;

    @PostMapping("/jxcExamine/queryExamineField")
    Result<List<ExamineField>> queryExamineField(@RequestParam("label") Integer label);

}
