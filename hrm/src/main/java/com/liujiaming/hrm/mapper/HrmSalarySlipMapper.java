package com.liujiaming.hrm.mapper;

import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.servlet.BaseMapper;
import com.liujiaming.hrm.entity.BO.QuerySalarySlipListBO;
import com.liujiaming.hrm.entity.PO.HrmSalarySlip;
import com.liujiaming.hrm.entity.VO.QuerySalarySlipListVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 工资条 Mapper 接口
 * </p>
 *
 * @author hmb
 * @since 2024-11-03
 */
public interface HrmSalarySlipMapper extends BaseMapper<HrmSalarySlip> {

    BasePage<QuerySalarySlipListVO> querySalarySlipList(BasePage<QuerySalarySlipListVO> parse, @Param("data") QuerySalarySlipListBO querySalarySlipListBO,@Param("employeeId") Integer employeeId);
}
