package com.liujiaming.work.common.log;
import com.liujiaming.core.common.log.Content;
import com.liujiaming.core.servlet.ApplicationContextHolder;
import com.liujiaming.work.entity.PO.WorkTaskLabel;
import com.liujiaming.work.service.IWorkTaskLabelService;

public class WorkTaskLabelLog {
    private IWorkTaskLabelService workTaskLabelService = ApplicationContextHolder.getBean(IWorkTaskLabelService.class);

    public Content deleteLabel(Integer labelId){
        WorkTaskLabel taskLabel = workTaskLabelService.getById(labelId);
        return new Content(taskLabel.getName(),"删除了标签"+taskLabel.getName());
    }
}
