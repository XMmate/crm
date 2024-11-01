package com.liujiaming.crm.entity.PO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 合同产品关系表
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_crm_contract_product")
@ApiModel(value="CrmContractProduct对象", description="合同产品关系表")
public class CrmContractProduct implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "r_id", type = IdType.AUTO)
    private Integer rId;

    @ApiModelProperty(value = "合同ID")
    private Integer contractId;

    @ApiModelProperty(value = "产品ID")
    private Integer productId;

    @ApiModelProperty(value = "产品单价")
    private BigDecimal price;

    @ApiModelProperty(value = "销售价格")
    private BigDecimal salesPrice;

    @ApiModelProperty(value = "数量")
    private BigDecimal num;

    @ApiModelProperty(value = "折扣")
    private BigDecimal discount;

    @ApiModelProperty(value = "小计（折扣后价格）")
    private BigDecimal subtotal;

    @ApiModelProperty(value = "单位")
    private String unit;


    @ApiModelProperty(value = "产品名称")
    @TableField(exist = false)
    private String name;

    @ApiModelProperty(value = "产品类型名称")
    @TableField(exist = false)
    private String categoryName;


}
