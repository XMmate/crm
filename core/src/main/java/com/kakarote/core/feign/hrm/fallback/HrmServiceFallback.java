package com.kakarote.core.feign.hrm.fallback;

import com.kakarote.core.common.Result;
import com.kakarote.core.feign.hrm.entity.HrmEmployee;
import com.kakarote.core.feign.hrm.service.HrmService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
public class HrmServiceFallback implements FallbackFactory<HrmService> {
    @Override
    public HrmService create(Throwable cause) {
        return new HrmService() {
            @Override
            public Result<Set<HrmEmployee>> queryEmployeeListByIds(List<Integer> employeeIds) {
                log.error(cause.getMessage());
                return Result.ok(new HashSet<>());
            }

            @Override
            public void employeeChangeRecords() {
                log.error(cause.getMessage());
            }

            @Override
            public Result<Boolean> queryIsInHrm() {
                log.error(cause.getMessage());
                return Result.ok(false);
            }
        };
    }
}
