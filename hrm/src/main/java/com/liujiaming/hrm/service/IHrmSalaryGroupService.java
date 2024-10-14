package com.liujiaming.hrm.service;

import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.entity.PageEntity;
import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.hrm.entity.BO.SetSalaryGroupBO;
import com.liujiaming.hrm.entity.PO.HrmSalaryGroup;
import com.liujiaming.hrm.entity.PO.HrmSalaryTaxRule;
import com.liujiaming.hrm.entity.VO.SalaryGroupPageListVO;

import java.util.List;

/**
 * <p>
 * 薪资组 服务类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-26
 */
public interface IHrmSalaryGroupService extends BaseService<HrmSalaryGroup> {

    /**
     * 查询薪资组列表
     * @param pageEntity
     * @return
     */
    BasePage<SalaryGroupPageListVO> querySalaryGroupPageList(PageEntity pageEntity);

    /**
     * 查询员工计税规则
     * @param employeeId
     * @return
     */
    HrmSalaryTaxRule queryEmployeeTaxRule(Integer employeeId);

    /**
     * 添加修改薪资组
     * @param salaryGroup
     */
    void setSalaryGroup(SetSalaryGroupBO salaryGroup);

    /**
     * 根据计税类型查询薪资组
     * @param taxType
     * @return
     */
    List<HrmSalaryGroup> querySalaryGroupByTaxType(int taxType);
}
