package com.liujiaming.crm.controller;

import com.liujiaming.core.common.ApiExplain;
import com.liujiaming.core.common.Result;
import com.liujiaming.crm.service.ICrmCommonService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liujiaming
 * @date 2024/9/16
 */
@RestController
@RequestMapping("/crmAnalysis")
@Api(tags = "crm分析")
public class CrmAnalysisController {

    @Autowired
    private ICrmCommonService crmCommonService;


    /**
     * 初始化crm数据，就是把原来的数据全删了
     * @return
     */
    @PostMapping("/initCrmData")
    @ApiExplain("初始化crm数据")
    public Result<Boolean> initCrmData() {
        return Result.ok(crmCommonService.initCrmData());
    }

}
