package com.liujiaming.hrm.mapper;

import com.liujiaming.core.servlet.BaseMapper;
import com.liujiaming.hrm.entity.PO.HrmEmployeeContract;

import java.util.List;

/**
 * <p>
 * 员工合同 Mapper 接口
 * </p>
 *
 * @author huangmingbo
 * @since 2024-05-12
 */
public interface HrmEmployeeContractMapper extends BaseMapper<HrmEmployeeContract> {

    List<Integer> queryToExpireContractCount();


}
