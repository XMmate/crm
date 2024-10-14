package com.liujiaming.crm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liujiaming.core.common.Const;
import com.liujiaming.core.field.service.FieldService;
import com.liujiaming.core.servlet.BaseServiceImpl;
import com.liujiaming.crm.common.CrmModel;
import com.liujiaming.crm.entity.PO.CrmBusinessData;
import com.liujiaming.crm.entity.VO.CrmModelFiledVO;
import com.liujiaming.crm.mapper.CrmBusinessDataMapper;
import com.liujiaming.crm.service.ICrmBusinessDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 商机扩展字段数据表 服务实现类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-27
 */
@Service
public class CrmBusinessDataServiceImpl extends BaseServiceImpl<CrmBusinessDataMapper, CrmBusinessData> implements ICrmBusinessDataService {

    @Autowired
    private FieldService fieldService;

    /**
     * 设置用户数据
     *
     * @param crmModel crmModel
     */
    @Override
    public void setDataByBatchId(CrmModel crmModel) {
        List<CrmBusinessData> businessDataList = query().eq("batch_id", crmModel.getBatchId()).list();
        businessDataList.forEach(obj -> {
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
        List<CrmBusinessData> businessDataList = new ArrayList<>();
        remove(new LambdaQueryWrapper<CrmBusinessData>().eq(CrmBusinessData::getBatchId, batchId));
        Date date = new Date();
        for (CrmModelFiledVO obj : array) {
            CrmBusinessData businessData = BeanUtil.copyProperties(obj, CrmBusinessData.class);
            businessData.setValue(fieldService.convertObjectValueToString(obj.getType(),obj.getValue(),businessData.getValue()));
            businessData.setName(obj.getFieldName());
            businessData.setCreateTime(date);
            businessData.setBatchId(batchId);
            businessDataList.add(businessData);
        }
        saveBatch(businessDataList, Const.BATCH_SAVE_SIZE);
    }

    /**
     * 通过batchId删除数据
     *
     * @param batchId data
     */
    @Override
    public void deleteByBatchId(List<String> batchId) {
        LambdaQueryWrapper<CrmBusinessData> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(CrmBusinessData::getBatchId,batchId);
        remove(wrapper);
    }
}
