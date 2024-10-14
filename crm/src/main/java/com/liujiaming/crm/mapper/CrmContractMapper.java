package com.liujiaming.crm.mapper;

import com.alibaba.fastjson.JSONObject;
import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.feign.crm.entity.CrmEventBO;
import com.liujiaming.core.servlet.BaseMapper;
import com.liujiaming.crm.common.CrmModel;
import com.liujiaming.crm.entity.PO.CrmContract;
import com.liujiaming.crm.entity.PO.CrmField;
import com.liujiaming.crm.entity.PO.CrmReceivablesPlan;
import com.liujiaming.crm.entity.VO.CrmInfoNumVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 合同表 Mapper 接口
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-27
 */
public interface CrmContractMapper extends BaseMapper<CrmContract> {

    /**
     * 通过id查询商机数据
     *
     * @param id id
     * @param userId 用户ID
     * @return data
     */
    public CrmModel queryById(@Param("id") Integer id, @Param("userId") Long userId);

    /**
     * 查询详情页数量
     * @param map map
     */
    public CrmInfoNumVO queryNum(Map<String,Object> map);

    List<CrmReceivablesPlan> queryReceivablesPlansByContractId(Integer contractId);

    public BigDecimal queryReceivablesMoney(@Param("contractId") Integer contractId);

    CrmReceivablesPlan queryReceivablesPlansByReceivablesId(Integer receivablesId);

    JSONObject querySubtotalByContractId(@Param("contractId")Integer contractId);

    BasePage<JSONObject> queryProductPageList(BasePage<Object> parse,@Param("contractId") Integer contractId);

    BasePage<JSONObject> queryReturnVisit(BasePage<Object> parse, @Param("contractId")Integer contractId, @Param("conditions")String conditions,@Param("nameList") List<CrmField> nameList);

    List<String> endContract(@Param("data") CrmEventBO crmEventBO);

    List<String> receiveContract(@Param("data") CrmEventBO crmEventBO);

    List<Integer> endContractList(@Param("userId") Long userId,@Param("time") Date time,@Param("expiringDay") Integer expiringDay);

    List<JSONObject> receiveContractList(@Param("userId") Long userId,@Param("time") Date time);
}
