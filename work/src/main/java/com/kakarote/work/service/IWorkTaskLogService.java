package com.kakarote.work.service;

import com.kakarote.core.servlet.BaseService;
import com.kakarote.work.entity.PO.WorkTaskLog;
import com.kakarote.work.entity.VO.WorkTaskLogVO;

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

    public void saveWorkTaskLog(WorkTaskLog workTaskLog);

    public List<WorkTaskLogVO> queryTaskLog(Integer taskId);
}
