package com.liujiaming.crm.entity.BO;

import com.esotericsoftware.kryo.NotNull;
import com.liujiaming.crm.entity.PO.CrmMarketingField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author liujiaming
 * @date 2024/12/2
 */
@Data
@NoArgsConstructor
@ApiModel(value="MarketingField保存对象", description="活动自定义字段表")
public class MarketingFieldBO {

    @ApiModelProperty(value = "审批类型id")
    @NotNull
    private Integer formId;

    @ApiModelProperty(value = "CrmMarketingField对象列表")
    private List<CrmMarketingField> data;
}
