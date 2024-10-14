package com.liujiaming.core.feign.crm.fallback;

import com.alibaba.fastjson.JSONObject;
import com.liujiaming.core.common.Result;
import com.liujiaming.core.feign.crm.entity.CrmExamineData;
import com.liujiaming.core.feign.crm.entity.CrmSaveExamineRecordBO;
import com.liujiaming.core.feign.crm.entity.SimpleCrmInfo;
import com.liujiaming.core.feign.crm.service.CrmExamineService;
import com.liujiaming.core.feign.examine.entity.ExamineConditionDataBO;
import com.liujiaming.core.feign.examine.entity.ExamineMessageBO;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class CrmExamineServiceFallback implements FallbackFactory<CrmExamineService> {
    @Override
    public CrmExamineService create(Throwable cause) {
        return new CrmExamineService() {
            @Override
            public Result<CrmExamineData> saveExamineRecord(CrmSaveExamineRecordBO examineRecordBO) {
                log.error(cause.getMessage());
                return Result.ok();
            }

            @Override
            public Result<List<JSONObject>> queryByRecordId(Integer recordId) {
                log.error(cause.getMessage());
                return Result.ok(new ArrayList<>());
            }

            @Override
            public Result<Boolean> queryExamineStepIsExist(Integer categoryType) {
                log.error(cause.getMessage());
                return Result.ok(false);
            }

            @Override
            public Result<JSONObject> queryExamineRecordList(Integer recordId, Long ownerUserId) {
                log.error(cause.getMessage());
                return Result.ok();
            }

            @Override
            public Result<Map<String, Object>> getDataMapForNewExamine(ExamineConditionDataBO examineConditionDataBO) {
                log.error(cause.getMessage());
                return Result.ok(new HashMap<>());
            }

            @Override
            public Result<Boolean> updateCheckStatusByNewExamine(ExamineConditionDataBO examineConditionDataBO) {
                log.error(cause.getMessage());
                return Result.ok(false);
            }

            @Override
            public Result addMessageForNewExamine(ExamineMessageBO examineMessageBO) {
                log.error(cause.getMessage());
                return Result.ok();
            }

            @Override
            public Result<SimpleCrmInfo> getCrmSimpleInfo(ExamineConditionDataBO examineConditionDataBO) {
                log.error(cause.getMessage());
                return Result.ok();
            }
        };
    }
}
