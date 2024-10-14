package com.liujiaming.hrm.service.impl;

import com.liujiaming.core.exception.CrmException;
import com.liujiaming.core.servlet.BaseServiceImpl;
import com.liujiaming.core.utils.UserUtil;
import com.liujiaming.hrm.common.HrmCodeEnum;
import com.liujiaming.hrm.entity.BO.SetTaxRuleBO;
import com.liujiaming.hrm.entity.PO.HrmSalaryGroup;
import com.liujiaming.hrm.entity.PO.HrmSalaryTaxRule;
import com.liujiaming.hrm.mapper.HrmSalaryTaxRuleMapper;
import com.liujiaming.hrm.service.IHrmSalaryGroupService;
import com.liujiaming.hrm.service.IHrmSalaryTaxRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 计税规则 服务实现类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-26
 */
@Service
public class HrmSalaryTaxRuleServiceImpl extends BaseServiceImpl<HrmSalaryTaxRuleMapper, HrmSalaryTaxRule> implements IHrmSalaryTaxRuleService {

    @Autowired
    private IHrmSalaryGroupService salaryGroupService;


    /**
     * 查询计税列表
     * @return
     */
    @Override
    public List<HrmSalaryTaxRule> queryTaxRuleList() {
        return list().stream().peek(hrmSalaryTaxRule -> {
            Integer count = salaryGroupService.lambdaQuery().eq(HrmSalaryGroup::getRuleId, hrmSalaryTaxRule.getRuleId())
                    .count();
            hrmSalaryTaxRule.setSalaryGroupCount(count);
        }).collect(Collectors.toList());
    }


    /**
     * 修改计税规则
     * @param setTaxRuleBO
     */
    @Override
    public void setTaxRule(SetTaxRuleBO setTaxRuleBO) {
        if (!UserUtil.isAdmin()){
            throw new CrmException(HrmCodeEnum.No_Modification_Permission);
        }
        HrmSalaryTaxRule taxRule = getById(setTaxRuleBO.getRuleId());
        if (taxRule.getTaxType() != 1){
            throw new CrmException(HrmCodeEnum.CAN_ONLY_MODIFY_SALARY_AND_INCOME_TAX);
        }
        lambdaUpdate().set(HrmSalaryTaxRule::getCycleType,setTaxRuleBO.getCycleType())
                .eq(HrmSalaryTaxRule::getRuleId,setTaxRuleBO.getRuleId())
                .update();
    }
}
