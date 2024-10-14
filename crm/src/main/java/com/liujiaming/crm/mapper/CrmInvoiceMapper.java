package com.liujiaming.crm.mapper;

import com.liujiaming.core.servlet.BaseMapper;
import com.liujiaming.crm.common.CrmModel;
import com.liujiaming.crm.entity.PO.CrmInvoice;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liujiaming
 * @since 2024-07-06
 */
public interface CrmInvoiceMapper extends BaseMapper<CrmInvoice> {

    CrmInvoice queryById(@Param("id")Integer id);

    CrmModel queryByIds(@Param("id") Integer id, @Param("userId") Long userId);
}
