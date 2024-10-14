package com.liujiaming.work.service;

import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.work.entity.PO.WorkTaskClass;

import java.util.List;

/**
 * <p>
 * 任务分类表 服务类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-15
 */
public interface IWorkTaskClassService extends BaseService<WorkTaskClass> {
    public List<WorkTaskClass> queryClassNameByWorkId(Integer workId);

    public void saveWorkTaskClass(WorkTaskClass workTaskClass);

    public void updateWorkTaskClass(WorkTaskClass workTaskClass);
}
