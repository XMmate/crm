package com.liujiaming.crm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liujiaming.core.common.Const;
import com.liujiaming.core.field.service.FieldService;
import com.liujiaming.core.servlet.BaseServiceImpl;
import com.liujiaming.crm.common.CrmModel;
import com.liujiaming.crm.entity.PO.CrmReceivablesData;
import com.liujiaming.crm.entity.VO.CrmModelFiledVO;
import com.liujiaming.crm.mapper.CrmReceivablesDataMapper;
import com.liujiaming.crm.service.ICrmReceivablesDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 回款自定义字段存值表 服务实现类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-28
 */
@Service
public class CrmReceivablesDataServiceImpl extends BaseServiceImpl<CrmReceivablesDataMapper, CrmReceivablesData> implements ICrmReceivablesDataService {

    @Autowired
    private FieldService fieldService;

    /**
     * 设置用户数据
     *
     * @param crmModel crmModel
     */
    @Override
    public void setDataByBatchId(CrmModel crmModel) {
        List<CrmReceivablesData> receivablesDataList = query().eq("batch_id", crmModel.getBatchId()).list();
        receivablesDataList.forEach(obj -> {
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
        List<CrmReceivablesData> receivablesDataList = new ArrayList<>();
        remove(new LambdaQueryWrapper<CrmReceivablesData>().eq(CrmReceivablesData::getBatchId, batchId));
        Date date = new Date();
        for (CrmModelFiledVO obj : array) {
            CrmReceivablesData receivablesData = BeanUtil.copyProperties(obj, CrmReceivablesData.class);
            receivablesData.setValue(fieldService.convertObjectValueToString(obj.getType(),obj.getValue(),receivablesData.getValue()));
            receivablesData.setName(obj.getFieldName());
            receivablesData.setCreateTime(date);
            receivablesData.setBatchId(batchId);
            receivablesDataList.add(receivablesData);
        }
        saveBatch(receivablesDataList, Const.BATCH_SAVE_SIZE);
    }

    /**
     * 通过batchId删除数据
     *
     * @param batchId data
     */
    @Override
    public void deleteByBatchId(List<String> batchId) {
        LambdaQueryWrapper<CrmReceivablesData> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(CrmReceivablesData::getBatchId,batchId);
        remove(wrapper);
    }
}
