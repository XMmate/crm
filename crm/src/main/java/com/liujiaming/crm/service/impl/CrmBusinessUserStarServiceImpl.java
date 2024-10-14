package com.liujiaming.crm.service.impl;

import com.liujiaming.core.servlet.BaseServiceImpl;
import com.liujiaming.crm.entity.PO.CrmBusinessUserStar;
import com.liujiaming.crm.mapper.CrmBusinessUserStarMapper;
import com.liujiaming.crm.service.ICrmBusinessUserStarService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户商机标星关系表  服务实现类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-27
 */
@Service
public class CrmBusinessUserStarServiceImpl extends BaseServiceImpl<CrmBusinessUserStarMapper, CrmBusinessUserStar> implements ICrmBusinessUserStarService {
    /**
     * 判断用户是否关注
     *
     * @param businessId id
     * @param userId     用户ID
     * @return int
     */
    @Override
    public Integer isStar(Object businessId, Long userId) {
        return lambdaQuery().eq(CrmBusinessUserStar::getUserId, userId).eq(CrmBusinessUserStar::getBusinessId, businessId).last(" limit 1").count();
    }

    @Override
    public List<Integer> starList(Long userId) {
        return lambdaQuery().select(CrmBusinessUserStar::getBusinessId).eq(CrmBusinessUserStar::getUserId,userId).list()
                .stream().map(CrmBusinessUserStar::getBusinessId).collect(Collectors.toList());
    }
}
