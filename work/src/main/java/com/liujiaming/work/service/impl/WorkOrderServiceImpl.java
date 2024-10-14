package com.liujiaming.work.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liujiaming.core.servlet.BaseServiceImpl;
import com.liujiaming.core.utils.UserUtil;
import com.liujiaming.work.entity.PO.WorkOrder;
import com.liujiaming.work.mapper.WorkOrderMapper;
import com.liujiaming.work.service.IWorkOrderService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 项目排序表 服务实现类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-15
 */
@Service
public class WorkOrderServiceImpl extends BaseServiceImpl<WorkOrderMapper, WorkOrder> implements IWorkOrderService {


    /**
     * 项目排序
     * @param workIdList
     */
    @Override
    public void updateWorkOrder(List<Integer> workIdList) {
        Long userId = UserUtil.getUserId();
        remove(new QueryWrapper<WorkOrder>().eq("user_id",userId));
        for (int i = 0; i < workIdList.size(); i++) {
            WorkOrder workOrder = new WorkOrder();
            workOrder.setWorkId(workIdList.get(i));
            workOrder.setUserId(userId);
            workOrder.setOrderNum(i + 1);
            save(workOrder);
        }
    }
}
