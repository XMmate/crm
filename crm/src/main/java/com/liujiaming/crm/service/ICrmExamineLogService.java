package com.liujiaming.crm.service;

import com.alibaba.fastjson.JSONObject;
import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.crm.entity.PO.CrmExamineLog;

import java.util.List;

/**
 * <p>
 * 审核日志表 服务类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-28
 */
public interface ICrmExamineLogService extends BaseService<CrmExamineLog> {

    List<JSONObject> queryByRecordId(Integer recordId);
}
