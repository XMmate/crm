package com.liujiaming.work.service;

import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.work.entity.PO.WorkTaskLabel;
import com.liujiaming.work.entity.VO.WorkTaskByLabelVO;
import com.liujiaming.work.entity.VO.WorkTaskLabelOrderVO;

import java.util.List;

/**
 * <p>
 * 任务标签表 服务类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-15
 */
public interface IWorkTaskLabelService extends BaseService<WorkTaskLabel> {
    public void saveLabel(WorkTaskLabel workTaskLabel);

    public void setLabel(WorkTaskLabel workTaskLabel);

    public void deleteLabel(Integer labelId);

    public List<WorkTaskLabelOrderVO> getLabelList();

    public void updateOrder(List<Integer> labelIdList);

    public WorkTaskLabel queryById(Integer labelId);

    public List<WorkTaskByLabelVO> getTaskList(Integer labelId);
}
