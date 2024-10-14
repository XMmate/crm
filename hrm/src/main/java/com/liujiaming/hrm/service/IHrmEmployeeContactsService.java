package com.liujiaming.hrm.service;

import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.hrm.entity.PO.HrmEmployeeContacts;

/**
 * <p>
 * 员工联系人 服务类
 * </p>
 *
 * @author huangmingbo
 * @since 2024-05-12
 */
public interface IHrmEmployeeContactsService extends BaseService<HrmEmployeeContacts> {

    /**
     * 校验字段唯一
     * @param fieldName
     * @param value
     * @param contactsId
     * @return
     */
    Integer verifyUnique(String fieldName, String value, Integer contactsId);
}
