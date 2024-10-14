package com.liujiaming.crm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liujiaming.core.common.Const;
import com.liujiaming.core.field.service.FieldService;
import com.liujiaming.core.servlet.BaseServiceImpl;
import com.liujiaming.crm.common.CrmModel;
import com.liujiaming.crm.entity.PO.CrmReturnVisitData;
import com.liujiaming.crm.entity.VO.CrmModelFiledVO;
import com.liujiaming.crm.mapper.CrmReturnVisitDataMapper;
import com.liujiaming.crm.service.ICrmReturnVisitDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liujiaming
 * @since 2024-07-06
 */
@Service
public class CrmReturnVisitDataServiceImpl extends BaseServiceImpl<CrmReturnVisitDataMapper, CrmReturnVisitData> implements ICrmReturnVisitDataService {

    @Autowired
    private FieldService fieldService;

    /**
     * 保存自定义字段数据
     * @param array data
     * @param batchId batchId
     */
    @Override
    public void saveData(List<CrmModelFiledVO> array, String batchId) {
        List<CrmReturnVisitData> crmReturnVisitDataList = new ArrayList<>();
        remove(new LambdaQueryWrapper<CrmReturnVisitData>().eq(CrmReturnVisitData::getBatchId, batchId));
        Date date = new Date();
        for (CrmModelFiledVO obj : array) {
            CrmReturnVisitData crmCustomerData = BeanUtil.copyProperties(obj, CrmReturnVisitData.class);
            crmCustomerData.setValue(fieldService.convertObjectValueToString(obj.getType(),obj.getValue(),crmCustomerData.getValue()));
            crmCustomerData.setName(obj.getFieldName());
            crmCustomerData.setCreateTime(date);
            crmCustomerData.setBatchId(batchId);
            crmReturnVisitDataList.add(crmCustomerData);
        }
        saveBatch(crmReturnVisitDataList, Const.BATCH_SAVE_SIZE);
    }

    @Override
    public void setDataByBatchId(CrmModel crmModel) {
        List<CrmReturnVisitData> crmReturnVisitDataList = query().eq("batch_id", crmModel.getBatchId()).list();
        crmReturnVisitDataList.forEach(obj -> {
            crmModel.put(obj.getName(), obj.getValue());
        });
    }

    @Override
    public void deleteByBatchId(List<String> batchList) {

    }
}
