package com.liujiaming.crm.mapper;

import com.alibaba.fastjson.JSONObject;
import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.servlet.BaseMapper;
import com.liujiaming.crm.common.CrmModel;
import com.liujiaming.crm.entity.PO.CrmReceivablesPlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 回款计划表 Mapper 接口
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-28
 */
public interface CrmReceivablesPlanMapper extends BaseMapper<CrmReceivablesPlan> {

    /**
     * 通过id查询回款计划数据
     *
     * @param id id
     * @return data
     */
    public CrmModel queryById(@Param("id") Integer id);


    /**
     * 根据合同ID查询回款计划
     * @param contractId 合同ID
     * @return data
     */
    public List<CrmReceivablesPlan> queryReceivablesPlansByContractId(@Param("contractId") Integer contractId);

    /**
     * 根据合同ID查询回款计划
     * @param contractId 合同ID
     * @return data
     */
    public BasePage<CrmReceivablesPlan> queryReceivablesPlanListByContractId(BasePage<JSONObject> page,@Param("contractId") Integer contractId);

    /**
     * 批量修改状态
     */
    public List<CrmReceivablesPlan> queryReceivablesPlans();

}
