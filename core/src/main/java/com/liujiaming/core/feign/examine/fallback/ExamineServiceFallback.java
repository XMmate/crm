package com.liujiaming.core.feign.examine.fallback;

import com.liujiaming.core.common.Result;
import com.liujiaming.core.feign.examine.entity.ExamineInfoVo;
import com.liujiaming.core.feign.examine.entity.ExamineRecordLog;
import com.liujiaming.core.feign.examine.entity.ExamineRecordReturnVO;
import com.liujiaming.core.feign.examine.entity.ExamineRecordSaveBO;
import com.liujiaming.core.feign.examine.service.ExamineService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@Component
public class ExamineServiceFallback implements FallbackFactory<ExamineService> {
    @Override
    public ExamineService create(Throwable cause) {
        return new ExamineService() {
            @Override
            public Result<ExamineRecordReturnVO> addExamineRecord(ExamineRecordSaveBO examineRecordSaveBO) {
                log.error(cause.getMessage());
                return Result.ok();
            }

            @Override
            public Result<List<ExamineInfoVo>> queryNormalExamine(Integer label) {
                log.error(cause.getMessage());
                return Result.ok(new ArrayList<>());
            }

            @Override
            public Result<ExamineRecordLog> queryExamineLogById(Integer examineLogId) {
                log.error(cause.getMessage());
                return Result.ok();
            }

            @Override
            public Result<ExamineRecordReturnVO> queryExamineRecordInfo(Integer recordId) {
                log.error(cause.getMessage());
                return Result.ok();
            }

            @Override
            public Result<ExamineInfoVo> queryExamineById(Long examineId) {
                log.error(cause.getMessage());
                return Result.ok();
            }

            @Override
            public Result<Boolean> deleteExamineRecord(Integer recordId) {
                log.error(cause.getMessage());
                return Result.ok(false);
            }

            @Override
            public Result<Boolean> updateExamineRecord(Integer recordId, Integer examineStatus) {
                log.error(cause.getMessage());
                return Result.ok(false);
            }

            @Override
            public Result<List<Integer>> queryOaExamineIdList(Integer status, Integer categoryId) {
                log.error(cause.getMessage());
                return Result.ok(new ArrayList<>());
            }

            @Override
            public Result<List<Integer>> queryCrmExamineIdList(Integer label, Integer status) {
                log.error(cause.getMessage());
                return Result.ok(new ArrayList<>());
            }

            @Override
            public Result<Boolean> deleteExamineRecordAndLog(Integer label) {
                log.error(cause.getMessage());
                return Result.ok();
            }
        };
    }
}
