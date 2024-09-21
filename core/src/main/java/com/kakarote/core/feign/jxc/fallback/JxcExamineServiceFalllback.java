package com.kakarote.core.feign.jxc.fallback;

import com.kakarote.core.common.R;
import com.kakarote.core.common.Result;
import com.kakarote.core.feign.crm.entity.ExamineField;
import com.kakarote.core.feign.jxc.entity.JxcExamine;
import com.kakarote.core.feign.jxc.entity.JxcState;
import com.kakarote.core.feign.jxc.service.JxcExamineService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.list.AbstractLinkedList;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
@Component
public class JxcExamineServiceFalllback implements FallbackFactory<JxcExamineService> {
    @Override
    public JxcExamineService create(Throwable cause) {
        return new JxcExamineService() {
            @Override
            public Result examine(Integer label, Integer state, Integer id) {
                log.error(cause.getMessage());
                return Result.ok();
            }

            @Override
            public Result<Map<String, Object>> examineFieldDataMap(Integer label, Integer id) {
                return Result.ok(new HashMap<>());
            }

            @Override
            public Result examineMessage(JxcExamine jxcExamine) {
                log.error(cause.getMessage());
                return Result.ok();
            }

            @Override
            public Result<JxcState> queryJxcById(Integer label, Integer id) {
                log.error(cause.getMessage());
                return Result.ok();
            }

            @Override
            public Result<Boolean> initJxcData() {
                log.error(cause.getMessage());
                return Result.ok(false);

            }

            @Override
            public Result<List<ExamineField>> queryExamineField(Integer label) {
                log.error(cause.getMessage());
                return Result.ok(new ArrayList<>() );
            }
        };
    }
}
