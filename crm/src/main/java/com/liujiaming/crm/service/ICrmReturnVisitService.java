package com.liujiaming.crm.service;

import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.feign.admin.entity.AdminConfig;
import com.liujiaming.core.feign.crm.entity.SimpleCrmEntity;
import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.core.servlet.upload.FileEntity;
import com.liujiaming.crm.common.CrmModel;
import com.liujiaming.crm.entity.BO.CrmBusinessSaveBO;
import com.liujiaming.crm.entity.BO.CrmSearchBO;
import com.liujiaming.crm.entity.BO.CrmUpdateInformationBO;
import com.liujiaming.crm.entity.PO.CrmReturnVisit;
import com.liujiaming.crm.entity.VO.CrmModelFiledVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liujiaming
 * @since 2024-07-06
 */
public interface ICrmReturnVisitService extends BaseService<CrmReturnVisit> {

    BasePage<Map<String, Object>> queryPageList(CrmSearchBO search);

    List<SimpleCrmEntity> querySimpleEntity(List<Integer> ids);

    void addOrUpdate(CrmBusinessSaveBO crmModel);

    CrmModel queryById(Integer visitId);

    List<CrmModelFiledVO> queryField(Integer id);

    List<List<CrmModelFiledVO>> queryFormPositionField(Integer id);

    List<CrmModelFiledVO> information(Integer visitId);

    List<FileEntity> queryFileList(Integer id);

    void deleteByIds(List<Integer> ids);

    /**
     * 修改回访提醒设置
     * @param status 状态
     * @param value 值
     */
    public void updateReturnVisitRemindConfig(Integer status,Integer value);

    /**
     * 查询回访提醒设置
     * @return data
     */
    public AdminConfig queryReturnVisitRemindConfig();

    public void updateInformation(CrmUpdateInformationBO updateInformationBO);
}
