package com.liujiaming.crm.controller;


import com.alibaba.fastjson.JSONObject;
import com.liujiaming.core.common.ApiExplain;
import com.liujiaming.core.common.R;
import com.liujiaming.core.common.Result;
import com.liujiaming.core.common.SubModelType;
import com.liujiaming.core.common.log.BehaviorEnum;
import com.liujiaming.core.common.log.SysLog;
import com.liujiaming.core.common.log.SysLogHandler;
import com.liujiaming.core.feign.crm.entity.ExamineField;
import com.liujiaming.crm.common.ElasticUtil;
import com.liujiaming.crm.common.log.CrmFieldLog;
import com.liujiaming.crm.entity.BO.*;
import com.liujiaming.crm.entity.BO.*;
import com.liujiaming.crm.entity.PO.CrmField;
import com.liujiaming.crm.entity.PO.CrmRoleField;
import com.liujiaming.crm.entity.VO.CrmFieldSortVO;
import com.liujiaming.crm.service.ICrmFieldService;
import com.liujiaming.crm.service.ICrmRoleFieldService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * <p>
 * 自定义字段表 前端控制器
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-19
 */
@RestController
@RequestMapping("/crmField")
@Api(tags = "系统配置接口")
@SysLog(logClass = CrmFieldLog.class)
public class CrmFieldController {

    @Autowired
    private ICrmFieldService crmFieldService;

    @Autowired
    private ElasticsearchRestTemplate restTemplate;

    @Autowired
    private ICrmRoleFieldService crmRoleFieldService;

    @ApiOperation("查询自定义字段")
    @RequestMapping(value = "/queryFields", method = RequestMethod.POST)
    public Result<List<CrmFieldsBO>> queryFields() {
        List<CrmFieldsBO> fieldsBOList = crmFieldService.queryFields();
        return Result.ok(fieldsBOList);
    }

    @ApiOperation("保存自定义字段")
    @RequestMapping(value = "/saveField", method = RequestMethod.POST)
    @SysLogHandler(applicationName = "admin",subModel = SubModelType.ADMIN_CUSTOMER_MANAGEMENT,behavior = BehaviorEnum.SAVE)
    public Result saveField(@RequestBody CrmFieldBO crmFieldBO) {
        crmFieldService.saveField(crmFieldBO);
        return Result.ok();
    }

    @ApiOperation("查询字段列表")
    @RequestMapping(value = "/list/{label}", method = RequestMethod.POST)
    public Result<List<List<CrmField>>> list(@PathVariable("label") Integer label) {
        List<List<CrmField>> crmFieldList = crmFieldService.queryFormPositionFieldList(label, true);
        return Result.ok(crmFieldList);
    }

    @ApiOperation("查询模块字段列表")
    @PostMapping(value = "/queryListHead/{label}")
    public Result<List<CrmFieldSortVO>> queryListHead(@NotNull @PathVariable("label") Integer label) {
        List<CrmFieldSortVO> voList = crmFieldService.queryListHead(label);
        return Result.ok(voList);
    }

    @ApiOperation("编辑字段宽度")
    @PostMapping(value = "/setFieldStyle")
    public Result setFieldStyle(@RequestBody CrmFieldStyleBO fieldStyle) {
        crmFieldService.setFieldStyle(fieldStyle);
        return Result.ok();
    }

    @ApiOperation("设置公海字段样式")
    @PostMapping(value = "/setPoolFieldStyle")
    public Result setPoolFieldStyle(@RequestBody CrmFieldStyleBO fieldStyle) {
        crmFieldService.setPoolFieldStyle(fieldStyle);
        return Result.ok();
    }

    @ApiOperation("编辑字段宽度")
    @PostMapping(value = "/setFieldConfig")
    public Result setFieldConfig(@RequestBody @Valid CrmFieldSortBO fieldSort) {
        crmFieldService.setFieldConfig(fieldSort);
        return Result.ok();
    }

    @ApiOperation("编辑字段宽度")
    @PostMapping(value = "/queryFieldConfig")
    public Result<JSONObject> queryFieldConfig(@RequestParam("label") Integer label) {
        JSONObject object = crmFieldService.queryFieldConfig(label);
        return Result.ok(object);
    }

    @ApiOperation("验证字段是否存在")
    @PostMapping(value = "/verify")
    public Result<CrmFieldVerifyBO> verify(@RequestBody CrmFieldVerifyBO fieldVerifyBO) {
        CrmFieldVerifyBO verify = crmFieldService.verify(fieldVerifyBO);
        return Result.ok(verify);
    }

    @PostMapping(value = "/queryRoleField")
    @ApiOperation("查询字段授权配置")
    public Result<List<CrmRoleField>> queryRoleField(@RequestParam("roleId") Integer roleId, @RequestParam("label") Integer label) {
        List<CrmRoleField> crmRoleFields = crmRoleFieldService.queryRoleField(roleId, label);
        return R.ok(crmRoleFields);
    }

    @PostMapping(value = "/saveRoleField")
    @ApiOperation("保存字段授权配置")
    public Result saveRoleField(@RequestBody List<CrmRoleField> fields) {
        crmRoleFieldService.saveRoleField(fields);
        return R.ok();
    }


    @PostMapping(value = "/batchUpdateEsData")
    @ApiExplain("根据类型跟新es冗余数据")
    public Result batchUpdateEsData(@RequestParam("id") String id, @RequestParam("name") String name) {
        ElasticUtil.batchUpdateEsData(restTemplate.getClient(), "user", id, name);
        return R.ok();
    }


    /**
     * 给审批模块远程调用
     * @param label
     * @return
     */
    @PostMapping("/queryExamineField")
    @ApiExplain("查询审批模块可设置字段")
    public Result<List<ExamineField>> queryExamineField(@RequestParam("label") Integer label) {
        List<ExamineField> fieldList = crmFieldService.queryExamineField(label);
        return R.ok(fieldList);
    }

}

