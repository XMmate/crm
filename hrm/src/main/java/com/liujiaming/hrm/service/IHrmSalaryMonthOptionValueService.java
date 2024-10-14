package com.liujiaming.hrm.service;

import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.hrm.entity.DTO.ComputeSalaryDTO;
import com.liujiaming.hrm.entity.PO.HrmSalaryMonthOptionValue;

import java.util.List;

/**
 * <p>
 * 每月员工薪资项表 服务类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-26
 */
public interface IHrmSalaryMonthOptionValueService extends BaseService<HrmSalaryMonthOptionValue> {

    /**
     * 查询计算的薪资项
     * @param sEmpRecordId
     */
    List<ComputeSalaryDTO> querySalaryOptionValue(Integer sEmpRecordId);
}
