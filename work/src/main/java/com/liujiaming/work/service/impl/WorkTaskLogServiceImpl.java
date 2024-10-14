package com.liujiaming.work.service.impl;

import cn.hutool.core.date.DateUtil;
import com.liujiaming.core.exception.CrmException;
import com.liujiaming.core.servlet.ApplicationContextHolder;
import com.liujiaming.core.servlet.BaseServiceImpl;
import com.liujiaming.work.common.WorkAuthUtil;
import com.liujiaming.work.common.WorkCodeEnum;
import com.liujiaming.work.entity.PO.WorkTaskLog;
import com.liujiaming.work.entity.VO.WorkTaskLogVO;
import com.liujiaming.work.mapper.WorkTaskLogMapper;
import com.liujiaming.work.service.IWorkTaskLogService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 任务日志表 服务实现类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-15
 */
@Service
public class WorkTaskLogServiceImpl extends BaseServiceImpl<WorkTaskLogMapper, WorkTaskLog> implements IWorkTaskLogService {
    /**
     * 保存任务日志
     * @param workTaskLog
     */
    @Override
    public void saveWorkTaskLog(WorkTaskLog workTaskLog) {
        workTaskLog.setCreateTime(DateUtil.date());
        workTaskLog.setLogId(null);
        save(workTaskLog);
    }

    @Override
    public List<WorkTaskLogVO> queryTaskLog(Integer taskId) {
        WorkAuthUtil workAuthUtil = ApplicationContextHolder.getBean(WorkAuthUtil.class);
        if (!workAuthUtil.isWorkAuth(taskId)){
            throw new CrmException(WorkCodeEnum.WORK_AUTH_ERROR);
        }
        return getBaseMapper().queryTaskLog(taskId);
    }
}
