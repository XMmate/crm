package com.liujiaming.crm.entity.BO;

import com.liujiaming.core.feign.crm.entity.BiParams;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author liujiaming
 * @date 2024/11/23
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CrmSearchParamsBO extends BiParams {
    private CrmSearchBO.Search entity;
}
