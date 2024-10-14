package com.liujiaming.hrm.service;

import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.hrm.entity.PO.HrmSalaryMonthEmpRecord;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 员工每月薪资记录 服务类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-26
 */
public interface IHrmSalaryMonthEmpRecordService extends BaseService<HrmSalaryMonthEmpRecord> {


    /**
     * 查询薪资员工id
     * @param sRecordId
     * @param dataAuthEmployeeIds
     * @return
     */
    List<Integer> queryEmployeeIds(Integer sRecordId, Collection<Integer> dataAuthEmployeeIds);
}
