package com.liujiaming.crm.service;

import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.core.servlet.upload.FileEntity;
import com.liujiaming.crm.common.CrmModel;
import com.liujiaming.crm.constant.CrmTypeEnum;
import com.liujiaming.crm.entity.BO.CrmBusinessSaveBO;
import com.liujiaming.crm.entity.BO.CrmReceivablesPlanBO;
import com.liujiaming.crm.entity.BO.CrmSearchBO;
import com.liujiaming.crm.entity.BO.CrmUpdateInformationBO;
import com.liujiaming.crm.entity.PO.CrmReceivablesPlan;
import com.liujiaming.crm.entity.VO.CrmModelFiledVO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 回款计划表 服务类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-28
 */
public interface ICrmReceivablesPlanService extends BaseService<CrmReceivablesPlan> {


    /**
     * 查询所有数据
     *
     * @param search 搜索对象
     * @return data
     */
    public BasePage<Map<String, Object>> queryPageList(CrmSearchBO search);

    /**
     * 查询字段配置
     *
     * @param id 主键ID
     * @return data
     */
    public CrmModel queryById(Integer id);

    /**
     * 批量保存回款计划
     * @param receivablesPlans 回款计划列表
     */
    public void batchSave(List<CrmReceivablesPlanBO> receivablesPlans);


    /**
     * 根据合同ID删除回款计划
     * @param contractId 合同ID
     */
    public void deleteByContractId(Integer contractId);

    /**
     * 修改回款计划状态
     * @param crmTypeEnum crmTypeEnum
     * @param object 对应的PO对象
     * @param examineStatus 审批状态
     */
    public void updateReceivedStatus(CrmTypeEnum crmTypeEnum, Object object, Integer examineStatus);


    /**
     * 定时回款计划状态
     */
    public void updateReceivedStatus();

    /**
     * 全部导出
     *
     * @param response resp
     * @param search   搜索对象
     */
    public void exportExcel(HttpServletResponse response, CrmSearchBO search);

    /**
     * 保存或修改
     * @param crmModel data
     */
    public void addOrUpdate(CrmBusinessSaveBO crmModel);

    /**
     * 查询详情页基本信息
     * @param receivablesPlanId id
     * @return data
     */
    public List<CrmModelFiledVO> information(Integer receivablesPlanId);

    /**
     * 删除ids
     * @param ids ids
     */
    public void deleteByIds(List<Integer> ids);

    /**
     * 修改基本信息
     * @param updateInformationBO data
     */
    public void updateInformation(CrmUpdateInformationBO updateInformationBO);

    /**
     * 查询新增所需字段
     * @param id id
     */
    public List<CrmModelFiledVO> queryField(Integer id);

    /**
     * 查询排序后字段
     * @param id id
     * @return list
     */
    public  List<List<CrmModelFiledVO>> queryFormPositionField(Integer id);

    /**
     * 根据客户ID查询未被使用回款计划
     * @param crmReceivablesPlanBO param
     * @return data
     */
    public List<CrmReceivablesPlan> queryByContractAndCustomer(CrmReceivablesPlanBO crmReceivablesPlanBO);

    String getReceivablesPlanNum(Integer receivablesPlanId);

    /**
     * 查询文件列表
     * @param receivablesPlanId id
     * @return file
     */
    public List<FileEntity> queryFileList(Integer receivablesPlanId);
}
