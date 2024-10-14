package com.liujiaming.crm.service.impl;

import com.liujiaming.core.servlet.BaseServiceImpl;
import com.liujiaming.crm.entity.PO.CrmContactsUserStar;
import com.liujiaming.crm.mapper.CrmContactsUserStarMapper;
import com.liujiaming.crm.service.ICrmContactsUserStarService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户联系人标星关系表  服务实现类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-26
 */
@Service
public class CrmContactsUserStarServiceImpl extends BaseServiceImpl<CrmContactsUserStarMapper, CrmContactsUserStar> implements ICrmContactsUserStarService {

    /**
     * 判断用户是否关注
     *
     * @param contactsId id
     * @param userId     用户ID
     * @return int
     */
    @Override
    public Integer isStar(Object contactsId, Long userId) {
        return lambdaQuery().eq(CrmContactsUserStar::getUserId, userId).eq(CrmContactsUserStar::getContactsId, contactsId).last(" limit 1").count();
    }

    @Override
    public List<Integer> starList(Long userId) {
        return lambdaQuery().select(CrmContactsUserStar::getContactsId).eq(CrmContactsUserStar::getUserId,userId).list()
                .stream().map(CrmContactsUserStar::getContactsId).collect(Collectors.toList());
    }
}
