package com.liujiaming.crm.mapper;

import com.alibaba.fastjson.JSONObject;
import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.servlet.BaseMapper;
import com.liujiaming.crm.common.CrmModel;
import com.liujiaming.crm.entity.PO.CrmReceivables;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 回款表 Mapper 接口
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-28
 */
public interface CrmReceivablesMapper extends BaseMapper<CrmReceivables> {
    /**
     * 通过id查询回款数据
     *
     * @param id id
     * @param userId 用户ID
     * @return data
     */
    public CrmModel queryById(@Param("id") Integer id, @Param("userId") Long userId);

    BasePage<JSONObject> queryListByContractId(BasePage<JSONObject> page,@Param("contractId") Integer contractId, @Param("conditions") String conditions);
}
