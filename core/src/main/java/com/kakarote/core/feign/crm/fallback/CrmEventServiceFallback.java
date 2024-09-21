package com.kakarote.core.feign.crm.fallback;

import com.kakarote.core.common.R;
import com.kakarote.core.common.Result;
import com.kakarote.core.entity.BasePage;
import com.kakarote.core.feign.crm.entity.CrmEventBO;
import com.kakarote.core.feign.crm.entity.QueryEventCrmPageBO;
import com.kakarote.core.feign.crm.service.CrmEventService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class CrmEventServiceFallback implements FallbackFactory<CrmEventService> {
    @Override
    public CrmEventService create(Throwable cause) {
        return new CrmEventService() {
            @Override
            public Result<List<String>> endContract(CrmEventBO crmEventBO) {
                log.error(cause.getMessage());
                return Result.ok(new ArrayList<>());
            }

            @Override
            public Result<List<String>> eventCustomer(CrmEventBO crmEventBO) {
                log.error(cause.getMessage());
                return Result.ok(new ArrayList<>());
            }

            @Override
            public Result<List<String>> eventLeads(CrmEventBO crmEventBO) {
                log.error(cause.getMessage());
                return Result.ok(new ArrayList<>());
            }

            @Override
            public Result<List<String>> eventBusiness(CrmEventBO crmEventBO) {
                log.error(cause.getMessage());
                return Result.ok(new ArrayList<>());
            }

            @Override
            public Result<List<String>> eventDealBusiness(CrmEventBO crmEventBO) {
                log.error(cause.getMessage());
                return Result.ok(new ArrayList<>());
            }

            @Override
            public Result<List<String>> receiveContract(CrmEventBO crmEventBO) {
                log.error(cause.getMessage());
                return Result.ok(new ArrayList<>());
            }

            @Override
            public Result<BasePage<Map<String, Object>>> eventCustomerPageList(QueryEventCrmPageBO eventCrmPageBO) {
                log.error(cause.getMessage());
                return Result.ok(new BasePage<>());
            }

            @Override
            public Result<BasePage<Map<String, Object>>> eventLeadsPageList(QueryEventCrmPageBO eventCrmPageBO) {
                log.error(cause.getMessage());
                return Result.ok(new BasePage<>());
            }

            @Override
            public Result<BasePage<Map<String, Object>>> eventBusinessPageList(QueryEventCrmPageBO eventCrmPageBO) {
                log.error(cause.getMessage());
                return Result.ok(new BasePage<>());
            }

            @Override
            public Result<BasePage<Map<String, Object>>> eventDealBusinessPageList(QueryEventCrmPageBO eventCrmPageBO) {
                log.error(cause.getMessage());
                return Result.ok(new BasePage<>());
            }

            @Override
            public Result<BasePage<Map<String, Object>>> eventContractPageList(QueryEventCrmPageBO eventCrmPageBO) {
                log.error(cause.getMessage());
                return Result.ok(new BasePage<>());
            }
        };
    }
}
