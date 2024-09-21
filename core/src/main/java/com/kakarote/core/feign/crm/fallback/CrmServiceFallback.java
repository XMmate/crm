package com.kakarote.core.feign.crm.fallback;

import com.kakarote.core.common.Result;
import com.kakarote.core.entity.BasePage;
import com.kakarote.core.feign.crm.entity.CrmSearchBO;
import com.kakarote.core.feign.crm.entity.ExamineField;
import com.kakarote.core.feign.crm.entity.SimpleCrmEntity;
import com.kakarote.core.feign.crm.service.CrmService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class CrmServiceFallback implements FallbackFactory<CrmService> {
    @Override
    public CrmService create(Throwable cause) {
        return new CrmService() {
            @Override
            public Result<List<SimpleCrmEntity>> queryCustomerInfo(Collection ids) {
                log.error(cause.getMessage());
                return Result.ok(new ArrayList<>());

            }

            @Override
            public Result<String> queryCustomerName(Integer customerId) {
                log.error(cause.getMessage());
                return Result.ok();
            }

            @Override
            public Result<List<SimpleCrmEntity>> queryLeadsInfo(Collection ids) {
                log.error(cause.getMessage());
                return Result.ok(new ArrayList<>());
            }

            @Override
            public Result<List<SimpleCrmEntity>> queryInvoiceInfo(Collection ids) {
                log.error(cause.getMessage());
                return Result.ok(new ArrayList<>());
            }

            @Override
            public Result<List<SimpleCrmEntity>> queryReceivablesInfo(Collection ids) {
                log.error(cause.getMessage());
                return Result.ok(new ArrayList<>());
            }

            @Override
            public Result<List<SimpleCrmEntity>> queryReturnVisitInfo(Collection ids) {
                log.error(cause.getMessage());
                return Result.ok(new ArrayList<>());
            }

            @Override
            public Result<List<SimpleCrmEntity>> queryByNameCustomerInfo(String name) {
                log.error(cause.getMessage());
                return Result.ok(new ArrayList<>());
            }

            @Override
            public Result<List<SimpleCrmEntity>> queryNameCustomerInfo(String name) {
                log.error(cause.getMessage());
                return Result.ok(new ArrayList<>());
            }

            @Override
            public Result<List<SimpleCrmEntity>> queryContactsInfo(Collection ids) {
                log.error(cause.getMessage());
                return Result.ok(new ArrayList<>());
            }

            @Override
            public Result<List<SimpleCrmEntity>> queryBusinessInfo(Collection ids) {
                log.error(cause.getMessage());
                return Result.ok(new ArrayList<>());
            }

            @Override
            public Result<List<SimpleCrmEntity>> queryContractInfo(Collection ids) {
                log.error(cause.getMessage());
                return Result.ok(new ArrayList<>());
            }

            @Override
            public Result<List<SimpleCrmEntity>> queryProductInfo() {
                log.error(cause.getMessage());
                return Result.ok(new ArrayList<>());
            }

            @Override
            public Result addActivity(Integer type, Integer activityType, Integer activityTypeId) {
                log.error(cause.getMessage());
                return Result.ok(new ArrayList<>());
            }

            @Override
            public Result batchUpdateEsData(String id, String name) {
                log.error(cause.getMessage());
                return Result.ok();
            }

            @Override
            public Result updateReceivablesPlan() {
                log.error(cause.getMessage());
                return Result.ok(new ArrayList<>());
            }

            @Override
            public Result putInInternational() {
                log.error(cause.getMessage());
                return Result.ok(new ArrayList<>());
            }

            @Override
            public Result<List> queryPoolNameListByAuth() {
                log.error(cause.getMessage());
                return Result.ok(new ArrayList<>());
            }

            @Override
            public Result<List<ExamineField>> queryExamineField(Integer label) {
                log.error(cause.getMessage());
                return Result.ok(new ArrayList<>());
            }

            @Override
            public Result<BasePage<Map<String, Object>>> queryCustomerPageList(CrmSearchBO search) {
                log.error(cause.getMessage());
                return Result.ok(new BasePage<>());
            }
        };
    }
}
