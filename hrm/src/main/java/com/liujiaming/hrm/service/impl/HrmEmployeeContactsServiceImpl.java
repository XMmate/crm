package com.liujiaming.hrm.service.impl;

import com.liujiaming.core.servlet.BaseServiceImpl;
import com.liujiaming.hrm.entity.PO.HrmEmployeeContacts;
import com.liujiaming.hrm.mapper.HrmEmployeeContactsMapper;
import com.liujiaming.hrm.service.IHrmEmployeeContactsService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 员工联系人 服务实现类
 * </p>
 *
 * @author huangmingbo
 * @since 2024-05-12
 */
@Service
public class HrmEmployeeContactsServiceImpl extends BaseServiceImpl<HrmEmployeeContactsMapper, HrmEmployeeContacts> implements IHrmEmployeeContactsService {

    @Override
    public Integer verifyUnique(String fieldName, String value, Integer contactsId) {
        return getBaseMapper().verifyUnique(fieldName,value,contactsId);
    }
}
