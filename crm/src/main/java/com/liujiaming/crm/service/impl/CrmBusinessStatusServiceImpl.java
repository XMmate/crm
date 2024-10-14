package com.liujiaming.crm.service.impl;

import com.liujiaming.core.servlet.BaseServiceImpl;
import com.liujiaming.crm.entity.PO.CrmBusinessStatus;
import com.liujiaming.crm.mapper.CrmBusinessStatusMapper;
import com.liujiaming.crm.service.ICrmBusinessStatusService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商机状态 服务实现类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-27
 */
@Service
public class CrmBusinessStatusServiceImpl extends BaseServiceImpl<CrmBusinessStatusMapper, CrmBusinessStatus> implements ICrmBusinessStatusService {

    @Override
    public String getBusinessStatusName(int statusId) {
        return lambdaQuery().select(CrmBusinessStatus::getName).eq(CrmBusinessStatus::getStatusId,statusId).oneOpt()
                .map(CrmBusinessStatus::getName).orElse("");
    }
}
