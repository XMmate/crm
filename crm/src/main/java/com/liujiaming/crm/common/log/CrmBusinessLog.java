package com.liujiaming.crm.common.log;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import com.liujiaming.core.common.FieldEnum;
import com.liujiaming.core.common.log.BehaviorEnum;
import com.liujiaming.core.common.log.Content;
import com.liujiaming.core.feign.admin.entity.SimpleDept;
import com.liujiaming.core.feign.admin.entity.SimpleUser;
import com.liujiaming.core.feign.admin.service.AdminFileService;
import com.liujiaming.core.feign.admin.service.AdminService;
import com.liujiaming.core.servlet.ApplicationContextHolder;
import com.liujiaming.core.servlet.upload.FileEntity;
import com.liujiaming.core.utils.TagUtil;
import com.liujiaming.core.utils.UserCacheUtil;
import com.liujiaming.core.utils.UserUtil;
import com.liujiaming.crm.constant.CrmEnum;
import com.liujiaming.crm.entity.BO.CrmBusinessSaveBO;
import com.liujiaming.crm.entity.BO.CrmChangeOwnerUserBO;
import com.liujiaming.crm.entity.BO.CrmMemberSaveBO;
import com.liujiaming.crm.entity.BO.CrmUpdateInformationBO;
import com.liujiaming.crm.entity.PO.CrmBusiness;
import com.liujiaming.crm.entity.PO.CrmBusinessData;
import com.liujiaming.crm.service.ICrmBusinessDataService;
import com.liujiaming.crm.service.ICrmBusinessService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CrmBusinessLog {

    private ICrmBusinessService crmBusinessService = ApplicationContextHolder.getBean(ICrmBusinessService.class);

    private SysLogUtil sysLogUtil = ApplicationContextHolder.getBean(SysLogUtil.class);

    private AdminService adminService = ApplicationContextHolder.getBean(AdminService.class);

    private AdminFileService adminFileService = ApplicationContextHolder.getBean(AdminFileService.class);

    private ICrmBusinessDataService crmBusinessDataService = ApplicationContextHolder.getBean(ICrmBusinessDataService.class);

    public Content update(CrmBusinessSaveBO crmModel) {
        CrmBusiness crmBusiness = BeanUtil.copyProperties(crmModel.getEntity(), CrmBusiness.class);
        sysLogUtil.updateRecord(crmModel.getField(), Dict.create().set("batchId", crmBusiness.getBatchId()).set("dataTableName", "wk_crm_business_data"));
        return sysLogUtil.updateRecord(BeanUtil.beanToMap(crmBusinessService.getById(crmBusiness.getBusinessId())), BeanUtil.beanToMap(crmBusiness), CrmEnum.BUSINESS,crmBusiness.getBusinessName());
    }

    public List<Content> deleteByIds(List<Integer> ids) {
        List<Content> contentList = new ArrayList<>();
        for (Integer id : ids) {
            String name = crmBusinessService.getBusinessName(id);
            if (name != null) {
                contentList.add(sysLogUtil.addDeleteActionRecord(CrmEnum.CONTACTS, name));
            }
        }
        return contentList;
    }

    public List<Content> changeOwnerUser(CrmChangeOwnerUserBO crmChangeOwnerUserBO) {
        return crmChangeOwnerUserBO.getIds().stream().map(id -> {
            String name = crmBusinessService.getBusinessName(id);
            return sysLogUtil.addConversionRecord(CrmEnum.BUSINESS, crmChangeOwnerUserBO.getOwnerUserId(), name);
        }).collect(Collectors.toList());
    }

    public List<Content> addMembers(CrmMemberSaveBO crmMemberSaveBO) {
        List<Content> contentList = new ArrayList<>();
        for (Integer id : crmMemberSaveBO.getIds()) {
            String name = crmBusinessService.getBusinessName(id);
            for (Long memberId : crmMemberSaveBO.getMemberIds()) {
                contentList.add(sysLogUtil.addMemberActionRecord(CrmEnum.BUSINESS, id, memberId, name));
            }
        }
        return contentList;
    }

    public List<Content> updateMembers(CrmMemberSaveBO crmMemberSaveBO) {
        List<Content> contentList = new ArrayList<>();
        for (Integer id : crmMemberSaveBO.getIds()) {
            String name = crmBusinessService.getBusinessName(id);
            for (Long memberId : crmMemberSaveBO.getMemberIds()) {
                contentList.add(sysLogUtil.addMemberActionRecord(CrmEnum.BUSINESS, id, memberId, name));
            }
        }
        return contentList;
    }

    public List<Content> deleteMembers(CrmMemberSaveBO crmMemberSaveBO) {
        List<Content> contentList = new ArrayList<>();
        for (Integer id : crmMemberSaveBO.getIds()) {
            String businessName = crmBusinessService.getBusinessName(id);
            for (Long memberId : crmMemberSaveBO.getMemberIds()) {
                if (!memberId.equals(UserUtil.getUserId())) {
                    contentList.add(sysLogUtil.addDeleteMemberActionRecord(CrmEnum.BUSINESS, memberId, false, businessName));
                } else {
                    contentList.add(sysLogUtil.addDeleteMemberActionRecord(CrmEnum.BUSINESS, memberId, true, businessName));
                }
            }
        }
        return contentList;
    }

    public Content exitTeam(Integer businessId) {
        String businessName = crmBusinessService.getBusinessName(businessId);
        return sysLogUtil.addDeleteMemberActionRecord(CrmEnum.BUSINESS, UserUtil.getUserId(), true, businessName);
    }

    public List<Content> updateInformation(CrmUpdateInformationBO updateInformationBO) {
        List<Content> contentList = new ArrayList<>();
        String batchId = updateInformationBO.getBatchId();
        updateInformationBO.getList().forEach(record -> {
            CrmBusiness oldBusiness = crmBusinessService.getById(updateInformationBO.getId());
            Map<String, Object> oldBusinessMap = BeanUtil.beanToMap(oldBusiness);
            if (record.getInteger("fieldType") == 1) {
                Map<String, Object> crmBusinessMap = new HashMap<>(oldBusinessMap);
                crmBusinessMap.put(record.getString("fieldName"), record.get("value"));
                CrmBusiness crmBusiness = BeanUtil.mapToBean(crmBusinessMap, CrmBusiness.class, true);
                contentList.add(sysLogUtil.updateRecord(oldBusinessMap, crmBusinessMap, CrmEnum.BUSINESS, crmBusiness.getBusinessName()));
            } else if (record.getInteger("fieldType") == 0 || record.getInteger("fieldType") == 2) {
                String formType = record.getString("formType");
                if(formType == null){
                    return;
                }
                String oldFieldValue = crmBusinessDataService.lambdaQuery().select(CrmBusinessData::getValue).eq(CrmBusinessData::getFieldId, record.getInteger("fieldId"))
                        .eq(CrmBusinessData::getBatchId, batchId).one().getValue();
                String newValue = record.getString("value");
                if (formType.equals(FieldEnum.USER.getFormType()) || formType.equals(FieldEnum.SINGLE_USER.getFormType())) {
                    oldFieldValue = UserCacheUtil.getSimpleUsers(TagUtil.toLongSet(oldFieldValue)).stream().map(SimpleUser::getRealname).collect(Collectors.joining(","));
                    newValue = UserCacheUtil.getSimpleUsers(TagUtil.toLongSet(record.getString("value"))).stream().map(SimpleUser::getRealname).collect(Collectors.joining(","));
                } else if (formType.equals(FieldEnum.STRUCTURE.getFormType())) {
                    oldFieldValue = adminService.queryDeptByIds(TagUtil.toSet(oldFieldValue)).getData().stream().map(SimpleDept::getName).collect(Collectors.joining(","));
                    newValue = adminService.queryDeptByIds(TagUtil.toSet(record.getString("value"))).getData().stream().map(SimpleDept::getName).collect(Collectors.joining(","));
                } else if (formType.equals(FieldEnum.FILE.getFormType())) {
                    oldFieldValue = adminFileService.queryFileList(oldFieldValue).getData().stream().map(FileEntity::getName).collect(Collectors.joining(","));
                    newValue = adminFileService.queryFileList(record.getString("value")).getData().stream().map(FileEntity::getName).collect(Collectors.joining(","));
                }
                String oldValue = StrUtil.isEmpty(oldFieldValue) ? "空" : oldFieldValue;
                String detail = "将" + record.getString("name") + " 由" + oldValue + "修改为" + newValue + "。";
                contentList.add(new Content(oldBusiness.getBusinessName(), detail, BehaviorEnum.UPDATE));
            }
        });
        return contentList;
    }

}
