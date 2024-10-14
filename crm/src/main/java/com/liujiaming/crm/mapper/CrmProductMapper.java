package com.liujiaming.crm.mapper;

import com.liujiaming.core.servlet.BaseMapper;
import com.liujiaming.crm.common.CrmModel;
import com.liujiaming.crm.entity.PO.CrmProduct;

/**
 * <p>
 * 产品表 Mapper 接口
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-26
 */
public interface CrmProductMapper extends BaseMapper<CrmProduct> {
    /**
     * 通过id查询产品数据
     *
     * @param id id
     * @param userId 用户ID
     * @return data
     */
    public CrmModel queryById(Integer id,Long userId);
}
