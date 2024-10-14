package com.liujiaming.crm.service;

import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.entity.PageEntity;
import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.crm.entity.PO.CrmMarketingForm;

import java.util.List;

/**
 * @author liujiaming
 * @date 2024/12/2
 */
public interface ICrmMarketingFormService extends BaseService<CrmMarketingForm> {

    CrmMarketingForm saveOrUpdateCrmMarketingForm(CrmMarketingForm crmMarketingForm);

    BasePage<CrmMarketingForm> queryCrmMarketingFormList(PageEntity pageEntity);

    List<CrmMarketingForm> queryAllCrmMarketingFormList();

    void deleteCrmMarketingForm(Integer id);

    void updateStatus(CrmMarketingForm crmMarketingForm);
}
