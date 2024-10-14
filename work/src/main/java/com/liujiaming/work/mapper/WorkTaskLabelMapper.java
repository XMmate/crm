package com.liujiaming.work.mapper;

import com.liujiaming.core.servlet.BaseMapper;
import com.liujiaming.work.entity.PO.WorkTaskLabel;
import com.liujiaming.work.entity.VO.TaskInfoVO;
import com.liujiaming.work.entity.VO.WorkTaskByLabelVO;
import com.liujiaming.work.entity.VO.WorkTaskLabelOrderVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 任务标签表 Mapper 接口
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-15
 */
public interface WorkTaskLabelMapper extends BaseMapper<WorkTaskLabel> {
    List<WorkTaskLabelOrderVO> getLabelList();

    List<TaskInfoVO> getTaskList(@Param("labelId")Integer labelId, @Param("userId")Long userId);

    List<WorkTaskByLabelVO> getWorkList(@Param("labelId")Integer labelId, @Param("userId")Long userId);
}
