package com.liujiaming.crm.common.log;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.IdUtil;
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
import com.liujiaming.crm.constant.CrmTypeEnum;
import com.liujiaming.crm.entity.BO.CrmChangeOwnerUserBO;
import com.liujiaming.crm.entity.BO.CrmContractSaveBO;
import com.liujiaming.crm.entity.BO.CrmMemberSaveBO;
import com.liujiaming.crm.entity.BO.CrmUpdateInformationBO;
import com.liujiaming.crm.entity.PO.CrmContract;
import com.liujiaming.crm.entity.PO.CrmContractData;
import com.liujiaming.crm.service.ICrmContractDataService;
import com.liujiaming.crm.service.ICrmContractService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CrmContractLog {
    private SysLogUtil sysLogUtil = ApplicationContextHolder.getBean(SysLogUtil.class);

    private ICrmContractService crmContractService = ApplicationContextHolder.getBean(ICrmContractService.class);

    private AdminService adminService = ApplicationContextHolder.getBean(AdminService.class);

    private AdminFileService adminFileService = ApplicationContextHolder.getBean(AdminFileService.class);

    private ICrmContractDataService crmContractDataService = ApplicationContextHolder.getBean(ICrmContractDataService.class);


    public List<Content> deleteByIds(List<Integer> ids) {
        List<Content> contentList = new ArrayList<>();
        for (Integer id : ids) {
            String name = crmContractService.getContractName(id);
            if (name != null) {
                contentList.add(sysLogUtil.addDeleteActionRecord(CrmTypeEnum.CONTRACT, name));
            }
        }
        return contentList;
    }

    public List<Content> changeOwnerUser(CrmChangeOwnerUserBO crmChangeOwnerUserBO) {
        return crmChangeOwnerUserBO.getIds().stream().map(id -> {
            String name = crmContractService.getContractName(id);
            return sysLogUtil.addConversionRecord(CrmTypeEnum.CONTRACT, crmChangeOwnerUserBO.getOwnerUserId(), name);
        }).collect(Collectors.toList());
    }

    public Content update(CrmContractSaveBO crmModel) {
        CrmContract crmContract = BeanUtil.copyProperties(crmModel.getEntity(), CrmContract.class);
        String batchId = StrUtil.isNotEmpty(crmContract.getBatchId()) ? crmContract.getBatchId() : IdUtil.simpleUUID();
        sysLogUtil.updateRecord(crmModel.getField(), Dict.create().set("batchId", batchId).set("dataTableName", "wk_crm_contract_data"));
        crmContractDataService.saveData(crmModel.getField(), batchId);
        CrmContract contract = crmContractService.getById(crmContract.getContractId());
        return sysLogUtil.updateRecord(BeanUtil.beanToMap(contract), BeanUtil.beanToMap(crmContract), CrmTypeEnum.CONTRACT, crmContract.getName());
    }


    public List<Content> addMembers(CrmMemberSaveBO crmMemberSaveBO) {
        List<Content> contentList = new ArrayList<>();
        for (Integer id : crmMemberSaveBO.getIds()) {
            String name = crmContractService.getContractName(id);
            for (Long memberId : crmMemberSaveBO.getMemberIds()) {
                contentList.add(sysLogUtil.addMemberActionRecord(CrmTypeEnum.CONTRACT, id, memberId, name));
            }
        }
        return contentList;
    }

    public List<Content> updateMembers(CrmMemberSaveBO crmMemberSaveBO) {
        List<Content> contentList = new ArrayList<>();
        for (Integer id : crmMemberSaveBO.getIds()) {
            String name = crmContractService.getContractName(id);
            for (Long memberId : crmMemberSaveBO.getMemberIds()) {
                contentList.add(sysLogUtil.addMemberActionRecord(CrmTypeEnum.CONTRACT, id, memberId, name));
            }
        }
        return contentList;
    }

    public List<Content> deleteMembers(CrmMemberSaveBO crmMemberSaveBO) {
        List<Content> contentList = new ArrayList<>();
        for (Integer id : crmMemberSaveBO.getIds()) {
            String name = crmContractService.getContractName(id);
            for (Long memberId : crmMemberSaveBO.getMemberIds()) {
                if (!memberId.equals(UserUtil.getUserId())) {
                    contentList.add(sysLogUtil.addDeleteMemberActionRecord(CrmTypeEnum.CONTRACT, memberId, false, name));
                } else {
                    contentList.add(sysLogUtil.addDeleteMemberActionRecord(CrmTypeEnum.CONTRACT, memberId, true, name));
                }
            }
        }
        return contentList;
    }

    public Content exitTeam(Integer contractId) {
        String contractName = crmContractService.getContractName(contractId);
        return sysLogUtil.addDeleteMemberActionRecord(CrmTypeEnum.CONTRACT, UserUtil.getUserId(), true, contractName);
    }


    public Content contractDiscard(Integer contractId) {
        String contractName = crmContractService.getContractName(contractId);
        return new Content(contractName,"将合同：" + contractName + "作废",BehaviorEnum.CANCEL_EXAMINE);
    }

    public List<Content> updateInformation(CrmUpdateInformationBO updateInformationBO) {
        List<Content> contentList = new ArrayList<>();
        String batchId = updateInformationBO.getBatchId();
        updateInformationBO.getList().forEach(record -> {
            CrmContract oldContract = crmContractService.getById(updateInformationBO.getId());
            Map<String, Object> oldContractMap = BeanUtil.beanToMap(oldContract);
            if (record.getInteger("fieldType") == 1) {
                Map<String, Object> crmContractMap = new HashMap<>(oldContractMap);
                crmContractMap.put(record.getString("fieldName"), record.get("value"));
                CrmContract crmContract = BeanUtil.mapToBean(crmContractMap, CrmContract.class, true);
                contentList.add(sysLogUtil.updateRecord(oldContractMap, crmContractMap, CrmTypeEnum.CONTRACT, crmContract.getName()));
            } else if (record.getInteger("fieldType") == 0 || record.getInteger("fieldType") == 2) {
                String formType = record.getString("formType");
                if(formType == null){
                    return;
                }
                String oldFieldValue = crmContractDataService.lambdaQuery().select(CrmContractData::getValue).eq(CrmContractData::getFieldId, record.getInteger("fieldId"))
                        .eq(CrmContractData::getBatchId, batchId).last("limit 1").one().getValue();
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
                contentList.add(new Content(oldContract.getName(), detail, BehaviorEnum.UPDATE));
            }
        });
        return contentList;
    }

}
