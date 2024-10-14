package com.liujiaming.crm.service;

import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.crm.common.CrmModel;
import com.liujiaming.crm.entity.PO.CrmContactsData;
import com.liujiaming.crm.entity.VO.CrmModelFiledVO;

import java.util.List;

/**
 * <p>
 * 联系人扩展字段数据表 服务类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-26
 */
public interface ICrmContactsDataService extends BaseService<CrmContactsData> {
    /**
     * 设置用户数据
     * @param crmModel crmModel
     */
    public void setDataByBatchId(CrmModel crmModel);

    /**
     * 保存自定义字段数据
     * @param array data
     * @param batchId batchId
     */
    public void saveData(List<CrmModelFiledVO> array, String batchId);

    /**
     * 通过batchId删除数据
     * @param batchId data
     */
    public void deleteByBatchId(List<String> batchId);
}
