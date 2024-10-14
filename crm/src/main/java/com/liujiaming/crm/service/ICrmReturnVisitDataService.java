package com.liujiaming.crm.service;

import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.crm.common.CrmModel;
import com.liujiaming.crm.entity.PO.CrmReturnVisitData;
import com.liujiaming.crm.entity.VO.CrmModelFiledVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liujiaming
 * @since 2024-07-06
 */
public interface ICrmReturnVisitDataService extends BaseService<CrmReturnVisitData> {
    /**
     * 保存自定义字段数据
     * @param array array
     * @param batchId batchId
     */
    void saveData(List<CrmModelFiledVO> array, String batchId);

    void setDataByBatchId(CrmModel crmModel);

    void deleteByBatchId(List<String> batchList);
}
