package com.liujiaming.admin.common;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

/**
 * 角色类型枚举
 *
 * @author liujiaming
 */
public enum AdminRoleTypeEnum {


    @ApiModelProperty("自定义")
    CUSTOM(0),
    @ApiModelProperty("管理员")
    MANAGER(1),
    @ApiModelProperty("客户经理")
    CUSTOMER_MANAGER(2),
    @ApiModelProperty("人事")
    PERSONNEL(3),
    @ApiModelProperty("财务")
    FINANCE(4),
    @ApiModelProperty("工作")
    WORK(5),
    @ApiModelProperty("办公自动化")
    OA(7),
    @ApiModelProperty("项目")
    PROJECT(8),
    @ApiModelProperty("人力资源管理")
    HRM(9),
    @ApiModelProperty("进销存（采购、销售和库存管理）")
    JXC(10)
    ;

    AdminRoleTypeEnum(Integer type) {
        this.type = type;
    }

    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public static AdminRoleTypeEnum parse(Integer type) {
        for (AdminRoleTypeEnum typeEnum : values()) {
            if (typeEnum.getType().equals(type)) {
                return typeEnum;
            }
        }
        return null;
    }

}
