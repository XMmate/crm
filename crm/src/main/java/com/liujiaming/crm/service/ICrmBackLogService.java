package com.liujiaming.crm.service;

import com.alibaba.fastjson.JSONObject;
import com.liujiaming.core.entity.BasePage;
import com.liujiaming.crm.entity.BO.CrmBackLogBO;
import com.liujiaming.crm.entity.PO.CrmReceivablesPlan;

import java.util.List;
import java.util.Map;

/**
 * 待办事项 服务类
 * @author liujiaming
 * @since 2024-05-23
 */
public interface ICrmBackLogService {

    /**
     * 查询待办事项数量
     * @return num
     */
    public JSONObject num();

    /**
     * 今日需联系线索（今日需联系，已逾期 已联系）
     * @return data
     */
    BasePage<Map<String, Object>> todayLeads(CrmBackLogBO crmBackLogBO);

    /**
     * 今日需联系客户
     * @return data
     */
    public BasePage<Map<String, Object>> todayCustomer(CrmBackLogBO crmBackLogBO);

    /**
     * 今日需联系商机
     * @return data
     */
    BasePage<Map<String, Object>> todayBusiness(CrmBackLogBO crmBackLogBO);

    /**
     * 待回访合同
     * @param crmBackLogBO bo
     * @return data
     */
    public BasePage<Map<String, Object>> returnVisitRemind(CrmBackLogBO crmBackLogBO);

    /**
     * 分配给我的线索
     * @return data
     */
    public BasePage<Map<String, Object>> followLeads(CrmBackLogBO crmBackLogBO);

    /**
     * 分配给我的客户
     * @return data
     */
    public BasePage<Map<String, Object>> followCustomer(CrmBackLogBO crmBackLogBO);

    /**
     * 标记线索为已跟进
     * @param ids data
     */
    public void setLeadsFollowup(List<Integer> ids);

    /**
     * 标记客户为已跟进
     */
    public void setCustomerFollowup(List<Integer> ids);

    /**
     * 待审核发票
     */
    public BasePage<Map<String, Object>> checkInvoice(CrmBackLogBO crmBackLogBO);

    /**
     * 待审核回款
     */
    public BasePage<Map<String, Object>> checkReceivables(CrmBackLogBO crmBackLogBO);

    /**
     * 待审核合同
     */
    public BasePage<Map<String, Object>> checkContract(CrmBackLogBO crmBackLogBO);

    /**
     * 待回款提醒
     */
    public BasePage<CrmReceivablesPlan> remindReceivables(CrmBackLogBO crmBackLogBO);

    /**
     * 即将到期的合同
     */
    public BasePage<Map<String, Object>> endContract(CrmBackLogBO crmBackLogBO);

    /**
     * 全部标记为已处理
     * modelID
     * @param model model
     */
    public void allDeal(Integer model);

    /**
     * 部分标记为已处理
     * @param model model模块  jsonObjectList标记列表
     */
    public void dealById(Integer model, List<JSONObject> jsonObjectList);


    /**
     *查询待进入客户池提醒
     * @param crmBackLogBO
     * @return
     */
    BasePage<Map<String, Object>> putInPoolRemind(CrmBackLogBO crmBackLogBO);

}
