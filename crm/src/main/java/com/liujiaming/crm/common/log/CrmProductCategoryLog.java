package com.liujiaming.crm.common.log;

import com.liujiaming.core.common.log.BehaviorEnum;
import com.liujiaming.core.common.log.Content;
import com.liujiaming.core.servlet.ApplicationContextHolder;
import com.liujiaming.crm.service.ICrmProductCategoryService;

public class CrmProductCategoryLog {

    private ICrmProductCategoryService crmProductCategoryService = ApplicationContextHolder.getBean(ICrmProductCategoryService.class);

    public Content deleteById(Integer id) {
        String productCategoryName = crmProductCategoryService.getProductCategoryName(id);
        return new Content(productCategoryName,"删除了产品类型:"+productCategoryName, BehaviorEnum.DELETE);
    }
}
