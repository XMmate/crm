package com.liujiaming.crm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.liujiaming.core.servlet.BaseServiceImpl;
import com.liujiaming.crm.entity.PO.CrmExamineLog;
import com.liujiaming.crm.mapper.CrmExamineLogMapper;
import com.liujiaming.crm.service.ICrmExamineLogService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 审核日志表 服务实现类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-28
 */
@Service
public class CrmExamineLogServiceImpl extends BaseServiceImpl<CrmExamineLogMapper, CrmExamineLog> implements ICrmExamineLogService {

    @Override
    public List<JSONObject> queryByRecordId(Integer recordId) {
        return baseMapper.queryByRecordIdAndStatus(recordId);
    }
}
