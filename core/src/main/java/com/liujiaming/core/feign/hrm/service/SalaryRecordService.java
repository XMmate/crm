package com.liujiaming.core.feign.hrm.service;

import com.liujiaming.core.common.Result;
import com.liujiaming.core.feign.hrm.entity.HrmSalaryMonthRecord;
import com.liujiaming.core.feign.hrm.fallback.SalaryRecordServiceFallback;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "hrm",contextId = "salary",fallbackFactory = SalaryRecordServiceFallback.class)
public interface SalaryRecordService {

    @PostMapping("/hrmSalaryMonthRecord/querySalaryRecordById")
    @ApiOperation("通过id查询薪资记录")
    Result<HrmSalaryMonthRecord> querySalaryRecordById(@RequestParam("sRecordId") Integer sRecordId);

    @PostMapping("/hrmSalaryMonthRecord/updateCheckStatus")
    @ApiOperation("通过id查询薪资记录")
    Result updateCheckStatus(@RequestParam("sRecordId") Integer sRecordId, @RequestParam("checkStatus") Integer checkStatus);
}