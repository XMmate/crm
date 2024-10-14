package com.liujiaming.work.common.log;

import com.liujiaming.core.common.log.Content;
import com.liujiaming.core.servlet.ApplicationContextHolder;
import com.liujiaming.core.utils.UserCacheUtil;
import com.liujiaming.work.entity.BO.DeleteTaskClassBO;
import com.liujiaming.work.entity.BO.RemoveWorkOwnerUserBO;
import com.liujiaming.work.entity.PO.Work;
import com.liujiaming.work.entity.PO.WorkTask;
import com.liujiaming.work.entity.PO.WorkTaskClass;
import com.liujiaming.work.service.IWorkService;
import com.liujiaming.work.service.IWorkTaskClassService;
import com.liujiaming.work.service.IWorkTaskService;

public class WorkLog {

    private IWorkService workService = ApplicationContextHolder.getBean(IWorkService.class);

    private IWorkTaskService workTaskService = ApplicationContextHolder.getBean(IWorkTaskService.class);

    private IWorkTaskClassService workTaskClassService = ApplicationContextHolder.getBean(IWorkTaskClassService.class);

    public Content deleteWork(Integer workId) {
        Work work = workService.getById(workId);
        return new Content(work.getName(),"删除了项目:"+work.getName());
    }

    public Content leave(Integer workId) {
        Work work = workService.getById(workId);
        return new Content(work.getName(),"退出了项目:"+work.getName());
    }

    public Content removeWorkOwnerUser(RemoveWorkOwnerUserBO workOwnerUserBO) {
        Work work = workService.getById(workOwnerUserBO.getWorkId());
        return new Content(work.getName(),"移出了项目成员:"+ UserCacheUtil.getUserName(workOwnerUserBO.getOwnerUserId()));
    }

    public Content deleteTaskList(DeleteTaskClassBO deleteTaskClassBO) {
        WorkTaskClass taskClass = workTaskClassService.getById(deleteTaskClassBO.getClassId());
        Work work = workService.getById(deleteTaskClassBO.getWorkId());
        return new Content(work.getName(),"删除了分类:"+ taskClass.getName());
    }

    public Content activation(Integer taskId) {
        WorkTask task = workTaskService.getById(taskId);
        return new Content(task.getName(),"激活已归档任务:"+ task.getName());
    }
}
