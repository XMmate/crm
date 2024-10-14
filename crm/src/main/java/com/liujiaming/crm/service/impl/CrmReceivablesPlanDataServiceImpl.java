package com.liujiaming.crm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liujiaming.core.common.Const;
import com.liujiaming.core.field.service.FieldService;
import com.liujiaming.core.servlet.BaseServiceImpl;
import com.liujiaming.crm.common.CrmModel;
import com.liujiaming.crm.entity.PO.CrmReceivablesPlanData;
import com.liujiaming.crm.entity.VO.CrmModelFiledVO;
import com.liujiaming.crm.mapper.CrmReceivablesPlanDataMapper;
import com.liujiaming.crm.service.ICrmReceivablesPlanDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 回款计划自定义字段存值表 服务实现类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-21
 */
@Service
public class CrmReceivablesPlanDataServiceImpl extends BaseServiceImpl<CrmReceivablesPlanDataMapper, CrmReceivablesPlanData> implements ICrmReceivablesPlanDataService {

    @Autowired
    private FieldService fieldService;

    /**
     * 设置用户数据
     *
     * @param crmModel crmModel
     */
    @Override
    public void setDataByBatchId(CrmModel crmModel) {
        List<CrmReceivablesPlanData> leadsDataList = query().eq("batch_id", crmModel.getBatchId()).list();
        leadsDataList.forEach(obj -> {
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
        List<CrmReceivablesPlanData> receivablesPlanDataList = new ArrayList<>();
        remove(new LambdaQueryWrapper<CrmReceivablesPlanData>().eq(CrmReceivablesPlanData::getBatchId, batchId));
        Date date = new Date();
        for (CrmModelFiledVO obj : array) {
            CrmReceivablesPlanData receivablesPlanData = BeanUtil.copyProperties(obj, CrmReceivablesPlanData.class);
            receivablesPlanData.setValue(fieldService.convertObjectValueToString(obj.getType(),obj.getValue(),receivablesPlanData.getValue()));
            receivablesPlanData.setName(obj.getFieldName());
            receivablesPlanData.setCreateTime(date);
            receivablesPlanData.setBatchId(batchId);
            receivablesPlanDataList.add(receivablesPlanData);
        }
        saveBatch(receivablesPlanDataList, Const.BATCH_SAVE_SIZE);
    }

    /**
     * 通过batchId删除数据
     *
     * @param batchId data
     */
    @Override
    public void deleteByBatchId(List<String> batchId) {
        LambdaQueryWrapper<CrmReceivablesPlanData> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(CrmReceivablesPlanData::getBatchId,batchId);
        remove(wrapper);
    }

}
