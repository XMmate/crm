package com.kakarote.work.mapper;

import com.alibaba.fastjson.JSONObject;
import com.kakarote.core.servlet.BaseMapper;
import com.kakarote.work.entity.PO.WorkTaskComment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 任务评论表 Mapper 接口
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-18
 */
public interface WorkTaskCommentMapper extends BaseMapper<WorkTaskComment> {

    @Select("SELECT * FROM `wk_oa_log` where log_id = #{logId}")
    public JSONObject queryOaLog(@Param("logId") Integer logId);

}
