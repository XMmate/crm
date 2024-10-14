package com.liujiaming.hrm.mapper;

import com.liujiaming.core.servlet.BaseMapper;
import com.liujiaming.hrm.entity.DTO.ComputeSalaryDTO;
import com.liujiaming.hrm.entity.PO.HrmSalaryMonthOptionValue;

import java.util.List;

/**
 * <p>
 * 每月员工薪资项表 Mapper 接口
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-26
 */
public interface HrmSalaryMonthOptionValueMapper extends BaseMapper<HrmSalaryMonthOptionValue> {

    List<ComputeSalaryDTO> querySalaryOptionValue(Integer sEmpRecordId);
}
