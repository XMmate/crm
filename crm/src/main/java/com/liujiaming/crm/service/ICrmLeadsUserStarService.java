package com.liujiaming.crm.service;

import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.crm.entity.PO.CrmLeadsUserStar;

import java.util.List;

/**
 * <p>
 * 用户线索标星关系表  服务类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-21
 */
public interface ICrmLeadsUserStarService extends BaseService<CrmLeadsUserStar> {
    /**
     * 判断用户是否关注
     * @param leadsId id
     * @param userId 用户ID
     * @return int
     */
    public Integer isStar(Object leadsId, Long userId);


    /**
     * 用户线索关注列表
     * @param userId
     * @return
     */
    List<Integer> starList(Long userId);
}
