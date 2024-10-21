package com.liujiaming.crm.entity.BO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EsMessageBO {
    private CrmBusinessSaveBO crmModel;
    private Integer businessId;
    private boolean isExcel;
}
