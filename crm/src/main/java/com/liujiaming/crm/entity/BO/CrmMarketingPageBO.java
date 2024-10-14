package com.liujiaming.crm.entity.BO;

import com.liujiaming.core.entity.PageEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrmMarketingPageBO extends PageEntity {

    private Integer crmType;

    private String search;

    private Integer timeType;

    private Long userId;
}
