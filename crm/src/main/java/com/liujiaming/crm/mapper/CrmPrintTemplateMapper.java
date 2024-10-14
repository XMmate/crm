package com.liujiaming.crm.mapper;

import com.alibaba.fastjson.JSONObject;
import com.liujiaming.core.servlet.BaseMapper;
import com.liujiaming.crm.entity.PO.CrmPrintRecord;
import com.liujiaming.crm.entity.PO.CrmPrintTemplate;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 打印模板表 Mapper 接口
 * </p>
 *
 * @author liujiaming
 * @since 2024-04-27
 */
public interface CrmPrintTemplateMapper extends BaseMapper<CrmPrintTemplate> {

    public void removePrintRecord(@Param("templateId") Integer templateId);

    @Select("select a.product_id,a.price,a.sales_price,a.num as sales_num,a.discount,a.subtotal from wk_crm_business_product as a where business_id = #{id}")
    public List<JSONObject> queryBusinessProduct(@Param("id") Integer id);

    @Select("select a.product_id,a.price,a.sales_price,a.num as sales_num,a.discount,a.subtotal from wk_crm_contract_product as a where contract_id = #{id}")
    public List<JSONObject> queryContractProduct(@Param("id") Integer id);

    public List<CrmPrintRecord> queryPrintRecord(@Param("crmType") Integer crmType, @Param("typeId") Integer typeId);

}
