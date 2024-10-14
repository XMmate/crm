package com.liujiaming.oa.mapper;

import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.servlet.BaseMapper;
import com.liujiaming.oa.entity.PO.OaAnnouncement;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 公告表 Mapper 接口
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-15
 */
public interface OaAnnouncementMapper extends BaseMapper<OaAnnouncement> {
    public BasePage<OaAnnouncement> queryList(BasePage<OaAnnouncement> parse, @Param("type") Integer type, @Param("userId") Long userId, @Param("deptId") Integer deptId);
}
