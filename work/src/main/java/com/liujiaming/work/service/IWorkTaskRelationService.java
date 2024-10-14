package com.liujiaming.work.service;

import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.work.entity.PO.WorkTaskRelation;

/**
 * <p>
 * 任务关联业务表 服务类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-18
 */
public interface IWorkTaskRelationService extends BaseService<WorkTaskRelation> {
    public void saveWorkTaskRelation(WorkTaskRelation workTaskRelation);
}
