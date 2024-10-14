package com.liujiaming.oa.service.impl;

import com.liujiaming.core.servlet.BaseServiceImpl;
import com.liujiaming.oa.entity.PO.OaExamineStep;
import com.liujiaming.oa.mapper.OaExamineStepMapper;
import com.liujiaming.oa.service.IOaExamineStepService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 审批步骤表 服务实现类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-15
 */
@Service
public class OaExamineStepServiceImpl extends BaseServiceImpl<OaExamineStepMapper, OaExamineStep> implements IOaExamineStepService {



    @Override
    public OaExamineStep queryExamineStepByNextExamineIdOrderByStepId(Integer categoryId, Integer examineStepId) {
        return getBaseMapper().queryExamineStepByNextExamineIdOrderByStepId(categoryId,examineStepId);
    }
}
