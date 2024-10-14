package com.liujiaming.crm.service;

import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.crm.entity.PO.CrmBusinessUserStar;

import java.util.List;

/**
 * <p>
 * 用户商机标星关系表  服务类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-27
 */
public interface ICrmBusinessUserStarService extends BaseService<CrmBusinessUserStar> {
    /**
     * 判断用户是否关注
     * @param businessId id
     * @param userId 用户ID
     * @return int
     */
    public Integer isStar(Object businessId, Long userId);

    List<Integer> starList(Long userId);
}
