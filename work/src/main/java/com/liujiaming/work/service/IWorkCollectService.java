package com.liujiaming.work.service;

import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.work.entity.PO.WorkCollect;

/**
 * <p>
 * 项目收藏表 服务类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-15
 */
public interface IWorkCollectService extends BaseService<WorkCollect> {

    /**
     * 收藏和取消收藏
     * @param workId
     */
    public void collect(Integer workId);
}
