package com.kakarote.core.feign.hrm.fallback;

import com.kakarote.core.common.Result;
import com.kakarote.core.feign.hrm.entity.HrmSalaryMonthRecord;
import com.kakarote.core.feign.hrm.service.SalaryRecordService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SalaryRecordServiceFallback implements FallbackFactory<SalaryRecordService> {
    @Override
    public SalaryRecordService create(Throwable cause) {
        return new SalaryRecordService() {
            @Override
            public Result<HrmSalaryMonthRecord> querySalaryRecordById(Integer sRecordId) {
                log.error(cause.getMessage());
                return Result.ok();
            }

            @Override
            public Result updateCheckStatus(Integer sRecordId, Integer checkStatus) {
                log.error(cause.getMessage());
                return Result.ok();
            }
        };
    }
}
