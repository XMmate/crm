package com.liujiaming.crm.entity.VO;

import com.liujiaming.core.feign.admin.entity.SimpleDept;
import com.liujiaming.core.feign.admin.entity.SimpleUser;
import com.liujiaming.crm.entity.PO.CrmCustomerPool;
import com.liujiaming.crm.entity.PO.CrmCustomerPoolFieldSetting;
import com.liujiaming.crm.entity.PO.CrmCustomerPoolRule;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("crm客户列表页")
public class CrmCustomerPoolVO extends CrmCustomerPool {

    @ApiModelProperty(value = "客户数量")
    private Integer customerNum;

    @ApiModelProperty(value = "管理员")
    private List<SimpleUser> adminUser;

    @ApiModelProperty(value = "公海规则员工成员")
    private List<SimpleUser> memberUser;

    @ApiModelProperty(value = "公海规则部门成员")
    private List<SimpleDept> memberDept;

    @ApiModelProperty(value = "规则")
    private List<CrmCustomerPoolRule> rule;

    @ApiModelProperty(value = "字段")
    private List<CrmCustomerPoolFieldSetting> field;
}
