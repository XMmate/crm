package com.liujiaming.crm.entity.BO;

import com.liujiaming.crm.entity.PO.CrmField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author liujiaming
 * 保存字段
 */
@Data
@ToString
@ApiModel(value="CrmField保存对象", description="自定义字段表")
public class CrmFieldBO {

    @ApiModelProperty(value = "标签 1 线索 2 客户 3 联系人 4 产品 5 商机 6 合同 7回款8.回款计划 18发票")
    @NotNull
    private Integer label;

    @ApiModelProperty(value = "CrmField对象列表")
    private List<CrmField> data;
}
