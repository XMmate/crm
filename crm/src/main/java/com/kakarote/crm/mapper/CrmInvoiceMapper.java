package com.kakarote.crm.mapper;

import com.kakarote.core.servlet.BaseMapper;
import com.kakarote.crm.common.CrmModel;
import com.kakarote.crm.entity.PO.CrmInvoice;
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
