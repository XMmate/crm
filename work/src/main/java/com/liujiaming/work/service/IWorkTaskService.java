package com.liujiaming.work.service;

import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.work.entity.BO.*;
import com.liujiaming.work.entity.PO.WorkTask;
import com.liujiaming.work.entity.VO.MyTaskVO;
import com.liujiaming.work.entity.VO.OaTaskListVO;
import com.liujiaming.work.entity.VO.TaskDetailVO;
import com.liujiaming.work.entity.VO.TaskInfoVO;
import com.liujiaming.work.entity.BO.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 任务表 服务类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-18
 */
public interface IWorkTaskService extends BaseService<WorkTask> {

    public List<MyTaskVO> myTask(WorkTaskNameBO workTaskNameBO, boolean isInternal);

    public void taskListTransfer(List<TaskInfoVO> taskList);

    public void updateTop(UpdateTaskTopBo updateTaskClassBo);

    /**
     * 在项目下新建项目的任务
     * @param workTask
     */
    public void saveWorkTask(WorkTask workTask);

    public boolean updateWorkTask(WorkTask task);

    public void setWorkTaskStatus(WorkTaskStatusBO workTaskStatusBO);

    public void setWorkTaskTitle(WorkTaskNameBO workTaskNameBO);

    public void setWorkTaskDescription(WorkTaskDescriptionBO workTaskDescriptionBO);

    public void setWorkTaskMainUser(WorkTaskUserBO workTaskUserBO);

    public void setWorkTaskOwnerUser(WorkTaskOwnerUserBO workTaskOwnerUserBO);

    public void setWorkTaskTime(WorkTask workTask);

    public void setWorkTaskLabel(WorkTaskLabelsBO workTaskLabelsBO);

    public void setWorkTaskPriority(WorkTaskPriorityBO workTaskPriorityBO);

    public WorkTask addWorkChildTask(WorkTask workTask);

    public void updateWorkChildTask(WorkTask workTask);

    public void setWorkChildTaskStatus(WorkTaskStatusBO workTaskStatusBO);

    public void deleteWorkTaskOwnerUser(WorkTaskUserBO workTaskUserBO);

    public void deleteWorkTaskLabel(WorkTaskLabelBO workTaskLabelBO);

    public TaskDetailVO queryTaskInfo(Integer taskId);

    public TaskDetailVO queryTrashTaskInfo(Integer taskId);

    /**
     * 删除任务
     * @param taskId
     */
    public void deleteWorkTask(Integer taskId);

    public void archiveByTaskId(Integer taskId);

    /**
     * 查看回收站任务列表
     * @return
     */
    public List<TaskInfoVO> queryTrashList();

    public void deleteTask(Integer taskId);

    public void restore(Integer taskId);

    public OaTaskListVO queryTaskList(OaTaskListBO oaTaskListBO);

    List<Map<String, Object>> workBenchTaskExport();


    public String getPriorityDesc(Integer priority);

}
