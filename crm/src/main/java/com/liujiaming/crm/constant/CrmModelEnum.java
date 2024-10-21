package com.liujiaming.crm.constant;

/**
 * crm模块枚举
 *     @ApiModelProperty(value = "待办事项模块 1今日需联系客户 2分配给我的线索 3分配给我的客户 4待进入公海的客户 5待审核合同 6待审核回款 7待回款提醒 8即将到期的合同 9待回访合同 10待审核发票")
 *     private Integer model;
 */
public enum CrmModelEnum {
    TODAY_CONTACT(1, "今日需联系客户"),
    ASSIGNED_LEAD(2, "分配给我的线索"),
    ASSIGNED_CUSTOMER(3, "分配给我的客户"),
    TO_PUBLIC_SEA(4, "待进入公海的客户"),
    PENDING_CONTRACT_APPROVAL(5, "待审核合同"),
    PENDING_PAYMENT_APPROVAL(6, "待审核回款"),
    PAYMENT_REMINDER(7, "待回款提醒"),
    CONTRACT_EXPIRING_SOON(8, "即将到期的合同"),
    PENDING_CONTRACT_FOLLOWUP(9, "待回访合同"),
    PENDING_INVOICE_APPROVAL(10, "待审核发票");

    private final Integer modelID;
    private final String remarks;

    CrmModelEnum(Integer modelID, String remarks) {
        this.modelID = modelID;
        this.remarks = remarks;
    }

    public Integer getModelID() {
        return modelID;
    }

    public String getRemarks() {
        return remarks;
    }
}

