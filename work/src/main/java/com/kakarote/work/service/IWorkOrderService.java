package com.kakarote.work.service;

import com.kakarote.core.servlet.BaseService;
import com.kakarote.work.entity.PO.WorkOrder;

import java.util.List;

/**
 * <p>
 * 项目排序表 服务类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-15
 */
public interface IWorkOrderService extends BaseService<WorkOrder> {
    /**
     * 项目排序
     * @param workIdList
     */
    public void updateWorkOrder(List<Integer> workIdList);
}
