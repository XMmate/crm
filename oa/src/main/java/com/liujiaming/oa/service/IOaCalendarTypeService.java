package com.liujiaming.oa.service;

import com.alibaba.fastjson.JSONObject;
import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.feign.crm.entity.QueryEventCrmPageBO;
import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.oa.entity.BO.QueryEventCrmBO;
import com.liujiaming.oa.entity.BO.QueryEventTaskBO;
import com.liujiaming.oa.entity.BO.UpdateTypeUserBO;
import com.liujiaming.oa.entity.PO.OaCalendarType;
import com.liujiaming.oa.entity.VO.EventTaskVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 日程类型 服务类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-15
 */
public interface IOaCalendarTypeService extends BaseService<OaCalendarType> {

    void addOrUpdateType(OaCalendarType oaCalendarType);


    /**
     * 根据id删除日常类型
     * @param typeId
     */
    void deleteType(Integer typeId);


    /**
     * 查询日程类型列表
     * @return
     */
    List<OaCalendarType> queryTypeList();

    List<OaCalendarType> queryTypeListByUser(Long userId);

    void updateTypeUser(UpdateTypeUserBO updateTypeUserBO);

    List<EventTaskVO>  eventTask(QueryEventTaskBO eventTaskBO);

    JSONObject eventCrm(QueryEventCrmBO queryEventCrmBO);

    List<String> queryFixedTypeByUserId(Long userId);

    BasePage<Map<String,Object>> eventCustomer(QueryEventCrmPageBO eventCrmPageBO);

    BasePage<Map<String, Object>> eventContract(QueryEventCrmPageBO eventCrmPageBO);

    BasePage<Map<String, Object>> eventLeads(QueryEventCrmPageBO eventCrmPageBO);

    BasePage<Map<String, Object>> eventBusiness(QueryEventCrmPageBO eventCrmPageBO);

    BasePage<Map<String, Object>> eventDealBusiness(QueryEventCrmPageBO eventCrmPageBO);
}
