package com.liujiaming.crm.service.impl;

import cn.hutool.core.lang.Dict;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liujiaming.core.feign.admin.entity.AdminConfig;
import com.liujiaming.core.feign.admin.service.AdminService;
import com.liujiaming.core.servlet.ApplicationContextHolder;
import com.liujiaming.core.servlet.BaseServiceImpl;
import com.liujiaming.crm.constant.CrmTypeEnum;
import com.liujiaming.crm.entity.PO.CrmActionRecord;
import com.liujiaming.crm.entity.VO.CrmActionRecordVO;
import com.liujiaming.crm.entity.VO.CrmModelFiledVO;
import com.liujiaming.crm.mapper.CrmActionRecordMapper;
import com.liujiaming.crm.service.ICrmActionRecordService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 字段操作记录表 服务实现类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-25
 */
@Service
public class CrmActionRecordServiceImpl extends BaseServiceImpl<CrmActionRecordMapper, CrmActionRecord> implements ICrmActionRecordService {

    /**
     * 删除字段记录类型
     *
     * @param crmTypeEnum 类型
     * @param ids     ids
     */
    @Override
    public void deleteActionRecord(CrmTypeEnum crmTypeEnum, List<Integer> ids) {
        LambdaQueryWrapper<CrmActionRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CrmActionRecord::getTypes, crmTypeEnum.getType());
        wrapper.in(CrmActionRecord::getActionId, ids);
        remove(wrapper);
    }

    /**
     * 查询跟进方式列表
     *
     * @return data
     */
    @Override
    public List<String> queryRecordOptions() {
        AdminService bean = ApplicationContextHolder.getBean(AdminService.class);
        List<AdminConfig> option = bean.queryConfigByName("followRecordOption").getData();
        return option.stream().map(AdminConfig::getValue).collect(Collectors.toList());
    }

    @Override
    public List<CrmActionRecordVO> queryRecordList(Integer actionId, Integer crmTypes) {
        List<CrmActionRecordVO> recordList = getBaseMapper().queryRecordList(actionId, crmTypes);
        recordList.forEach(record -> {
            try {
                List<String> list = JSON.parseArray((String) record.getContent(), String.class);
                record.setContent(list);
            } catch (Exception e) {
                List<String> list = new ArrayList<>();
                list.add((String) record.getContent());
                record.setContent(list);
            }
        });
        return recordList;
    }

    @Override
    public List<CrmModelFiledVO> queryFieldValue(Dict kv) {
        return getBaseMapper().queryFieldValue(kv);
    }
}
