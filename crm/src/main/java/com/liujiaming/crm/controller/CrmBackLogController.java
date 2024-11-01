package com.liujiaming.crm.controller;


import com.alibaba.fastjson.JSONObject;
import com.liujiaming.core.common.Result;
import com.liujiaming.core.entity.BasePage;
import com.liujiaming.crm.entity.BO.CrmBackLogBO;
import com.liujiaming.crm.entity.PO.CrmReceivablesPlan;
import com.liujiaming.crm.service.ICrmBackLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 待办事项前端控制器
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-23
 */
@RestController
@RequestMapping("/crmBackLog")
@Api(tags = "待办事项模块")
public class CrmBackLogController {

    @Autowired
    private ICrmBackLogService crmBackLogService;


    @PostMapping("/num")
    @ApiOperation("查询待办事项数量")
    public Result<JSONObject> num() {
        JSONObject num = crmBackLogService.num();
        return Result.ok(num);
    }

    @PostMapping("/todayLeads")
    @ApiOperation("今日需联系线索 type(1今日需联系，2已逾期 3已联系) ")
    public Result<BasePage<Map<String, Object>>> todayLeads(@RequestBody CrmBackLogBO crmBackLogBO) {
        BasePage<Map<String, Object>> basePage = crmBackLogService.todayLeads(crmBackLogBO);
        return Result.ok(basePage);
    }

    @PostMapping("/todayCustomer")
    @ApiOperation("今日需联系客户 type(1今日需联系，2已逾期 3已联系）")
    public Result<BasePage<Map<String, Object>>> todayCustomer(@RequestBody CrmBackLogBO crmBackLogBO) {
        BasePage<Map<String, Object>> basePage = crmBackLogService.todayCustomer(crmBackLogBO);
        return Result.ok(basePage);
    }

    @PostMapping("/todayBusiness")
    @ApiOperation("今日需联系商机 type(1今日需联系，2已逾期 3已联系）")
    public Result<BasePage<Map<String, Object>>> todayBusiness(@RequestBody CrmBackLogBO crmBackLogBO) {
        BasePage<Map<String, Object>> basePage = crmBackLogService.todayBusiness(crmBackLogBO);
        return Result.ok(basePage);
    }

    @PostMapping("/followLeads")
    @ApiOperation("分配给我的线索 type(1待跟进 2已跟进)")
    public Result<BasePage<Map<String, Object>>> followLeads(@RequestBody CrmBackLogBO crmBackLogBO) {
        BasePage<Map<String, Object>> basePage = crmBackLogService.followLeads(crmBackLogBO);
        return Result.ok(basePage);
    }

    @PostMapping("/followCustomer")
    @ApiOperation("分配给我的客户 type(1待跟进 2已跟进)")
    public Result<BasePage<Map<String, Object>>> followCustomer(@RequestBody CrmBackLogBO crmBackLogBO) {
        BasePage<Map<String, Object>> basePage = crmBackLogService.followCustomer(crmBackLogBO);
        return Result.ok(basePage);
    }

    @PostMapping("/returnVisitRemind")
    @ApiOperation("待回访合同 type(1待审核 2已审核)")
    public Result<BasePage<Map<String, Object>>> returnVisitRemind(@RequestBody CrmBackLogBO crmBackLogBO) {
        BasePage<Map<String, Object>> basePage = crmBackLogService.returnVisitRemind(crmBackLogBO);
        return Result.ok(basePage);
    }

    @PostMapping("/setLeadsFollowup")
    @ApiOperation("标记线索为已跟进")
    public Result setLeadsFollowup(@RequestBody List<Integer> ids) {
        crmBackLogService.setLeadsFollowup(ids);
        return Result.ok();
    }

    @PostMapping("/setCustomerFollowup")
    @ApiOperation("标记客户为已跟进")
    public Result setCustomerFollowup(@RequestBody List<Integer> ids) {
        crmBackLogService.setCustomerFollowup(ids);
        return Result.ok();
    }

    @PostMapping("/checkContract")
    @ApiOperation("查询待审核合同 type(1待审核 2已审核)")
    public Result<BasePage<Map<String, Object>>> checkContract(@RequestBody CrmBackLogBO crmBackLogBO) {
        BasePage<Map<String, Object>> basePage = crmBackLogService.checkContract(crmBackLogBO);
        return Result.ok(basePage);
    }

    @PostMapping("/checkReceivables")
    @ApiOperation("查询待审核回款 type(1待审核 2已审核)")
    public Result<BasePage<Map<String, Object>>> checkReceivables(@RequestBody CrmBackLogBO crmBackLogBO) {
        BasePage<Map<String, Object>> basePage = crmBackLogService.checkReceivables(crmBackLogBO);
        return Result.ok(basePage);
    }

    @PostMapping("/checkInvoice")
    @ApiOperation("查询待审核发票 type(1待审核 2已审核)")
    public Result<BasePage<Map<String, Object>>> checkInvoice(@RequestBody CrmBackLogBO crmBackLogBO) {
        BasePage<Map<String, Object>> basePage = crmBackLogService.checkInvoice(crmBackLogBO);
        return Result.ok(basePage);
    }

    @PostMapping("/remindReceivables")
    @ApiOperation("查询待回款提醒 type(1待回款 2已回款 3已逾期)")
    public Result<BasePage<CrmReceivablesPlan>> remindReceivables(@RequestBody CrmBackLogBO crmBackLogBO) {
        BasePage<CrmReceivablesPlan> basePage = crmBackLogService.remindReceivables(crmBackLogBO);
        return Result.ok(basePage);
    }

    @PostMapping("/endContract")
    @ApiOperation("查询即将到期的合同 type(1即将到期 2已到期")
    public Result<BasePage<Map<String, Object>>> endContract(@RequestBody CrmBackLogBO crmBackLogBO) {
        BasePage<Map<String, Object>> basePage = crmBackLogService.endContract(crmBackLogBO);
        return Result.ok(basePage);
    }

    /**
     * modelID 1今日需联系客户 2分配给我的线索 3分配给我的客户 4待进入公海的客户
     * 5待审核合同 6待审核回款 7待回款提醒 8即将到期的合同 9待回访合同 10待审核发票 11 今日需联系线索 12 今日需联系商机
     */
    @PostMapping("/allDeal")
    @ApiOperation("（按模块）待办事项全部标为已处理")
    public Result allDeal(@RequestParam("model") Integer model) {
        crmBackLogService.allDeal(model);
        return Result.ok();
    }

    /**
     * 部分标为已处理
     */
    @PostMapping("/dealById")
    @ApiOperation("部分标为已处理")
    public Result dealById(@RequestBody JSONObject jsonObject) {
        Integer model = jsonObject.getInteger("model");
        List<JSONObject> jsonObjectList = jsonObject.getJSONArray("list").toJavaList(JSONObject.class);
        crmBackLogService.dealById(model, jsonObjectList);
        return Result.ok();
    }

    /**
     * @author liujiaming
     * 待进入客户池提醒
     */
    @PostMapping("/putInPoolRemind")
    @ApiOperation("查询待进入客户池提醒")
    public Result<BasePage<Map<String, Object>>> putInPoolRemind(@RequestBody CrmBackLogBO crmBackLogBO){
        BasePage<Map<String, Object>> page = crmBackLogService.putInPoolRemind(crmBackLogBO);
        return Result.ok(page);
    }

}

