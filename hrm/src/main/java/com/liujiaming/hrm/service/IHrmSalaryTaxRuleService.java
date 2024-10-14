package com.liujiaming.hrm.service;

import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.hrm.entity.BO.SetTaxRuleBO;
import com.liujiaming.hrm.entity.PO.HrmSalaryTaxRule;

import java.util.List;

/**
 * <p>
 * 计税规则 服务类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-26
 */
public interface IHrmSalaryTaxRuleService extends BaseService<HrmSalaryTaxRule> {

    /**
     * 查询计税规则列表
     * @return
     */
    List<HrmSalaryTaxRule> queryTaxRuleList();

    /**
     * 修改计规则
     * @param setTaxRuleBO
     */
    void setTaxRule(SetTaxRuleBO setTaxRuleBO);

}
