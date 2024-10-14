package com.liujiaming.crm.service;

import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.crm.entity.PO.CrmContractProduct;

import java.util.List;

/**
 * <p>
 * 合同产品关系表 服务类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-27
 */
public interface ICrmContractProductService extends BaseService<CrmContractProduct> {
    /**
     * 根据合同ID删除合同产品关系表
     * @param contractId 合同ID
     */
    public void deleteByContractId(Integer... contractId);

    /**
     * 根据合同ID查询合同产品关系表
     * @param contractId 合同ID
     */
    public List<CrmContractProduct> queryByContractId(Integer contractId);

    /**
     * 查询合同下产品
     * @param contractId 商机ID
     * @return data
     */
    public List<CrmContractProduct> queryList(Integer contractId);
}
