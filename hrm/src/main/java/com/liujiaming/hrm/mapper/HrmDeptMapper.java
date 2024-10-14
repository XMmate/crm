package com.liujiaming.hrm.mapper;

import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.servlet.BaseMapper;
import com.liujiaming.hrm.entity.BO.QueryDeptListBO;
import com.liujiaming.hrm.entity.BO.QueryEmployeeByDeptIdBO;
import com.liujiaming.hrm.entity.PO.HrmDept;
import com.liujiaming.hrm.entity.VO.DeptEmployeeVO;
import com.liujiaming.hrm.entity.VO.DeptVO;
import com.liujiaming.hrm.entity.VO.QueryEmployeeListByDeptIdVO;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 部门表 Mapper 接口
 * </p>
 *
 * @author huangmingbo
 * @since 2024-05-12
 */
public interface HrmDeptMapper extends BaseMapper<HrmDept> {

    DeptVO queryById(@Param("deptId") Integer deptId, @Param("employeeIds")Collection<Integer> employeeIds);

    List<DeptVO> queryList(@Param("queryDeptListBO") QueryDeptListBO queryDeptListBO);

    BasePage<QueryEmployeeListByDeptIdVO> queryEmployeeByDeptId(BasePage<QueryEmployeeListByDeptIdVO> parse,@Param("data") QueryEmployeeByDeptIdBO employeeByDeptIdBO,
                                                                @Param("employeeIds")Collection<Integer> employeeIds);

    List<DeptEmployeeVO> queryDeptEmployeeList();

    List<DeptVO> queryDeptByEmpIds(@Param("employeeIds") Collection<Integer> employeeIds);
}
