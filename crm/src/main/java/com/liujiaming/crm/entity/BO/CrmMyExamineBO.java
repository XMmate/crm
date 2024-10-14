package com.liujiaming.crm.entity.BO;

import com.liujiaming.core.entity.PageEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrmMyExamineBO extends PageEntity {
    private Integer status;

    private Integer categoryType;
}
