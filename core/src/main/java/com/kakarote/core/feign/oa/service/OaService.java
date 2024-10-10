package com.kakarote.core.feign.oa.service;

import com.kakarote.core.common.Result;
import com.kakarote.core.feign.crm.entity.ExamineField;
import com.kakarote.core.feign.examine.entity.ExamineConditionDataBO;
import com.kakarote.core.feign.oa.entity.ExamineVO;
import com.kakarote.core.feign.oa.fallback.OaServiceFallback;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "oa",contextId = "eventJob",fallbackFactory = OaServiceFallback.class)
public interface OaService {

    @PostMapping("/oaEventJob/eventNoticeCron")
    Result eventNoticeCron();

    @PostMapping("/oaExamine/transfer")
    @ApiOperation("转换审批")
    Result<List<ExamineVO>> transfer(@RequestBody List<ExamineVO> recordList);

    /**
     * 初始化OA
     * @return
     */
    @PostMapping("/oaLog/initOaData")
    Result<Boolean> initOaData();

    /**
     * 初始化日程
     * @return
     */
    @PostMapping("/oaLog/initCalendarData")
    Result<Boolean> initCalendarData();

    /**
     * 初始化审查
     * @return
     */
    @PostMapping("/oaLog/initOaExamineData")
    Result<Boolean> initOaExamineData();


    /**
     * 查询字段
     * @param categoryId
     * @return
     */
    @PostMapping("/oaExamine/queryExamineField")
    Result<List<ExamineField>> queryExamineField(@RequestParam("categoryId") Integer categoryId);

    /**
     * 查询条件字段
     * @param examineConditionDataBO
     * @return
     */
    @PostMapping("/oaExamine/queryConditionData")
    Result<Map<String, Object>> getDataMapForNewExamine(@RequestBody ExamineConditionDataBO examineConditionDataBO);


    /**
     * 通过 NewExamine 更新检查状态
     * @param examineConditionDataBO
     * @return
     */
    @PostMapping("/oaExamine/updateCheckStatusByNewExamine")
    Result<Boolean> updateCheckStatusByNewExamine(@RequestBody ExamineConditionDataBO examineConditionDataBO);


    /**
     * 获取指定的审批信息
     * @param oaExamineId
     * @return
     */
    @PostMapping("/oaExamine/getOaExamineById")
    Result<ExamineVO> getOaExamineById(@RequestParam("oaExamineId") Integer oaExamineId);

    /**
     * 保存默认字段
     * @param categoryId
     * @return
     */
    @PostMapping("/oaExamineField/saveDefaultField")
    Result saveDefaultField(@RequestParam("categoryId") Long categoryId);

    /**
     * 修改自定义字段
     * @param newCategoryId
     * @param oldCategoryId
     * @return
     */
    @PostMapping("/oaExamineField/updateFieldCategoryId")
    Result<Boolean> updateFieldCategoryId(@RequestParam("newCategoryId") Long newCategoryId,@RequestParam("oldCategoryId") Long oldCategoryId);
}
