package com.liujiaming.crm.constant;

import com.liujiaming.core.common.SystemCodeEnum;
import com.liujiaming.core.exception.CrmException;

public enum CrmBackLogModelEnum {
    TODAY_CUSTOMER(1,"今日需联系客户"),
    FOLLOW_LEADS(2,"分配给我的线索"),
    FOLLOW_CUSTOMER(3,"分配给我的客户"),
    TO_ENTER_CUSTOMER_POOL(4,"待进入公海的客户"),
    CHECK_CONTRACT(5,"待审核合同"),
    CHECK_RECEIVABLES(6,"待审核回款"),
    REMIND_RECEIVABLES_PLAN(7,"待回款提醒 "),
    END_CONTRACT(8,"即将到期的合同"),
    REMIND_RETURN_VISIT_CONTRACT(9,"待回访合同"),
    CHECK_INVOICE(10,"待审核发票"),
    TODAY_LEADS(11,"今日需联系线索"),
    TODAY_BUSINESS(12,"今日需联系商机"),
    ;
    private Integer modelId;
    private String remarks;

    CrmBackLogModelEnum(Integer modelId,String remarks){
        this.modelId=modelId;
        this.remarks=remarks;
    }
    public Integer getModelID(){
        return modelId;
    }
    private String getRemarks(){
        return remarks;
    }
    public static CrmBackLogModelEnum parse(int modelId) {
        for ( CrmBackLogModelEnum crmBackLogModelEnum :  CrmBackLogModelEnum.values()) {
            if (crmBackLogModelEnum.getModelID() == modelId) {
                return crmBackLogModelEnum;
            }
        }
        throw new CrmException(SystemCodeEnum.SYSTEM_NO_VALID);
    }

}
