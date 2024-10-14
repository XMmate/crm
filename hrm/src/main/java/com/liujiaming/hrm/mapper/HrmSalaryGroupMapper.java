package com.liujiaming.hrm.mapper;

import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.servlet.BaseMapper;
import com.liujiaming.hrm.entity.PO.HrmSalaryGroup;
import com.liujiaming.hrm.entity.VO.SalaryGroupPageListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 薪资组 Mapper 接口
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-26
 */
public interface HrmSalaryGroupMapper extends BaseMapper<HrmSalaryGroup> {

    BasePage<SalaryGroupPageListVO> querySalaryGroupPageList(BasePage<SalaryGroupPageListVO> parse);

    /**
     * 通过员工id查询薪资组
     * @param employeeId
     * @return
     */
    HrmSalaryGroup queryEmployeeSalaryGroupByEmpId(@Param("employeeId")Integer employeeId);

    /**
     * 通过部门id查询薪资组
     * @param deptIds
     * @return
     */
    HrmSalaryGroup queryEmployeeSalaryGroupByDId(@Param("deptIds") Set<Integer> deptIds);

    List<HrmSalaryGroup> querySalaryGroupByTaxType(int taxType);
}
