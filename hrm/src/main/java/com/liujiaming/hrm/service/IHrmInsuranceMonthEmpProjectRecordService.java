package com.liujiaming.hrm.service;

import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.hrm.entity.PO.HrmInsuranceMonthEmpProjectRecord;

import java.util.Map;

/**
 * <p>
 * 员工每月参保项目表 服务类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-26
 */
public interface IHrmInsuranceMonthEmpProjectRecordService extends BaseService<HrmInsuranceMonthEmpProjectRecord> {

    /**
     * 查询社保项统计
     * @param iEmpRecordId
     * @return
     */
    Map<String, Object> queryProjectCount(Integer iEmpRecordId);
}
