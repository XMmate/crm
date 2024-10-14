package com.liujiaming.admin.mapper;

import com.liujiaming.admin.entity.BO.AdminMessageQueryBO;
import com.liujiaming.admin.entity.PO.AdminMessage;
import com.liujiaming.admin.entity.VO.AdminMessageVO;
import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.servlet.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 系统消息表 Mapper 接口
 * </p>
 *
 * @author liujiaming
 * @since 2024-04-27
 */
public interface AdminMessageMapper extends BaseMapper<AdminMessage> {
    public BasePage<AdminMessage> queryList(BasePage<AdminMessage> parse, @Param("data") AdminMessageQueryBO adminMessageBO);

    public AdminMessageVO queryUnreadCount(@Param("userId") Long userId);
}
