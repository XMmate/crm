package com.liujiaming.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liujiaming.core.common.Const;
import com.liujiaming.core.servlet.BaseServiceImpl;
import com.liujiaming.crm.entity.PO.CrmBusinessProduct;
import com.liujiaming.crm.mapper.CrmBusinessProductMapper;
import com.liujiaming.crm.service.ICrmBusinessProductService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 商机产品关系表 服务实现类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-27
 */
@Service
public class CrmBusinessProductServiceImpl extends BaseServiceImpl<CrmBusinessProductMapper, CrmBusinessProduct> implements ICrmBusinessProductService {

    /**
     * 通过商机ID删除商机产品关联
     *
     * @param ids ids
     */
    @Override
    public void deleteByBusinessId(Integer... ids) {
        LambdaQueryWrapper<CrmBusinessProduct> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(CrmBusinessProduct::getBusinessId, Arrays.asList(ids));
        remove(wrapper);
    }

    /**
     * 保存商机产品关联
     *
     * @param crmBusinessProductList data
     */
    @Override
    public void save(List<CrmBusinessProduct> crmBusinessProductList) {
        saveBatch(crmBusinessProductList, Const.BATCH_SAVE_SIZE);
    }

    /**
     * 通过产品ID删除商机产品关联
     *
     * @param ids ids
     */
    @Override
    public void deleteByProductId(Integer... ids) {
        LambdaQueryWrapper<CrmBusinessProduct> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(CrmBusinessProduct::getProductId, Arrays.asList(ids));
        remove(wrapper);
    }

    /**
     * 查询商机下产品
     *
     * @param businessId 商机ID
     * @return data
     */
    @Override
    public List<CrmBusinessProduct> queryList(Integer businessId) {
        if (businessId == null) {
            return new ArrayList<>();
        }
        return getBaseMapper().queryList(businessId);
    }
}
