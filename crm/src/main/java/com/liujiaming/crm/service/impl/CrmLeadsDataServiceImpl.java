package com.liujiaming.crm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liujiaming.core.common.Const;
import com.liujiaming.core.field.service.FieldService;
import com.liujiaming.core.servlet.BaseServiceImpl;
import com.liujiaming.crm.common.CrmModel;
import com.liujiaming.crm.entity.PO.CrmLeadsData;
import com.liujiaming.crm.entity.VO.CrmModelFiledVO;
import com.liujiaming.crm.mapper.CrmLeadsDataMapper;
import com.liujiaming.crm.service.ICrmLeadsDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 线索自定义字段存值表 服务实现类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-21
 */
@Service
public class CrmLeadsDataServiceImpl extends BaseServiceImpl<CrmLeadsDataMapper, CrmLeadsData> implements ICrmLeadsDataService {

    @Autowired
    private FieldService fieldService;

    /**
     * 设置用户数据
     *
     * @param crmModel crmModel
     */
    @Override
    public void setDataByBatchId(CrmModel crmModel) {
        List<CrmLeadsData> leadsDataList = query().eq("batch_id", crmModel.getBatchId()).list();
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
        List<CrmLeadsData> leadsDataList = new ArrayList<>();
        remove(new LambdaQueryWrapper<CrmLeadsData>().eq(CrmLeadsData::getBatchId, batchId));
        Date date = new Date();
        for (CrmModelFiledVO obj : array) {
            CrmLeadsData leadsData = BeanUtil.copyProperties(obj, CrmLeadsData.class);
            leadsData.setValue(fieldService.convertObjectValueToString(obj.getType(),obj.getValue(),leadsData.getValue()));
            leadsData.setName(obj.getFieldName());
            leadsData.setCreateTime(date);
            leadsData.setBatchId(batchId);
            leadsDataList.add(leadsData);
        }
        saveBatch(leadsDataList, Const.BATCH_SAVE_SIZE);
    }

    /**
     * 通过batchId删除数据
     *
     * @param batchId data
     */
    @Override
    public void deleteByBatchId(List<String> batchId) {
        LambdaQueryWrapper<CrmLeadsData> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(CrmLeadsData::getBatchId,batchId);
        remove(wrapper);
    }

}
