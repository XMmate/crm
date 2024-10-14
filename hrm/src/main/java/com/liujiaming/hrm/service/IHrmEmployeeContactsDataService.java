package com.liujiaming.hrm.service;

import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.hrm.entity.PO.HrmEmployeeContactsData;

/**
 * <p>
 * 客户扩展字段数据表 服务类
 * </p>
 *
 * @author huangmingbo
 * @since 2024-05-12
 */
public interface IHrmEmployeeContactsDataService extends BaseService<HrmEmployeeContactsData> {

    /**
     * 验证字段唯一
     * @param fieldId
     * @param value
     * @param id
     * @return
     */
    Integer verifyUnique(Integer fieldId, String value, Integer id);
}
