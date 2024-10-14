package com.liujiaming.crm.mapper;

import com.liujiaming.core.servlet.BaseMapper;
import com.liujiaming.crm.entity.PO.CrmCustomerPool;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 公海表 Mapper 接口
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-29
 */
public interface CrmCustomerPoolMapper extends BaseMapper<CrmCustomerPool> {

    List<Integer> queryPoolIdByUserId(@Param("userId") Long userId,@Param("deptId") Integer deptId);

    Set<Integer> putInPoolByRecord(@Param("data") Map<String, Object> record);

    Set<Integer> putInPoolByBusiness(@Param("data") Map<String, Object> record);

    Set<Integer> putInPoolByDealStatus(@Param("data") Map<String, Object> record);

}
