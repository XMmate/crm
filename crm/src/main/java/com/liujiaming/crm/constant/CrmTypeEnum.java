package com.liujiaming.crm.constant;

import cn.hutool.core.util.StrUtil;

/**
 * @author liujiaming
 * crm类型枚举
 */

public enum CrmTypeEnum {
    /**
     * 线索
     */
    LEADS(1, "线索"),
    /**
     * 客户
     */
    CUSTOMER(2, "客户"),
    /**
     * 联系人
     */
    CONTACTS(3, "联系人"),
    /**
     * 产品
     */
    PRODUCT(4, "产品"),
    /**
     * 商机
     */
    BUSINESS(5, "商机"),
    /**
     * 合同
     */
    CONTRACT(6, "合同"),
    /**
     * 回款
     */
    RECEIVABLES(7, "回款"),
    /**
     * 回款计划
     */
    RECEIVABLES_PLAN(8, "回款计划"),
    /**
     * 公海
     */
    CUSTOMER_POOL(9, "公海"),
    /**
     * 市场活动
     */
    MARKETING(10, "市场活动"),
    /**
     * 回访
     */
    RETURN_VISIT(17, "客户回访"),
    /**
     * 发票
     */
    INVOICE(18, "发票"),

    /**
     * NULL
     */
    NULL(0,"");

    CrmTypeEnum(Integer type, String remarks) {
        this.type = type;
        this.remarks = remarks;
    }

    private Integer type;
    private String remarks;

    public String getRemarks() {
        return remarks;
    }

    public Integer getType() {
        return type;
    }

    public static CrmTypeEnum parse(Integer type) {
        for (CrmTypeEnum crmTypeEnum : CrmTypeEnum.values()) {
            if (crmTypeEnum.getType().equals(type)) {
                return crmTypeEnum;
            }
        }
        return NULL;
    }

    public static CrmTypeEnum parse(String name) {
        for (CrmTypeEnum crmTypeEnum : CrmTypeEnum.values()) {
            if (crmTypeEnum.name().equals(name)) {
                return crmTypeEnum;
            }
        }
        return NULL;
    }

    public String getIndex() {
        return "wk_single_" + name().toLowerCase();
    }

    public String getTableName() {
        return name().toLowerCase();
    }

    /**
     * 获取主键ID
     * @param camelCase 是否驼峰
     * @return primaryKey
     */
    public String getPrimaryKey(boolean camelCase) {
        String name;
        if(this == CrmTypeEnum.RETURN_VISIT){
            name = "visit";
        }else {
            name = name().toLowerCase();
        }
        if(camelCase){
            return StrUtil.toCamelCase(name) + "Id";
        }
        return name + "_id";
    }

    public String getPrimaryKey() {
        return getPrimaryKey(true);
    }

    @Override
    public String toString() {
        return this.type.toString();
    }
}
