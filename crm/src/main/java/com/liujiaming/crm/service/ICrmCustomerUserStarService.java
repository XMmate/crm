package com.liujiaming.crm.service;

import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.crm.entity.PO.CrmCustomerUserStar;

import java.util.List;

/**
 * <p>
 * 用户客户标星关系表  服务类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-29
 */
public interface ICrmCustomerUserStarService extends BaseService<CrmCustomerUserStar> {
    /**
     * 判断用户是否关注
     * @param customerId id
     * @param userId 用户ID
     * @return int
     */
    public Integer isStar(Object customerId, Long userId);

    List<Integer> starList(Long userId);
}
