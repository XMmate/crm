package com.liujiaming.hrm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.entity.PageEntity;
import com.liujiaming.core.exception.CrmException;
import com.liujiaming.core.servlet.BaseServiceImpl;
import com.liujiaming.core.utils.TagUtil;
import com.liujiaming.core.utils.UserUtil;
import com.liujiaming.hrm.common.HrmCodeEnum;
import com.liujiaming.hrm.entity.BO.SetSalaryGroupBO;
import com.liujiaming.hrm.entity.PO.HrmEmployee;
import com.liujiaming.hrm.entity.PO.HrmSalaryGroup;
import com.liujiaming.hrm.entity.PO.HrmSalaryTaxRule;
import com.liujiaming.hrm.entity.VO.SalaryGroupPageListVO;
import com.liujiaming.hrm.mapper.HrmSalaryGroupMapper;
import com.liujiaming.hrm.service.IHrmDeptService;
import com.liujiaming.hrm.service.IHrmEmployeeService;
import com.liujiaming.hrm.service.IHrmSalaryGroupService;
import com.liujiaming.hrm.service.IHrmSalaryTaxRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 薪资组 服务实现类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-26
 */
@Service("salaryGroupService")
public class HrmSalaryGroupServiceImpl extends BaseServiceImpl<HrmSalaryGroupMapper, HrmSalaryGroup> implements IHrmSalaryGroupService {

    @Autowired
    private HrmSalaryGroupMapper salaryGroupMapper;

    @Autowired
    private IHrmSalaryTaxRuleService taxRuleService;

    @Autowired
    private IHrmDeptService deptService;

    @Autowired
    private IHrmEmployeeService employeeService;

    @Override
    public BasePage<SalaryGroupPageListVO> querySalaryGroupPageList(PageEntity pageEntity) {
        BasePage<HrmSalaryGroup> salaryGroupBasePage = salaryGroupMapper.selectPage(pageEntity.parse(), Wrappers.emptyWrapper());
        List<SalaryGroupPageListVO> salaryGroupPageListVOList = new ArrayList<>();
        salaryGroupBasePage.getList().forEach(salaryGroup -> {
            HrmSalaryTaxRule taxRule = taxRuleService.lambdaQuery().select(HrmSalaryTaxRule::getRuleName)
                    .eq(HrmSalaryTaxRule::getRuleId, salaryGroup.getRuleId()).last("limit 1").one();
            SalaryGroupPageListVO salaryGroupPageListVO = new SalaryGroupPageListVO();
            BeanUtil.copyProperties(salaryGroup,salaryGroupPageListVO);
            salaryGroupPageListVO.setRuleName(taxRule.getRuleName());
            Set<Integer> deptIds = TagUtil.toSet(salaryGroup.getDeptIds());
            if (CollUtil.isNotEmpty(deptIds)){
                salaryGroupPageListVO.setDeptList(deptService.querySimpleDeptList(deptIds));
            }
            Set<Integer> employeeIds = TagUtil.toSet(salaryGroup.getEmployeeIds());
            if (CollUtil.isNotEmpty(employeeIds)){
                salaryGroupPageListVO.setEmployeeList(employeeService.querySimpleEmployeeList(employeeIds));
            }
            salaryGroupPageListVOList.add(salaryGroupPageListVO);
        });
        BasePage<SalaryGroupPageListVO> page = new BasePage<>(salaryGroupBasePage.getCurrent(),salaryGroupBasePage.getSize(),salaryGroupBasePage.getTotal());
        page.setList(salaryGroupPageListVOList);
        return page;
    }

    @Override
    public HrmSalaryTaxRule queryEmployeeTaxRule(Integer employeeId) {
        HrmEmployee employee = employeeService.getById(employeeId);
        HrmSalaryGroup salaryGroup = salaryGroupMapper.queryEmployeeSalaryGroupByEmpId(employeeId);
        if (salaryGroup == null){
            Set<Integer> deptIds = deptService.queryParentDeptId(employee.getDeptId());
            salaryGroup = salaryGroupMapper.queryEmployeeSalaryGroupByDId(deptIds);
        }
        return taxRuleService.getById(salaryGroup.getRuleId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setSalaryGroup(SetSalaryGroupBO salaryGroup) {
        if (!UserUtil.isAdmin()){
            //todo 待完善
            throw new CrmException(null);
        }
        List<HrmSalaryGroup> salaryGroupList = lambdaQuery().ne(salaryGroup.getGroupId() != null,HrmSalaryGroup::getGroupId,salaryGroup.getGroupId()).list();
        List<Integer> addDeptIds = salaryGroup.getDeptIds();
        List<Integer> addEmployeeIds = salaryGroup.getEmployeeIds();
        salaryGroupList.forEach(salaryGroup1 -> {
            Set<Integer> deptIds = TagUtil.toSet(salaryGroup1.getDeptIds());
            Set<Integer> employeeIds = TagUtil.toSet(salaryGroup1.getEmployeeIds());
            Collection<Integer> empIntersection = CollUtil.intersection(addEmployeeIds, employeeIds);
            if (empIntersection.size()>0){
                throw new CrmException(HrmCodeEnum.THE_SALARY_GROUP_ALREADY_HAS_SELECTED_EMPLOYEE);
            }
            Set<Integer> list = deptService.queryChildDeptId(deptIds);
            Set<Integer> addList = deptService.queryChildDeptId(addDeptIds);
            Collection<Integer> deptIntersection = CollUtil.intersection(list, addList);
            if (deptIntersection.size()>0){
                throw new CrmException(HrmCodeEnum.THE_SALARY_GROUP_ALREADY_HAS_SELECTED_DEPT);
            }
        });
        HrmSalaryGroup salaryGroup1 = BeanUtil.copyProperties(salaryGroup, HrmSalaryGroup.class);
        salaryGroup1.setDeptIds(TagUtil.fromSet(salaryGroup.getDeptIds()));
        salaryGroup1.setEmployeeIds(TagUtil.fromSet(salaryGroup.getEmployeeIds()));
        saveOrUpdate(salaryGroup1);
    }

    @Override
    public List<HrmSalaryGroup> querySalaryGroupByTaxType(int taxType) {
        return getBaseMapper().querySalaryGroupByTaxType(taxType);
    }
}
