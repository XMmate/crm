package com.liujiaming.admin.service;

import com.liujiaming.admin.entity.BO.AdminMessageQueryBO;
import com.liujiaming.admin.entity.PO.AdminMessage;
import com.liujiaming.admin.entity.VO.AdminMessageVO;
import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.feign.admin.entity.AdminMessageBO;
import com.liujiaming.core.servlet.BaseService;

/**
 * <p>
 * 系统消息表 服务类
 * </p>
 *
 * @author liujiaming
 * @since 2024-04-27
 */
public interface IAdminMessageService extends BaseService<AdminMessage> {

    /**
     * 新增或修改消息
     *
     * @param message message
     */
    public Long saveOrUpdateMessage(com.liujiaming.core.feign.admin.entity.AdminMessage message);

    /**
     * 查询消息列表
     * @param adminMessageBO 搜索对象
     * @return data
     */
    public BasePage<AdminMessage> queryList(AdminMessageQueryBO adminMessageBO);

    /**
     * 查询未读消息数量
     * @return data
     */
    public AdminMessageVO queryUnreadCount();

    /**
     * 新增消息
     *
     * @param adminMessageBO message
     */
    public void addMessage(AdminMessageBO adminMessageBO);

    void deleteEventMessage(Integer eventId);

    void deleteById(Integer messageId);

    void deleteByLabel(Integer label);
}
