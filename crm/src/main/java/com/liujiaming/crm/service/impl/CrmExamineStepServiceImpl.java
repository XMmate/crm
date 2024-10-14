package com.liujiaming.crm.service.impl;

import com.liujiaming.core.feign.admin.service.AdminService;
import com.liujiaming.core.servlet.BaseServiceImpl;
import com.liujiaming.crm.entity.PO.CrmExamineStep;
import com.liujiaming.crm.mapper.CrmExamineStepMapper;
import com.liujiaming.crm.service.ICrmExamineLogService;
import com.liujiaming.crm.service.ICrmExamineService;
import com.liujiaming.crm.service.ICrmExamineStepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 审批步骤表 服务实现类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-28
 */
@Service
public class CrmExamineStepServiceImpl extends BaseServiceImpl<CrmExamineStepMapper, CrmExamineStep> implements ICrmExamineStepService {

    @Autowired
    private ICrmExamineService examineService;

    @Autowired
    private ICrmExamineLogService examineLogService;

    @Autowired
    private AdminService adminService;

}
