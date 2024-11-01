package com.liujiaming.crm.service;

import com.alibaba.fastjson.JSONObject;
import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.feign.crm.entity.CrmEventBO;
import com.liujiaming.core.feign.crm.entity.QueryEventCrmPageBO;
import com.liujiaming.core.feign.crm.entity.SimpleCrmEntity;
import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.core.servlet.upload.FileEntity;
import com.liujiaming.crm.common.CrmModel;
import com.liujiaming.crm.entity.BO.*;
import com.liujiaming.crm.entity.PO.CrmBusiness;
import com.liujiaming.crm.entity.PO.CrmContacts;
import com.liujiaming.crm.entity.VO.CrmInfoNumVO;
import com.liujiaming.crm.entity.VO.CrmModelFiledVO;
import com.liujiaming.crm.entity.BO.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商机表 服务类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-27
 */
public interface ICrmBusinessService extends BaseService<CrmBusiness> {

    /**
     * 查询字段配置
     *
     * @param id 主键ID
     * @return data
     */
    public List<CrmModelFiledVO> queryField(Integer id);
    public List<List<CrmModelFiledVO>> queryFormPositionField(Integer id);
    /**
     * 查询商机列表页数据
     *
     * @param crmSearchBO 搜索对象
     * @return data
     */
    public BasePage<Map<String, Object>> queryPageList(CrmSearchBO crmSearchBO);

    /**
     * 查询字段配置
     *
     * @param id 主键ID
     * @return data
     */
    public CrmModel queryById(Integer id);

    /**
     * 保存或新增商机数据
     *
     * @param crmModel model
     */
    public void addOrUpdate(CrmBusinessSaveBO crmModel);

    /**
     * 删除商机数据
     *
     * @param ids ids
     */
    public void deleteByIds(List<Integer> ids);

    /**
     * 修改商机负责人
     *
     * @param changOwnerUserBO       data
     */
    public void changeOwnerUser(CrmChangeOwnerUserBO changOwnerUserBO);

    /**
     * 全部导出
     *
     * @param response resp
     * @param search   搜索对象
     */
    public void exportExcel(HttpServletResponse response, CrmSearchBO search);

    /**
     * 查询商机下联系人
     * @param pageEntity entity
     * @return data
     */
    public BasePage<CrmContacts> queryContacts(CrmContactsPageBO pageEntity);

    /**
     * 查询商机详情信息
     * @param businessId 商机id
     * @return data
     */
    public List<CrmModelFiledVO> information(Integer businessId);

    /**
     * 标星
     * @param businessId 商机id
     */
    public void star(Integer businessId);

    /**
     * 查询文件数量
     *
     * @param businessId id
     * @return data
     */
    public CrmInfoNumVO num(Integer businessId);

    /**
     * 查询文件列表
     * @param businessId id
     * @return file
     */
    public List<FileEntity> queryFileList(Integer businessId);

    /**
     * 设置首要联系人
     * @param contactsBO  data
     */
    public void setContacts(CrmFirstContactsBO contactsBO);

    /**
     * 商机关联联系人
     * @param relevanceBusinessBO 业务对象
     */
    public void relateContacts(CrmRelevanceBusinessBO relevanceBusinessBO);

    /**
     * 商机解除+关联联系人
     * @param relevanceBusinessBO 业务对象
     */
    public void unrelateContacts(CrmRelevanceBusinessBO relevanceBusinessBO);

    public List<SimpleCrmEntity> querySimpleEntity(List<Integer> ids);

    String getBusinessName(int businessId);

    void updateInformation(CrmUpdateInformationBO updateInformationBO);

    JSONObject queryProduct(CrmBusinessQueryRelationBO businessQueryProductBO);

    BasePage<JSONObject> queryContract(CrmBusinessQueryRelationBO businessQueryRelationBO);

    List<String> eventDealBusiness(CrmEventBO crmEventBO);

    BasePage<Map<String, Object>> eventDealBusinessPageList(QueryEventCrmPageBO eventCrmPageBO);

    List<String> eventBusiness(CrmEventBO crmEventBO);

    BasePage<Map<String, Object>> eventBusinessPageList(QueryEventCrmPageBO eventCrmPageBO);
}
