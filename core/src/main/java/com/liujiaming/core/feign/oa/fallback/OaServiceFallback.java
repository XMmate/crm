package com.liujiaming.core.feign.oa.fallback;

import com.liujiaming.core.common.Result;
import com.liujiaming.core.feign.crm.entity.ExamineField;
import com.liujiaming.core.feign.examine.entity.ExamineConditionDataBO;
import com.liujiaming.core.feign.oa.entity.ExamineVO;
import com.liujiaming.core.feign.oa.service.OaService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
@Slf4j
public class OaServiceFallback implements FallbackFactory<OaService> {
    @Override
    public OaService create(Throwable cause) {
        return new OaService() {
            @Override
            public Result eventNoticeCron() {
                log.error(cause.getMessage());
                return Result.ok();
            }

            @Override
            public Result<List<ExamineVO>> transfer(List<ExamineVO> recordList) {
                log.error(cause.getMessage());
                return Result.ok(new ArrayList<>());
            }

            @Override
            public Result<Boolean> initOaData() {
                log.error(cause.getMessage());
                return Result.ok(false);
            }

            @Override
            public Result<Boolean> initCalendarData() {
                log.error(cause.getMessage());
                return Result.ok(false);
            }

            @Override
            public Result<Boolean> initOaExamineData() {
                log.error(cause.getMessage());
                return Result.ok(false);
            }

            @Override
            public Result<List<ExamineField>> queryExamineField(Integer categoryId) {
                log.error(cause.getMessage());
                return Result.ok(new ArrayList<>());
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
            public Result<ExamineVO> getOaExamineById(Integer oaExamineId) {
                log.error(cause.getMessage());
                return Result.ok();
            }

            @Override
            public Result saveDefaultField(Long categoryId) {
                log.error(cause.getMessage());
                return Result.ok();
            }

            @Override
            public Result<Boolean> updateFieldCategoryId(Long newCategoryId, Long oldCategoryId) {
                log.error(cause.getMessage());
                return Result.ok(false);
            }
        };
    }
}
