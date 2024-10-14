package com.liujiaming.bi.mapper;

import com.alibaba.fastjson.JSONObject;
import com.liujiaming.bi.entity.VO.ProductStatisticsVO;
import com.liujiaming.core.utils.BiTimeUtil;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BiMapper {

    public List<ProductStatisticsVO> queryProductSell(BiTimeUtil.BiTimeEntity entity);

    public JSONObject queryProductSellCount(BiTimeUtil.BiTimeEntity entity);

    public List<JSONObject> taskCompleteStatistics(JSONObject entity);
}
