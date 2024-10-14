package com.liujiaming.crm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liujiaming.core.common.Const;
import com.liujiaming.core.field.service.FieldService;
import com.liujiaming.core.servlet.BaseServiceImpl;
import com.liujiaming.crm.common.CrmModel;
import com.liujiaming.crm.entity.PO.CrmCustomerData;
import com.liujiaming.crm.entity.VO.CrmModelFiledVO;
import com.liujiaming.crm.mapper.CrmCustomerDataMapper;
import com.liujiaming.crm.service.ICrmCustomerDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 客户扩展字段数据表 服务实现类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-29
 */
@Service
public class CrmCustomerDataServiceImpl extends BaseServiceImpl<CrmCustomerDataMapper, CrmCustomerData> implements ICrmCustomerDataService {

    @Autowired
    private FieldService fieldService;

    /**
     * 设置用户数据
     *
     * @param crmModel crmModel
     */
    @Override
    public void setDataByBatchId(CrmModel crmModel) {
        List<CrmCustomerData> customerDataList = query().eq("batch_id", crmModel.getBatchId()).list();
        customerDataList.forEach(obj -> {
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
        List<CrmCustomerData> customerDataList = new ArrayList<>();
        remove(new LambdaQueryWrapper<CrmCustomerData>().eq(CrmCustomerData::getBatchId, batchId));
        Date date = new Date();
        for (CrmModelFiledVO obj : array) {
            CrmCustomerData customerData = BeanUtil.copyProperties(obj, CrmCustomerData.class);
            customerData.setValue(fieldService.convertObjectValueToString(obj.getType(),obj.getValue(),customerData.getValue()));
            customerData.setName(obj.getFieldName());
            customerData.setCreateTime(date);
            customerData.setBatchId(batchId);
            customerDataList.add(customerData);
        }
        saveBatch(customerDataList, Const.BATCH_SAVE_SIZE);
    }

    /**
     * 通过batchId删除数据
     *
     * @param batchId data
     */
    @Override
    public void deleteByBatchId(List<String> batchId) {
        LambdaQueryWrapper<CrmCustomerData> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(CrmCustomerData::getBatchId, batchId);
        remove(wrapper);
    }
}
