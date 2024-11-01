package com.liujiaming.crm.controller;


import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.liujiaming.core.common.*;
import com.liujiaming.core.common.log.BehaviorEnum;
import com.liujiaming.core.common.log.SysLog;
import com.liujiaming.core.common.log.SysLogHandler;
import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.exception.CrmException;
import com.liujiaming.core.feign.crm.entity.SimpleCrmEntity;
import com.liujiaming.core.servlet.upload.FileEntity;
import com.liujiaming.crm.common.AuthUtil;
import com.liujiaming.crm.common.CrmModel;
import com.liujiaming.crm.common.log.CrmBusinessLog;
import com.liujiaming.crm.constant.CrmAuthEnum;
import com.liujiaming.crm.constant.CrmCodeEnum;
import com.liujiaming.crm.constant.CrmTypeEnum;
import com.liujiaming.crm.entity.BO.*;
import com.liujiaming.crm.entity.PO.CrmBusiness;
import com.liujiaming.crm.entity.PO.CrmBusinessType;
import com.liujiaming.crm.entity.PO.CrmContacts;
import com.liujiaming.crm.entity.VO.CrmBusinessStatusVO;
import com.liujiaming.crm.entity.VO.CrmInfoNumVO;
import com.liujiaming.crm.entity.VO.CrmMembersSelectVO;
import com.liujiaming.crm.entity.VO.CrmModelFiledVO;
import com.liujiaming.crm.service.ICrmBusinessService;
import com.liujiaming.crm.service.ICrmBusinessTypeService;
import com.liujiaming.crm.service.ICrmTeamMembersService;
import com.liujiaming.crm.entity.BO.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 商机表 前端控制器
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-27
 */
@RestController
@RequestMapping("/crmBusiness")
@Api(tags = "商机模块接口")
@SysLog(subModel = SubModelType.CRM_BUSINESS,logClass = CrmBusinessLog.class)
public class CrmBusinessController {

    @Autowired
    private ICrmBusinessService crmBusinessService;

    @Autowired
    private ICrmBusinessTypeService crmBusinessTypeService;

    @Autowired
    private ICrmTeamMembersService teamMembersService;


    /**
     * 查es
     * @param crmSearchBO
     * @return
     */
    @PostMapping("/queryPageList")
    @ApiOperation("查询商机列表页数据")
    public Result<BasePage<Map<String, Object>>> queryPageList(@RequestBody CrmSearchBO crmSearchBO) {
        BasePage<Map<String, Object>> mapBasePage = crmBusinessService.queryPageList(crmSearchBO);
        return R.ok(mapBasePage);
    }

    @PostMapping("/add")
    @ApiOperation("新建商机")
    @SysLogHandler(behavior = BehaviorEnum.SAVE,object = "#crmModel.entity[businessName]",detail = "'新增了商机:' + #crmModel.entity[businessName]")
    public Result add(@RequestBody CrmBusinessSaveBO crmModel) {
        crmBusinessService.addOrUpdate(crmModel);
        return R.ok();
    }

    @PostMapping("/information/{id}")
    @ApiOperation("根据ID查询商机详情页信息")
    public Result<List<CrmModelFiledVO>> information(@PathVariable("id") @ApiParam(name = "id", value = "id") Integer id) {
        List<CrmModelFiledVO> information = crmBusinessService.information(id);
        return R.ok(information);
    }

    @PostMapping("/update")
    @ApiOperation("修改商机数据")
    @SysLogHandler(behavior = BehaviorEnum.UPDATE)
    public Result update(@RequestBody CrmBusinessSaveBO crmModel) {
        if (AuthUtil.isRwAuth((Integer) crmModel.getEntity().get("businessId"), CrmTypeEnum.BUSINESS,CrmAuthEnum.EDIT)) {
            throw new CrmException(SystemCodeEnum.SYSTEM_NO_AUTH);
        }
        crmBusinessService.addOrUpdate(crmModel);
        return R.ok();
    }

    @PostMapping("/queryById/{businessId}")
    @ApiOperation("根据ID查询商机")
    public Result<CrmModel> queryById(@PathVariable("businessId") @ApiParam(name = "id", value = "id") Integer businessId) {
        Integer number = crmBusinessService.lambdaQuery().eq(CrmBusiness::getBusinessId, businessId).count();
        if (number == 0) {
            throw new CrmException(CrmCodeEnum.CRM_DATA_DELETED, "商机");
        }
        CrmModel model = crmBusinessService.queryById(businessId);
        return R.ok(model);
    }

    @PostMapping("/queryProduct")
    @ApiOperation("查询产品")
    public Result<JSONObject> queryProduct(@RequestBody CrmBusinessQueryRelationBO businessQueryProductBO) {
        boolean auth = AuthUtil.isCrmAuth(CrmTypeEnum.BUSINESS, businessQueryProductBO.getBusinessId(),CrmAuthEnum.READ);
        if (auth) {
            throw new CrmException(SystemCodeEnum.SYSTEM_NO_AUTH);
        }
        JSONObject jsonObject = crmBusinessService.queryProduct(businessQueryProductBO);
        return R.ok(jsonObject);
    }

    /**
     * 查询合同
     * @param businessQueryRelationBO
     * @return
     */
    @PostMapping("/queryContract")
    public Result<BasePage<JSONObject>> queryContract(@RequestBody CrmBusinessQueryRelationBO businessQueryRelationBO) {
        boolean auth = AuthUtil.isCrmAuth(CrmTypeEnum.BUSINESS, businessQueryRelationBO.getBusinessId(), CrmAuthEnum.READ);
        if (auth) {
            throw new CrmException(SystemCodeEnum.SYSTEM_NO_AUTH);
        }
        BasePage<JSONObject> page = crmBusinessService.queryContract(businessQueryRelationBO);
        return R.ok(page);
    }

    @PostMapping("/queryContacts")
    @ApiOperation("查询商机下联系人")
    public Result<BasePage<CrmContacts>> queryContacts(@RequestBody CrmContactsPageBO pageEntity) {
        BasePage<CrmContacts> contactsBasePage = crmBusinessService.queryContacts(pageEntity);
        return R.ok(contactsBasePage);
    }

    @PostMapping("/relateContacts")
    @ApiOperation("关联联系人")
    public Result relateContacts(@RequestBody CrmRelevanceBusinessBO relevanceBusinessBO) {
        crmBusinessService.relateContacts(relevanceBusinessBO);
        return R.ok();
    }

    @PostMapping("/unrelateContacts")
    @ApiOperation("解除关联联系人")
    public Result unrelateContacts(@RequestBody CrmRelevanceBusinessBO relevanceBusinessBO) {
        crmBusinessService.unrelateContacts(relevanceBusinessBO);
        return R.ok();
    }

    @PostMapping("/deleteByIds")
    @ApiOperation("根据ID列表删除数据")
    @SysLogHandler(behavior = BehaviorEnum.DELETE)
    public Result deleteByIds(@ApiParam(name = "ids", value = "id列表") @RequestBody List<Integer> ids) {
        crmBusinessService.deleteByIds(ids);
        return R.ok();
    }

    @PostMapping("/changeOwnerUser")
    @ApiOperation("修改商机负责人")
    @SysLogHandler(behavior = BehaviorEnum.CHANGE_OWNER)
    public Result changeOwnerUser(@RequestBody CrmChangeOwnerUserBO crmChangeOwnerUserBO) {
        crmBusinessService.changeOwnerUser(crmChangeOwnerUserBO);
        return R.ok();
    }

    @PostMapping("/field")
    @ApiOperation("查询新增所需字段")
    public Result<List> queryBusinessField(@RequestParam(value = "type",required = false) String type) {
        if (StrUtil.isNotEmpty(type)) {
            return R.ok(crmBusinessService.queryField(null));
        }
        return R.ok(crmBusinessService.queryFormPositionField(null));
    }

    @PostMapping("/field/{id}")
    @ApiOperation("查询修改数据所需信息")
    public Result<List> queryField(@PathVariable("id") @ApiParam(name = "id", value = "id") Integer id,
                                   @RequestParam(value = "type",required = false) String type) {
        if (StrUtil.isNotEmpty(type)) {
            return R.ok(crmBusinessService.queryField(id));
        }
        return R.ok(crmBusinessService.queryFormPositionField(id));
    }

    @PostMapping("/getMembers/{businessId}")
    @ApiOperation("获取团队成员")
    public Result<List<CrmMembersSelectVO>> getMembers(@PathVariable("businessId") @ApiParam("商机ID") Integer businessId) {
        CrmTypeEnum crmTypeEnum = CrmTypeEnum.BUSINESS;
        CrmBusiness business = crmBusinessService.getById(businessId);
        if (business == null) {
            throw new CrmException(CrmCodeEnum.CRM_DATA_DELETED, crmTypeEnum.getRemarks());
        }
        List<CrmMembersSelectVO> members = teamMembersService.getMembers(crmTypeEnum,businessId,business.getOwnerUserId());
        return R.ok(members);
    }

    @PostMapping("/addMembers")
    @ApiOperation("新增团队成员")
    @SysLogHandler(behavior = BehaviorEnum.ADD_MEMBER)
    public Result addMembers(@RequestBody CrmMemberSaveBO crmMemberSaveBO) {
        teamMembersService.addMember(CrmTypeEnum.BUSINESS,crmMemberSaveBO);
        return R.ok();
    }

    @PostMapping("/updateMembers")
    @ApiOperation("新增团队成员")
    @SysLogHandler(behavior = BehaviorEnum.ADD_MEMBER)
    public Result updateMembers(@RequestBody CrmMemberSaveBO crmMemberSaveBO) {
        teamMembersService.addMember(CrmTypeEnum.BUSINESS,crmMemberSaveBO);
        return R.ok();
    }

    @PostMapping("/deleteMembers")
    @ApiOperation("删除团队成员")
    @SysLogHandler
    public Result deleteMembers(@RequestBody CrmMemberSaveBO crmMemberSaveBO) {
        teamMembersService.deleteMember(CrmTypeEnum.BUSINESS,crmMemberSaveBO);
        return R.ok();
    }

    @PostMapping("/exitTeam/{businessId}")
    @ApiOperation("删除团队成员")
    @SysLogHandler
    public Result exitTeam(@PathVariable("businessId") @ApiParam("商机ID") Integer businessId) {
        teamMembersService.exitTeam(CrmTypeEnum.BUSINESS,businessId);
        return R.ok();
    }

    @PostMapping("/queryBusinessStatus/{businessId}")
    @ApiOperation("查询商机状态")
    public Result<CrmBusinessStatusVO> queryBusinessStatus(@PathVariable("businessId") @ApiParam("商机ID") Integer businessId) {
        boolean auth = AuthUtil.isCrmAuth(CrmTypeEnum.BUSINESS, businessId,CrmAuthEnum.READ);
        if (auth) {
            throw new CrmException(SystemCodeEnum.SYSTEM_NO_AUTH);
        }
        CrmBusinessStatusVO statusVO = crmBusinessTypeService.queryBusinessStatus(businessId);
        return R.ok(statusVO);
    }

    @PostMapping("/boostBusinessStatus")
    @ApiOperation("商机状态组推进")
    public Result boostBusinessStatus(@RequestBody CrmBusinessStatusBO businessStatus) {
        boolean auth = AuthUtil.isRwAuth(businessStatus.getBusinessId(),CrmTypeEnum.BUSINESS,CrmAuthEnum.EDIT);
        if (auth) {
            throw new CrmException(SystemCodeEnum.SYSTEM_NO_AUTH);
        }
        crmBusinessTypeService.boostBusinessStatus(businessStatus);
        return R.ok();
    }

    @PostMapping("/queryBusinessStatusOptions")
    @ApiOperation("查询商机状态组及商机状态")
    public Result<List<CrmBusinessType>> queryBusinessStatusOptions() {
        List<CrmBusinessType> businessTypeList = crmBusinessTypeService.queryBusinessStatusOptions();
        return R.ok(businessTypeList);
    }

    @PostMapping("/setContacts")
    @ApiOperation("设置首要联系人")
    public Result setContacts(@RequestBody CrmFirstContactsBO contacts) {
        crmBusinessService.setContacts(contacts);
        return R.ok();
    }

    @PostMapping("/batchExportExcel")
    @ApiOperation("选中导出")
    @SysLogHandler(behavior = BehaviorEnum.EXCEL_EXPORT,object = "选中导出",detail = "导出商机")
    public void batchExportExcel(@RequestBody @ApiParam(name = "ids", value = "id列表") List<Integer> ids, HttpServletResponse response) {
        CrmSearchBO search = new CrmSearchBO();
        search.setPageType(0);
        search.setLabel(CrmTypeEnum.BUSINESS.getType());
        CrmSearchBO.Search entity = new CrmSearchBO.Search();
        entity.setFormType(FieldEnum.TEXT.getFormType());
        entity.setSearchEnum(CrmSearchBO.FieldSearchEnum.ID);
        entity.setValues(ids.stream().map(Object::toString).collect(Collectors.toList()));
        search.getSearchList().add(entity);
        search.setPageType(0);
        crmBusinessService.exportExcel(response, search);
    }

    @PostMapping("/allExportExcel")
    @ApiOperation("全部导出")
    @SysLogHandler(behavior = BehaviorEnum.EXCEL_EXPORT,object = "全部导出",detail = "导出商机")
    public void allExportExcel(@RequestBody CrmSearchBO search, HttpServletResponse response) {
        search.setPageType(0);
        crmBusinessService.exportExcel(response, search);
    }

    @PostMapping("/queryFileList")
    @ApiOperation("查询附件列表")
    public Result<List<FileEntity>> queryFileList(@RequestParam("id") @ApiParam(name = "id", value = "id") Integer id) {
        List<FileEntity> fileEntities = crmBusinessService.queryFileList(id);
        return R.ok(fileEntities);
    }

    @PostMapping("/num")
    @ApiOperation("详情页数量展示")
    public Result<CrmInfoNumVO> num(@RequestParam("id") @ApiParam(name = "id", value = "id") Integer id) {
        CrmInfoNumVO infoNumVO = crmBusinessService.num(id);
        return R.ok(infoNumVO);
    }

    @PostMapping("/star/{id}")
    @ApiOperation("商机标星")
    public Result star(@PathVariable("id") @ApiParam(name = "id", value = "id") Integer id) {
        crmBusinessService.star(id);
        return R.ok();
    }

    @PostMapping("/querySimpleEntity")
    @ApiExplain("查询简单的商机对象")
    public Result<List<SimpleCrmEntity>> querySimpleEntity(@RequestBody List<Integer> ids) {
        List<SimpleCrmEntity> crmEntities = crmBusinessService.querySimpleEntity(ids);
        return R.ok(crmEntities);
    }

    @PostMapping("/updateInformation")
    @ApiOperation("基本信息保存修改")
    @SysLogHandler(behavior = BehaviorEnum.UPDATE)
    public Result updateInformation(@RequestBody CrmUpdateInformationBO updateInformationBO) {
        boolean auth = AuthUtil.isRwAuth(updateInformationBO.getId(),CrmTypeEnum.BUSINESS,CrmAuthEnum.EDIT);
        if (auth) {
            throw new CrmException(SystemCodeEnum.SYSTEM_NO_AUTH);
        }
        crmBusinessService.updateInformation(updateInformationBO);
        return R.ok();
    }

}

