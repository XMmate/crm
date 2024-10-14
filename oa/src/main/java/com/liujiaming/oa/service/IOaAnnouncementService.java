package com.liujiaming.oa.service;

import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.entity.PageEntity;
import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.oa.entity.PO.OaAnnouncement;

/**
 * <p>
 * 公告表 服务类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-15
 */
public interface IOaAnnouncementService extends BaseService<OaAnnouncement> {
    /**
     * 添加公告
     * @param oaAnnouncement
     */
    public void addOaAnnouncement(OaAnnouncement oaAnnouncement);

    /**
     * 编辑公告
     * @param oaAnnouncement
     */
    public void setOaAnnouncement(OaAnnouncement oaAnnouncement);

    /**
     * 删除公告
     */
    public void delete(Integer announcementId);

    /**
     * 查询公告
     */
    public OaAnnouncement queryById(Integer announcementId);

    /**
     * 查询公共列表
     * @return data
     */
    public BasePage<OaAnnouncement> queryList(PageEntity entity,Integer type);
}
