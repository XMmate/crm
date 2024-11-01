package com.liujiaming.crm.service;

import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.crm.common.CrmModel;
import com.liujiaming.crm.entity.PO.CrmBusinessData;
import com.liujiaming.crm.entity.VO.CrmModelFiledVO;

import java.util.List;

/**
 * <p>
 * 商机扩展字段数据表 服务类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-27
 */
public interface ICrmBusinessDataService extends BaseService<CrmBusinessData> {
    /**
     *根据BatchId()往CrmModel填充扩展字段表里面的数据，
     *  CrmModel是个扩展Map,可以表示任何对象
     * @param crmModel
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
