package com.liujiaming.crm.service;

import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.crm.constant.CrmBackLogEnum;
import com.liujiaming.crm.constant.CrmTypeEnum;
import com.liujiaming.crm.entity.PO.CrmBackLogDeal;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-23
 */
public interface ICrmBackLogDealService extends BaseService<CrmBackLogDeal> {
    /**
     * 更新对应的待办事项提醒
     *
     * @param userId         用户ID
     * @param crmTypeEnum        类型
     * @param crmBackLogEnum 模块
     * @param typeId         类型ID
     */
    public void deleteByType(Long userId, CrmTypeEnum crmTypeEnum, CrmBackLogEnum crmBackLogEnum, Integer typeId);

    /**
     * 更新对应的待办事项提醒
     *
     * @param userId         用户ID
     * @param crmTypeEnum        类型
     * @param crmBackLogEnum 模块
     * @param typeId         类型ID
     */
    public void deleteByTypes(Long userId, CrmTypeEnum crmTypeEnum,Integer typeId, CrmBackLogEnum... crmBackLogEnum);

    /**
     * 查询对应的主键ID
     * @param model model
     * @param crmType 类型
     * @param userId 当前用户ID
     * @return data
     */
    public List<String> queryTypeId(Integer model, Integer crmType, Long userId);
}
