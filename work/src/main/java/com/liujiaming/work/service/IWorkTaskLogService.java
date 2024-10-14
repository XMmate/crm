package com.liujiaming.work.service;

import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.work.entity.PO.WorkTaskLog;
import com.liujiaming.work.entity.VO.WorkTaskLogVO;

import java.util.List;

/**
 * <p>
 * 任务日志表 服务类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-15
 */
public interface IWorkTaskLogService extends BaseService<WorkTaskLog> {

    /**
     * 保存任务日志
     * @param workTaskLog
     */
    public void saveWorkTaskLog(WorkTaskLog workTaskLog);

    public List<WorkTaskLogVO> queryTaskLog(Integer taskId);
}
