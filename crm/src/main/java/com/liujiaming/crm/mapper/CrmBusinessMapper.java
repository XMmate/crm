package com.liujiaming.crm.mapper;

import com.alibaba.fastjson.JSONObject;
import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.feign.crm.entity.CrmEventBO;
import com.liujiaming.core.servlet.BaseMapper;
import com.liujiaming.crm.common.CrmModel;
import com.liujiaming.crm.entity.PO.CrmBusiness;
import com.liujiaming.crm.entity.PO.CrmContacts;
import com.liujiaming.crm.entity.VO.CrmInfoNumVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商机表 Mapper 接口
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-27
 */
public interface CrmBusinessMapper extends BaseMapper<CrmBusiness> {
    /**
     * 通过id查询商机数据
     * @param id id
     * @param userId 用户ID
     * @return data
     */
    public CrmModel queryById(@Param("id") Integer id, @Param("userId") Long userId);

    public BasePage<CrmContacts> queryContacts(BasePage<CrmContacts> crmContactsBaseMapper, @Param("businessId") Integer businessId);

    /**
     * 查询详情页数量
     * @param map map
     */
    public CrmInfoNumVO queryNum(Map<String,Object> map);

    JSONObject querySubtotalByBusinessId(Integer businessId);

    BasePage<JSONObject> queryProduct(BasePage<Object> parse,@Param("businessId") Integer businessId);

    BasePage<JSONObject> queryContract(BasePage<Object> parse,@Param("businessId") Integer businessId,@Param("conditions") String conditions);

    List<String> eventDealBusiness(@Param("data") CrmEventBO crmEventBO);

    List<Integer> eventDealBusinessPageList(@Param("userId") Long userId, @Param("time") Date time);
    List<String> eventBusiness(@Param("data") CrmEventBO crmEventBO);

    List<Integer> eventBusinessPageList(@Param("userId") Long userId, @Param("time") Date time);
}
