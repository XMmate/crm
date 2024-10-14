package com.liujiaming.crm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liujiaming.core.common.Const;
import com.liujiaming.core.field.service.FieldService;
import com.liujiaming.core.servlet.BaseServiceImpl;
import com.liujiaming.crm.common.CrmModel;
import com.liujiaming.crm.entity.PO.CrmInvoiceData;
import com.liujiaming.crm.entity.VO.CrmModelFiledVO;
import com.liujiaming.crm.mapper.CrmInvoiceDataMapper;
import com.liujiaming.crm.service.ICrmInvoiceDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 合同扩展字段数据表 服务实现类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-27
 */
@Service
public class CrmInvoiceDataServiceImpl extends BaseServiceImpl<CrmInvoiceDataMapper, CrmInvoiceData> implements ICrmInvoiceDataService {

    @Autowired
    private FieldService fieldService;

    /**
     * 设置用户数据
     *
     * @param crmModel crmModel
     */
    @Override
    public void setDataByBatchId(CrmModel crmModel) {
        List<CrmInvoiceData> crmInvoiceDataList = query().eq("batch_id", crmModel.getBatchId()).list();
        crmInvoiceDataList.forEach(obj -> {
            crmModel.put(obj.getName(), obj.getValue());
        });
    }

    /**
     * 保存自定义字段数据
     *
     * @param array data
     */
    @Override
    public void saveData(List<CrmModelFiledVO> array, String batchId) {
        List<CrmInvoiceData> invoiceDataList = new ArrayList<>();
        remove(new LambdaQueryWrapper<CrmInvoiceData>().eq(CrmInvoiceData::getBatchId, batchId));
        Date date = new Date();
        for (CrmModelFiledVO obj : array) {
            CrmInvoiceData crmInvoiceData = BeanUtil.copyProperties(obj, CrmInvoiceData.class);
            crmInvoiceData.setValue(fieldService.convertObjectValueToString(obj.getType(),obj.getValue(),crmInvoiceData.getValue()));
            crmInvoiceData.setName(obj.getFieldName());
            crmInvoiceData.setCreateTime(date);
            crmInvoiceData.setBatchId(batchId);
            invoiceDataList.add(crmInvoiceData);
        }
        saveBatch(invoiceDataList, Const.BATCH_SAVE_SIZE);
    }

    /**
     * 通过batchId删除数据
     *
     * @param batchId data
     */
    @Override
    public void deleteByBatchId(List<String> batchId) {
        LambdaQueryWrapper<CrmInvoiceData> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(CrmInvoiceData::getBatchId,batchId);
        remove(wrapper);
    }


}
