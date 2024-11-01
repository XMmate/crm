package com.liujiaming.crm.mapper;

import cn.hutool.core.lang.Dict;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.SqlParser;
import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.servlet.BaseMapper;
import com.liujiaming.crm.entity.PO.CrmActivity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * crm活动表 Mapper 接口
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-25
 */
public interface CrmActivityMapper extends BaseMapper<CrmActivity> {
    /**
     * 查询活动分页统计
     *
     * @param map map
     * @return data
     */
    public List<String> getActivityCountByTime(Map<String, Object> map);

    public List<CrmActivity> getCrmActivityPageList(Map<String, Object> map);

    CrmActivity queryActivityById(Integer activityId);

    @SqlParser(filter = true)
    BasePage<CrmActivity> queryRecordList(BasePage<Object> parse, @Param("data") Dict data);

    @SqlParser(filter = true)
    List<Map<String,Object>> exportRecordList( @Param("data") Dict data);

    public BasePage<JSONObject> queryOutworkStats(BasePage<JSONObject> parse, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("userIds") List<Long> userIds);

    public BasePage<CrmActivity> queryOutworkList(BasePage<CrmActivity> parse, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("userId") Long userId);
}
