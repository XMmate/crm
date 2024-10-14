package com.liujiaming.crm.service;

import com.alibaba.fastjson.JSONObject;
import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.crm.entity.PO.CrmInstrumentSort;

/**
 * <p>
 * 仪表盘排序表 服务类
 * </p>
 *
 * @author liujiaming
 * @since 2024-06-04
 */
public interface ICrmInstrumentSortService extends BaseService<CrmInstrumentSort> {

    /**
     * 查询模块排序
     * @return data
     */
    public JSONObject queryModelSort();

    /**
     * 设置模块排序
     * @param object obj
     */
    public void setModelSort(JSONObject object);
}
