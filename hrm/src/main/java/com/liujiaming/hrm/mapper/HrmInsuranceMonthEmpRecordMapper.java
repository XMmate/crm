package com.liujiaming.hrm.mapper;

import cn.hutool.core.date.DateTime;
import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.servlet.BaseMapper;
import com.liujiaming.hrm.entity.BO.QueryEmpInsuranceMonthBO;
import com.liujiaming.hrm.entity.BO.QueryInsuranceRecordListBO;
import com.liujiaming.hrm.entity.PO.HrmInsuranceMonthEmpRecord;
import com.liujiaming.hrm.entity.VO.QueryEmpInsuranceMonthVO;
import com.liujiaming.hrm.entity.VO.QueryInsurancePageListVO;
import com.liujiaming.hrm.entity.VO.SimpleHrmEmployeeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 员工每月社保记录 Mapper 接口
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-26
 */
public interface HrmInsuranceMonthEmpRecordMapper extends BaseMapper<HrmInsuranceMonthEmpRecord> {

    BasePage<QueryEmpInsuranceMonthVO> queryEmpInsuranceMonth(BasePage<QueryEmpInsuranceMonthVO> parse,@Param("data") QueryEmpInsuranceMonthBO queryEmpInsuranceMonthBO);

    List<SimpleHrmEmployeeVO> queryNoInsuranceEmp(@Param("iRecordId") Integer iRecordId,@Param("dateTime") DateTime dateTime);

    BasePage<QueryInsurancePageListVO> myInsurancePageList(BasePage<QueryInsurancePageListVO> parse,@Param("data") QueryInsuranceRecordListBO recordListBO);

}
