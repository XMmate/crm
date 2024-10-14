package com.liujiaming.crm.mapper;

import com.liujiaming.core.feign.crm.entity.CrmEventBO;
import com.liujiaming.core.servlet.BaseMapper;
import com.liujiaming.crm.common.CrmModel;
import com.liujiaming.crm.entity.PO.CrmLeads;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 线索表 Mapper 接口
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-21
 */
public interface CrmLeadsMapper extends BaseMapper<CrmLeads> {
    /**
     * 通过id查询线索数据
     *
     * @param id id
     * @param userId 用户ID
     * @return data
     */
    public CrmModel queryById(@Param("id") Integer id, @Param("userId") Long userId);

    List<String> eventLeads(@Param("data") CrmEventBO crmEventBO);

    List<Integer> eventLeadsList(@Param("userId") Long userId,@Param("time") Date time);
}
