package com.liujiaming.crm.service;

import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.crm.entity.PO.CrmBusinessStatus;

/**
 * <p>
 * 商机状态 服务类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-27
 */
public interface ICrmBusinessStatusService extends BaseService<CrmBusinessStatus> {

    String getBusinessStatusName(int statusId);
}
