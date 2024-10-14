package com.liujiaming.bi.service;

import com.alibaba.fastjson.JSONObject;
import com.liujiaming.bi.entity.VO.BiPageVO;
import com.liujiaming.bi.entity.VO.BiParamVO;
import com.liujiaming.core.feign.crm.entity.BiParams;

import java.util.List;

public interface BiWorkService {

    /**
     * 查询日志统计信息
     * @author zhang
     */
    public List<JSONObject> logStatistics(BiParams biParams);

    /**
     * 查询审批统计信息
     * @author zhang
     */
    public JSONObject examineStatistics(BiParams biParams);

    /**
     * 查询审批详情
     * */
    BiPageVO<JSONObject> examineInfo(BiParamVO biParams);
}
