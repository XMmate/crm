package com.liujiaming.crm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liujiaming.core.common.Const;
import com.liujiaming.core.field.service.FieldService;
import com.liujiaming.core.servlet.BaseServiceImpl;
import com.liujiaming.crm.common.CrmModel;
import com.liujiaming.crm.entity.PO.CrmProductData;
import com.liujiaming.crm.entity.VO.CrmModelFiledVO;
import com.liujiaming.crm.mapper.CrmProductDataMapper;
import com.liujiaming.crm.service.ICrmProductDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 产品自定义字段存值表 服务实现类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-26
 */
@Service
public class CrmProductDataServiceImpl extends BaseServiceImpl<CrmProductDataMapper, CrmProductData> implements ICrmProductDataService {

    @Autowired
    private FieldService fieldService;

    /**
     * 设置用户数据
     *
     * @param crmModel crmModel
     */
    @Override
    public void setDataByBatchId(CrmModel crmModel) {
        List<CrmProductData> productDataList = query().eq("batch_id", crmModel.getBatchId()).list();
        productDataList.forEach(obj -> {
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
        List<CrmProductData> productDataList = new ArrayList<>();
        remove(new LambdaQueryWrapper<CrmProductData>().eq(CrmProductData::getBatchId, batchId));
        Date date = new Date();
        for (CrmModelFiledVO obj : array) {
            CrmProductData productData = BeanUtil.copyProperties(obj, CrmProductData.class);
            productData.setValue(fieldService.convertObjectValueToString(obj.getType(),obj.getValue(),productData.getValue()));
            productData.setName(obj.getFieldName());
            productData.setCreateTime(date);
            productData.setBatchId(batchId);
            productDataList.add(productData);
        }
        saveBatch(productDataList, Const.BATCH_SAVE_SIZE);
    }

    /**
     * 通过batchId删除数据
     *
     * @param batchId data
     */
    @Override
    public void deleteByBatchId(List<String> batchId) {
        lambdaUpdate().in(CrmProductData::getBatchId,batchId).remove();
    }

}
