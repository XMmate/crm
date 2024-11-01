package com.liujiaming.crm.service;

import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.crm.entity.PO.CrmFieldSort;
import com.liujiaming.crm.entity.VO.CrmFieldSortVO;

import java.util.List;

/**
 * <p>
 * 字段排序表 服务类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-19
 */
public interface ICrmFieldSortService extends BaseService<CrmFieldSort> {

    /**
     * 查询模块字段列表
     * @param label label
     * @return data
     */
    public List<CrmFieldSortVO> queryListHead(Integer label);

    /**
     * 查询模块全部字段排序
     * @param label label
     * @param userId 用户ID
     * @return data
     */
    public List<CrmFieldSort> queryAllFieldSortList(Integer label,Long userId);
}
