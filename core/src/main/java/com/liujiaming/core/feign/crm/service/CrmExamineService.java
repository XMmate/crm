package com.liujiaming.core.feign.crm.service;

import com.alibaba.fastjson.JSONObject;
import com.liujiaming.core.common.Result;
import com.liujiaming.core.feign.crm.entity.CrmExamineData;
import com.liujiaming.core.feign.crm.entity.CrmSaveExamineRecordBO;
import com.liujiaming.core.feign.crm.entity.SimpleCrmInfo;

import com.liujiaming.core.feign.crm.fallback.CrmEventServiceFallback;
import com.liujiaming.core.feign.examine.entity.ExamineConditionDataBO;
import com.liujiaming.core.feign.examine.entity.ExamineMessageBO;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "crm",contextId = "examine",fallbackFactory = CrmEventServiceFallback.class)
public interface CrmExamineService {

    @PostMapping("/crmExamineRecord/saveExamineRecord")
    @ApiModelProperty("/保存审批")
    Result<CrmExamineData> saveExamineRecord(@RequestBody CrmSaveExamineRecordBO examineRecordBO);

    @PostMapping("/crmExamineRecord/queryByRecordId")
    Result<List<JSONObject>> queryByRecordId(@RequestParam("recordId") Integer recordId);

    @PostMapping("/crmExamine/queryExamineStepIsExist")
    Result<Boolean> queryExamineStepIsExist(@RequestParam("categoryType") Integer categoryType);


    /**
     * 查询审批列表
     * @param recordId
     * @param ownerUserId
     * @return
     */
    @PostMapping("/crmExamineRecord/queryExamineRecordList")
    Result<JSONObject> queryExamineRecordList(
            @RequestParam("recordId") Integer recordId,
            @RequestParam("ownerUserId") Long ownerUserId);


    @PostMapping("/crmExamineRecord/queryConditionData")
    Result<Map<String, Object>> getDataMapForNewExamine(@RequestBody ExamineConditionDataBO examineConditionDataBO);


    @PostMapping("/crmExamineRecord/updateCheckStatusByNewExamine")
    Result<Boolean> updateCheckStatusByNewExamine(@RequestBody ExamineConditionDataBO examineConditionDataBO);

    @PostMapping("/crmExamineRecord/addMessageForNewExamine")
    Result addMessageForNewExamine(@RequestBody ExamineMessageBO examineMessageBO);


    @PostMapping("/crmExamineRecord/getCrmSimpleInfo")
    Result<SimpleCrmInfo> getCrmSimpleInfo(@RequestBody ExamineConditionDataBO examineConditionDataBO);
}
