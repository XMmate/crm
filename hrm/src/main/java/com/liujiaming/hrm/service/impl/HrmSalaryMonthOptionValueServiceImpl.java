package com.liujiaming.hrm.service.impl;

import com.liujiaming.core.servlet.BaseServiceImpl;
import com.liujiaming.hrm.entity.DTO.ComputeSalaryDTO;
import com.liujiaming.hrm.entity.PO.HrmSalaryMonthOptionValue;
import com.liujiaming.hrm.mapper.HrmSalaryMonthOptionValueMapper;
import com.liujiaming.hrm.service.IHrmSalaryMonthOptionValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 每月员工薪资项表 服务实现类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-26
 */
@Service
public class HrmSalaryMonthOptionValueServiceImpl extends BaseServiceImpl<HrmSalaryMonthOptionValueMapper, HrmSalaryMonthOptionValue> implements IHrmSalaryMonthOptionValueService {

    @Autowired
    private HrmSalaryMonthOptionValueMapper optionValueMapper;

    @Override
    public List<ComputeSalaryDTO> querySalaryOptionValue(Integer sEmpRecordId) {
        return optionValueMapper.querySalaryOptionValue(sEmpRecordId);
    }
}
