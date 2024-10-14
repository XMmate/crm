package com.liujiaming.crm.service;

import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.crm.entity.BO.MarketingFieldBO;
import com.liujiaming.crm.entity.PO.CrmMarketingField;

import java.util.List;

/**
 * @author liujiaming
 * @date 2024/12/2
 */
public interface ICrmMarketingFieldService extends BaseService<CrmMarketingField> {

    List<CrmMarketingField> queryField(Integer id);

    void recordToFormType(List<CrmMarketingField> list);

    void transferFieldList(List<CrmMarketingField> recordList, Integer isDetail);

    void saveField(MarketingFieldBO marketingFieldBO);
}
