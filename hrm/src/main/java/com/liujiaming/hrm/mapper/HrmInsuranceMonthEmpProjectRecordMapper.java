package com.liujiaming.hrm.mapper;

import com.liujiaming.core.servlet.BaseMapper;
import com.liujiaming.hrm.entity.PO.HrmInsuranceMonthEmpProjectRecord;

import java.util.Map;

/**
 * <p>
 * 员工每月参保项目表 Mapper 接口
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-26
 */
public interface HrmInsuranceMonthEmpProjectRecordMapper extends BaseMapper<HrmInsuranceMonthEmpProjectRecord> {

    Map<String, Object> queryProjectCount(Integer iEmpRecordId);
}
