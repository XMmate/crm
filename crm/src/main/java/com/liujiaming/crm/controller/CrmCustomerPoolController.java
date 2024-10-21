package com.liujiaming.crm.controller;


import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.liujiaming.core.common.*;
import com.liujiaming.core.common.log.BehaviorEnum;
import com.liujiaming.core.common.log.SysLog;
import com.liujiaming.core.common.log.SysLogHandler;
import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.entity.PageEntity;
import com.liujiaming.core.exception.CrmException;
import com.liujiaming.core.utils.ExcelParseUtil;
import com.liujiaming.core.utils.UserUtil;
import com.liujiaming.crm.common.log.CrmCustomerPoolLog;
import com.liujiaming.crm.constant.CrmCodeEnum;
import com.liujiaming.crm.constant.CrmTypeEnum;
import com.liujiaming.crm.entity.BO.CrmCustomerPoolBO;
import com.liujiaming.crm.entity.BO.CrmSearchBO;
import com.liujiaming.crm.entity.BO.UploadExcelBO;
import com.liujiaming.crm.entity.PO.CrmCustomerPool;
import com.liujiaming.crm.entity.PO.CrmCustomerPoolFieldSort;
import com.liujiaming.crm.entity.VO.CrmCustomerPoolVO;
import com.liujiaming.crm.entity.VO.CrmModelFiledVO;
import com.liujiaming.crm.service.CrmUploadExcelService;
import com.liujiaming.crm.service.ICrmCustomerPoolService;
import com.liujiaming.crm.service.ICrmCustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 公海表 前端控制器
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-29
 */
@RestController
@RequestMapping("/crmCustomerPool")
@Api(tags = "公海控制器")
@Slf4j
@SysLog(logClass = CrmCustomerPoolLog.class)
public class CrmCustomerPoolController {

    @Autowired
    private ICrmCustomerPoolService crmCustomerPoolService;

    @Autowired
    private ICrmCustomerService customerService;

    @Autowired
    private CrmUploadExcelService uploadExcelService;

    @ApiOperation("查看公海列表页")
    @PostMapping("/queryPageList")
    public Result<BasePage<Map<String, Object>>> queryPageList(@RequestBody CrmSearchBO crmSearchBO) {
        BasePage<Map<String, Object>> basePage = crmCustomerPoolService.queryPageList(crmSearchBO,false);
        return Result.ok(basePage);
    }

    /**
     * 企业后台-客户管理-客户公海规则设置
     * @param entity
     * @return
     */
    @ApiOperation("查询公海列表配置")
    @PostMapping("/queryPoolSettingList")
    public Result<BasePage<CrmCustomerPoolVO>> queryPoolSettingList(@RequestBody PageEntity entity) {
        BasePage<CrmCustomerPoolVO> poolVOBasePage = crmCustomerPoolService.queryPoolSettingList(entity);
        return Result.ok(poolVOBasePage);
    }

    @ApiOperation("公海选择列表")
    @PostMapping("/queryPoolNameList")
    public Result<List<CrmCustomerPool>> queryPoolNameList() {
        List<CrmCustomerPool> crmCustomerPools = crmCustomerPoolService.queryPoolNameList();
        return Result.ok(crmCustomerPools);
    }

    @ApiOperation("修改公海状态")
    @PostMapping("/changeStatus")
    @SysLogHandler(applicationName = "admin",subModel = SubModelType.ADMIN_CUSTOMER_MANAGEMENT,behavior = BehaviorEnum.UPDATE)
    public Result changeStatus(@RequestParam("poolId") Integer poolId, @RequestParam("status") Integer status) {
        crmCustomerPoolService.changeStatus(poolId, status);
        return Result.ok();
    }



    @ApiOperation("根据ID查询公海信息")
    @PostMapping("/queryPoolById")
    public Result<CrmCustomerPoolVO> queryPoolById(@RequestParam("poolId") Integer poolId) {
        CrmCustomerPoolVO customerPoolVO = crmCustomerPoolService.queryPoolById(poolId);
        return Result.ok(customerPoolVO);
    }

    @ApiOperation("获取公海默认字段")
    @PostMapping("/queryPoolField")
    public Result<List<CrmModelFiledVO>> queryPoolField() {
        List<CrmModelFiledVO> filedVOS = crmCustomerPoolService.queryPoolField();
        return Result.ok(filedVOS);
    }

    @PostMapping("/downloadExcel")
    @ApiOperation("下载导入模板")
    public void downloadExcel(HttpServletResponse response) throws IOException {
        customerService.downloadExcel(true,response);
    }

    @PostMapping("/uploadExcel")
    @ApiOperation("导入客户")
    public Result<Long> uploadExcel(@RequestParam("file") MultipartFile file, @RequestParam("repeatHandling") Integer repeatHandling, @RequestParam("poolId") Integer poolId) {
       Boolean isAdmin = crmCustomerPoolService.queryAuthListByPoolId(poolId);
        if (!isAdmin) {
            throw new CrmException(CrmCodeEnum.CRM_CUSTOMER_POOL_NOT_IS_ADMIN);
        }
        UploadExcelBO uploadExcelBO = new UploadExcelBO();
        uploadExcelBO.setUserInfo(UserUtil.getUser());
        uploadExcelBO.setCrmTypeEnum(CrmTypeEnum.CUSTOMER);
        uploadExcelBO.setPoolId(poolId);
        uploadExcelBO.setRepeatHandling(repeatHandling);
        Long messageId = uploadExcelService.uploadExcel(file, uploadExcelBO);
        return R.ok(messageId);
    }


    @ApiOperation("公海全部导出")
    @PostMapping("/allExportExcel")
    public void allExportExcel(@RequestBody CrmSearchBO search, HttpServletResponse response) throws IOException {
        Boolean isAdmin = crmCustomerPoolService.queryAuthListByPoolId(search.getPoolId());
        if (!isAdmin) {
            throw new CrmException(CrmCodeEnum.CRM_CUSTOMER_POOL_NOT_IS_ADMIN);
        }
        search.setPageType(0);
        BasePage<Map<String, Object>> basePage = crmCustomerPoolService.queryPageList(search,true);
        export(basePage.getList(), search.getPoolId(), response);
    }

    @ApiOperation("公海批量导出")
    @PostMapping("/batchExportExcel")
    public void batchExportExcel(@RequestBody JSONObject jsonObject, HttpServletResponse response) {
        Integer poolId = jsonObject.getInteger("poolId");
        Boolean isAdmin = crmCustomerPoolService.queryAuthListByPoolId(poolId);
        if (!isAdmin) {
            throw new CrmException(CrmCodeEnum.CRM_CUSTOMER_POOL_NOT_IS_ADMIN);
        }
        String ids = jsonObject.getString("ids");
        CrmSearchBO search = new CrmSearchBO();
        search.setPoolId(poolId);
        search.setPageType(0);
        search.setLabel(CrmTypeEnum.CUSTOMER.getType());
        CrmSearchBO.Search entity = new CrmSearchBO.Search();
        entity.setFormType(FieldEnum.TEXT.getFormType());
        entity.setSearchEnum(CrmSearchBO.FieldSearchEnum.ID);
        entity.setValues(StrUtil.splitTrim(ids, Const.SEPARATOR));
        search.getSearchList().add(entity);
        search.setPageType(0);
        BasePage<Map<String, Object>> basePage = crmCustomerPoolService.queryPageList(search,false);
        export(basePage.getList(), search.getPoolId(), response);
    }


    private void export(List<Map<String, Object>> recordList, Integer poolId, HttpServletResponse response) {
        List<CrmCustomerPoolFieldSort> headList = crmCustomerPoolService.queryPoolListHead(poolId);
        ExcelParseUtil.exportExcel(recordList, new ExcelParseUtil.ExcelParseService() {
            @Override
            public void castData(Map<String, Object> record, Map<String, Integer> headMap) {
                record.put("dealStatus", Objects.equals(1, record.get("dealStatus")) ? "已成交" : "未成交");
            }
            @Override
            public String getExcelName() {
                return "公海客户";
            }
        }, headList);
    }

    @ApiOperation("获取客户级别选项")
    @PostMapping("/queryCustomerLevel")
    public Result<List<String>> queryCustomerLevel() {
        List<String> strings = crmCustomerPoolService.queryCustomerLevel();
        return Result.ok(strings);
    }

    @ApiOperation("设置公海规则")
    @PostMapping("/setCustomerPool")
    @SysLogHandler(applicationName = "admin",subModel = SubModelType.ADMIN_CUSTOMER_MANAGEMENT,behavior = BehaviorEnum.SAVE,object = "#jsonObject[poolName]",detail = "'添加或修改了公海规则:'+#jsonObject[poolName]")
    public Result setCustomerPool(@RequestBody JSONObject jsonObject) {
        crmCustomerPoolService.setCustomerPool(jsonObject);
        return Result.ok();
    }

    @ApiOperation("查询公海字段配置")
    @PostMapping("/queryPoolFieldConfig")
    public Result<JSONObject> queryPoolFieldConfig(@RequestParam("poolId") Integer poolId) {
        JSONObject object = crmCustomerPoolService.queryPoolFieldConfig(poolId);
        return Result.ok(object);
    }

    @ApiOperation("公海字段配置")
    @PostMapping("/poolFieldConfig")
    public Result poolFieldConfig(@RequestBody JSONObject jsonObject) {
        crmCustomerPoolService.poolFieldConfig(jsonObject);
        return Result.ok();
    }

    @ApiOperation("删除公海规则")
    @PostMapping("/deleteCustomerPool")
    @SysLogHandler(applicationName = "admin",subModel = SubModelType.ADMIN_CUSTOMER_MANAGEMENT,behavior = BehaviorEnum.DELETE)
    public Result deleteCustomerPool(@RequestParam("poolId") Integer poolId) {
        crmCustomerPoolService.deleteCustomerPool(poolId);
        return Result.ok();
    }

    @ApiOperation("查询前台公海列表")
    @PostMapping("/queryPoolNameListByAuth")
    public Result queryPoolNameListByAuth() {
        List<CrmCustomerPool> crmCustomerPools = crmCustomerPoolService.queryPoolNameListByAuth();
        return Result.ok(crmCustomerPools);
    }

    @ApiOperation("查询前台公海字段")
    @PostMapping("/queryPoolListHead")
    public Result<List<CrmCustomerPoolFieldSort>> queryPoolListHead(Integer poolId) {
        if (poolId == null) {
            List<CrmCustomerPool> crmCustomerPools = crmCustomerPoolService.queryPoolNameListByAuth();
            if (crmCustomerPools.size() == 0) {
                throw new CrmException(CrmCodeEnum.CRM_CUSTOMER_POOL_NOT_EXIST_ERROR);
            }
            poolId = crmCustomerPools.get(0).getPoolId();
        }
        List<CrmCustomerPoolFieldSort> crmCustomerPoolFieldSorts = crmCustomerPoolService.queryPoolListHead(poolId);
        return Result.ok(crmCustomerPoolFieldSorts);
    }

    @PostMapping("/transfer")
    @ApiOperation("公海客户的转移")
    @SysLogHandler(applicationName = "admin",subModel = SubModelType.ADMIN_CUSTOMER_MANAGEMENT,behavior = BehaviorEnum.UPDATE)
    public Result transfer(@RequestParam("prePoolId") Integer prePoolId, @RequestParam("postPoolId") Integer postPoolId) {
        crmCustomerPoolService.transfer(prePoolId, postPoolId);
        return R.ok();
    }

    @PostMapping("/deleteByIds")
    @ApiOperation("根据ID删除数据")
    public Result deleteByIds(@RequestBody CrmCustomerPoolBO poolBO) {
        crmCustomerPoolService.deleteByIds(poolBO.getIds(), poolBO.getPoolId());
        return R.ok();
    }

    @ApiOperation("查询前台公海权限")
    @PostMapping("/queryAuthByPoolId")
    public Result<JSONObject> queryAuthByPoolId(@RequestParam("poolId") Integer poolId) {
        JSONObject object = crmCustomerPoolService.queryAuthByPoolId(poolId);
        return Result.ok(object);
    }
}

