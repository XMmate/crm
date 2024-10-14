package com.liujiaming.admin.service;

import com.liujiaming.admin.entity.PO.AdminUserHisTable;
import com.liujiaming.core.servlet.BaseService;

import java.util.List;

/**
 * <p>
 * 授权坐席 服务类
 * </p>
 *
 * @author liujiaming
 * @since 2024-04-27
 */
public interface IAdminUserHisTableService extends BaseService<AdminUserHisTable> {

    /**
     * 员工坐席授权
     */
    boolean authorize(List<Long> userIds, Integer status,Integer hisUse);

    /**
     * 判断用户是否为坐席
     * @return
     */
    public Integer checkAuth();
}
