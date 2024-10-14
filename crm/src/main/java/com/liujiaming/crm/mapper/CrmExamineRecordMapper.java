package com.liujiaming.crm.mapper;

import com.alibaba.fastjson.JSONObject;
import com.liujiaming.core.servlet.BaseMapper;
import com.liujiaming.crm.entity.PO.CrmExamineRecord;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 审核记录表 Mapper 接口
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-28
 */
public interface CrmExamineRecordMapper extends BaseMapper<CrmExamineRecord> {
    public JSONObject queryExamineRecordById(@Param("recordId") Integer recordId);

    public JSONObject queryInfoByRecordId(@Param("recordId") Integer recordId,@Param("tableName")String tableName);
}
