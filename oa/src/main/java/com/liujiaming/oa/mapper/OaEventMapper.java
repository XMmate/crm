package com.liujiaming.oa.mapper;

import com.liujiaming.core.servlet.BaseMapper;
import com.liujiaming.oa.entity.BO.QueryEventListBO;
import com.liujiaming.oa.entity.PO.OaEvent;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 日程表 Mapper 接口
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-15
 */
public interface OaEventMapper extends BaseMapper<OaEvent> {

    List<OaEvent> queryList(@Param("queryEventListBO") QueryEventListBO queryEventListBO);
}
