package com.liujiaming.hrm.service.impl;

import com.liujiaming.core.servlet.BaseServiceImpl;
import com.liujiaming.hrm.entity.PO.HrmInsuranceMonthEmpProjectRecord;
import com.liujiaming.hrm.mapper.HrmInsuranceMonthEmpProjectRecordMapper;
import com.liujiaming.hrm.service.IHrmInsuranceMonthEmpProjectRecordService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 员工每月参保项目表 服务实现类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-26
 */
@Service
public class HrmInsuranceMonthEmpProjectRecordServiceImpl extends BaseServiceImpl<HrmInsuranceMonthEmpProjectRecordMapper, HrmInsuranceMonthEmpProjectRecord> implements IHrmInsuranceMonthEmpProjectRecordService {

    @Override
    public Map<String, Object> queryProjectCount(Integer iEmpRecordId) {
        return getBaseMapper().queryProjectCount(iEmpRecordId);
    }
}
