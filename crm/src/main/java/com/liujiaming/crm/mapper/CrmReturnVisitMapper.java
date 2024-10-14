package com.liujiaming.crm.mapper;

import com.liujiaming.core.servlet.BaseMapper;
import com.liujiaming.crm.common.CrmModel;
import com.liujiaming.crm.entity.PO.CrmReturnVisit;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liujiaming
 * @since 2024-07-06
 */
public interface CrmReturnVisitMapper extends BaseMapper<CrmReturnVisit> {
    /**
     * 通过id查询客户数据
     *
     * @param id     id
     * @return data
     */
    public  CrmModel queryById(@Param("id") Integer id);
}
