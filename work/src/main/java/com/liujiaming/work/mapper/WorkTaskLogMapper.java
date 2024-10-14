package com.liujiaming.work.mapper;

import com.liujiaming.core.servlet.BaseMapper;
import com.liujiaming.work.entity.PO.WorkTaskLog;
import com.liujiaming.work.entity.VO.WorkTaskLogVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 任务日志表 Mapper 接口
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-15
 */
public interface WorkTaskLogMapper extends BaseMapper<WorkTaskLog> {
    List<WorkTaskLogVO> queryTaskLog(@Param("taskId")Integer taskId);
}
