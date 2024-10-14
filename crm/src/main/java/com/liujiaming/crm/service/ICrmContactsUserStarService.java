package com.liujiaming.crm.service;

import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.crm.entity.PO.CrmContactsUserStar;

import java.util.List;

/**
 * <p>
 * 用户联系人标星关系表  服务类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-26
 */
public interface ICrmContactsUserStarService extends BaseService<CrmContactsUserStar> {
    /**
     * 判断用户是否关注
     * @param contactsId id
     * @param userId 用户ID
     * @return int
     */
    public Integer isStar(Object contactsId, Long userId);

    List<Integer> starList(Long userId);
}
