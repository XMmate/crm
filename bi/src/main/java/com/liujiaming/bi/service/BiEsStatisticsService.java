package com.liujiaming.bi.service;

import com.alibaba.fastjson.JSONObject;
import com.liujiaming.core.utils.BiTimeUtil;

import java.util.List;

/**
 * @author liujiaming
 * @date 2024/12/1
 */
public interface BiEsStatisticsService {

    /**
     * 获取统计客户信息
     * @date 2024/12/1 10:58
     * @param timeEntity
     * @param isNeedDealNum
     * @return java.util.List<com.alibaba.fastjson.JSONObject>
     **/
    List<JSONObject> getStatisticsCustomerInfo(BiTimeUtil.BiTimeEntity timeEntity,boolean isNeedDealNum);


    /**
     * 统计结果合并
     * @date 2024/12/1 10:58
     * @param customerNumList
     * @param dealNumList
     * @return java.util.List<com.alibaba.fastjson.JSONObject>
     **/
    public List<JSONObject> mergeJsonObjectList(List<JSONObject> customerNumList,List<JSONObject> dealNumList);
}
