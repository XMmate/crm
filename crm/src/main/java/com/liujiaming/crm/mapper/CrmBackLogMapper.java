package com.liujiaming.crm.mapper;

import com.alibaba.fastjson.JSONObject;
import com.liujiaming.core.entity.BasePage;
import com.liujiaming.crm.entity.PO.CrmReceivablesPlan;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface CrmBackLogMapper {

    /**
     * 今日需联系客户（下次联系时间是今天）
     * @param map
     * @return
     */

    public Integer todayCustomerNum(Map<String, Object> map);

    /**
     * 今日需联系线索 （跟进时间是今天的线索条数）
     * @param paras
     * @return
     */
    Integer todayLeadsNum(Map<String, Object> paras);

    /**
     * 今日需联系商机（下次联系时间是今天）
     * @param paras
     * @return
     */
    Integer todayBusinessNum(Map<String, Object> paras);

    /**
     *分配给我的客户（未跟进的客户）
     * @param map
     * @return
     */
    public Integer followCustomerNum(Map<String, Object> map);


    /**
     * 今日需联系中，已经逾期的数据数量
     * @param paras 参数
     * @return 数量
     */
    public Integer todayOvertimeNum(Map<String, Object> paras);


    /**
     * 公海里面到期回收的
     * @param map
     * @return
     */
    public List<JSONObject> putInPoolByRecord(Map<String, Object> map);

    public List<JSONObject> putInPoolByBusiness(Map<String, Object> map);

    public List<JSONObject> putInPoolByDealStatus(Map<String, Object> map);

    /**
     *未跟进的线索
     * @param map
     * @return
     */
    public Integer followLeadsNum(Map<String, Object> map);

    /**
     * 即将到期的合同
     * @param map
     * @return
     */
    public Integer endContractNum(Map<String, Object> map);

    /**回访提醒
     *
     * @param map
     * @return
     */
    public Integer returnVisitRemindNum(Map<String, Object> map);

    /**
     * 应收账款计划提醒
     * @param map
     * @return
     */
    public Integer remindReceivablesPlanNum(Map<String, Object> map);

    /**、
     *应收账款已逾期提醒
     * @param date
     * @param userId
     * @return
     */
    public Integer remindReceivablesOvertimeNum(@Param("date")Date date,@Param("userId")Long userId);


    /**
     * 提醒应收账款
     * @param parse
     * @param type
     * @param ids
     * @param userId
     * @return
     */
    public BasePage<CrmReceivablesPlan> remindReceivables(BasePage<CrmReceivablesPlan> parse, @Param("type") Integer type, @Param("ids") List<String> ids, @Param("userId") Long userId);

    @Select("select customer_id from wk_crm_customer where to_days(next_time) = to_days(now()) and last_time < next_time and owner_user_id = #{userId} and status != 3")
    public List<Integer> queryTodayCustomerId(@Param("userId") Long userId);

    @Select("select leads_id from wk_crm_leads where followup = 0 and is_transform = 0  and owner_user_id = #{userId}")
    public List<Integer> queryFollowLeadsId(@Param("userId") Long userId);

    @Select("select customer_id from wk_crm_customer where is_receive = 1 and followup = 0 and status = 1 and owner_user_id = #{userId}")
    public List<Integer> queryFollowCustomerId(@Param("userId") Long userId);

    @Select("select type_id from wk_crm_back_log_deal where create_user_id = #{userId} and crm_type = #{type} and model = #{model} and pool_id = #{poolId}")
    public List<Integer> queryDealIdByPoolId(@Param("userId") Long userId, @Param("type") Integer type, @Param("model") Integer model, @Param("poolId") Integer poolId);
    
    @Select("select a.receivables_plan_id from wk_crm_receivables_plan as a inner join wk_crm_customer as b on a.customer_id = b.customer_id\n" +
            "  inner join wk_crm_contract as c on a.contract_id = c.contract_id\n" +
            "  where to_days(a.return_date) >= to_days(now()) and to_days(a.return_date) <= to_days(now())+a.remind and receivables_id is null and c.owner_user_id = #{userId}")
    public List<Integer> queryRemindReceivablesPlanId(@Param("userId") Long userId);
    
    @Select("select a.contract_id from wk_crm_contract as a inner join wk_crm_customer as b on a.customer_id = b.customer_id where check_status = 1 and to_days(a.end_time) >= to_days(now()) and to_days(a.end_time) <= to_days(now()) + IFNULL(#{remindDay},0) and a.owner_user_id = #{userId}")
    public List<Integer> queryEndContractId(@Param("userId") Long userId,@Param("remindDay")Integer remindDay);
    
    @Select("select contract_id from wk_crm_contract where check_status = 1 and owner_user_id = #{userId} and to_days(now()) - to_days(start_time) >= #{remindDay}")
    public List<Integer> queryReturnVisitContractId(@Param("userId") Long userId,@Param("remindDay")Integer remindDay);

    @Select("select contract_id from wk_crm_contract where check_status = 1 and owner_user_id in (${userIds}) and to_days(now()) - to_days(start_time) >= #{remindDay}")
    public List<String> returnVisitRemind(@Param("userIds") String userIds,@Param("remindDay")String remindDay);

    @Select("select leads_id from wk_crm_leads where to_days(next_time) = to_days(now()) and last_time < next_time and owner_user_id = #{userId}")
    List<Integer> queryTodayLeadsId(Long userId);

    @Select("select business_id from wk_crm_business where to_days(next_time) = to_days(now()) and last_time < next_time and owner_user_id = #{userId} and status != 3")
    List<Integer> queryTodayBusinessId(Long userId);


}
