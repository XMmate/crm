package com.liujiaming.hrm.service.impl;

import com.liujiaming.core.servlet.BaseServiceImpl;
import com.liujiaming.hrm.entity.PO.HrmSalaryMonthEmpRecord;
import com.liujiaming.hrm.mapper.HrmSalaryMonthEmpRecordMapper;
import com.liujiaming.hrm.service.IHrmSalaryMonthEmpRecordService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 员工每月薪资记录 服务实现类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-26
 */
@Service
public class HrmSalaryMonthEmpRecordServiceImpl extends BaseServiceImpl<HrmSalaryMonthEmpRecordMapper, HrmSalaryMonthEmpRecord> implements IHrmSalaryMonthEmpRecordService {

    @Override
    public List<Integer> queryEmployeeIds(Integer sRecordId, Collection<Integer> dataAuthEmployeeIds) {
        return getBaseMapper().queryEmployeeIds(sRecordId,dataAuthEmployeeIds);
    }
}
