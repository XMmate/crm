package com.liujiaming.core.feign.examine.service;

import com.liujiaming.core.common.Result;
import com.liujiaming.core.feign.examine.entity.ExamineInfoVo;
import com.liujiaming.core.feign.examine.entity.ExamineRecordLog;
import com.liujiaming.core.feign.examine.entity.ExamineRecordReturnVO;
import com.liujiaming.core.feign.examine.entity.ExamineRecordSaveBO;
import com.liujiaming.core.feign.examine.fallback.ExamineServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 远程调用审批服务
 *
 */
@FeignClient(name = "examine",contextId = "record" ,fallbackFactory = ExamineServiceFallback.class)
public interface ExamineService {

    @PostMapping("/examineRecord/addExamineRecord")
    public Result<ExamineRecordReturnVO> addExamineRecord(@RequestBody ExamineRecordSaveBO examineRecordSaveBO);

    @PostMapping("/examines/queryNormalExamine")
    public Result<List<ExamineInfoVo>> queryNormalExamine(@RequestParam("label") Integer label);

    @PostMapping("/examineRecord/queryExamineLogById")
    public Result<ExamineRecordLog> queryExamineLogById(@RequestParam("examineLogId") Integer examineLogId);

    @PostMapping("/examineRecord/queryExamineRecordInfo")
    public Result<ExamineRecordReturnVO> queryExamineRecordInfo(@RequestParam("recordId") Integer recordId);


    /**
     * 查询审批
     * @param examineId
     * @return
     */
    @PostMapping("/examines/queryExamineById")
    public Result<ExamineInfoVo> queryExamineById(@RequestParam("examineId") Long examineId);

    @PostMapping("/examineRecord/deleteExamineRecord")
    public Result<Boolean> deleteExamineRecord(@RequestParam("recordId") Integer recordId);

    @PostMapping("/examineRecord/updateExamineRecord")
    public Result<Boolean> updateExamineRecord(@RequestParam("recordId") Integer recordId,@RequestParam("examineStatus") Integer examineStatus);

    @PostMapping("/examineWaiting/queryOaExamineIdList")
    public Result<List<Integer>> queryOaExamineIdList(@RequestParam("status")Integer status,@RequestParam("categoryId")Integer categoryId);

    @PostMapping("/examineWaiting/queryCrmExamineIdList")
    public Result<List<Integer>> queryCrmExamineIdList(@RequestParam("label")Integer label,@RequestParam("status")Integer status);

    @PostMapping("/examineRecord/deleteExamineRecordAndLog")
    public Result<Boolean> deleteExamineRecordAndLog(@RequestParam("label") Integer label);

}
