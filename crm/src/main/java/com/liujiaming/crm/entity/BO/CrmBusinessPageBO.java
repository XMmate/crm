package com.liujiaming.crm.entity.BO;

import com.liujiaming.core.entity.PageEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Data
@EqualsAndHashCode(callSuper = true)
@ToString
@ApiModel("联系人下查询商机")
public class CrmBusinessPageBO extends PageEntity {

    @ApiModelProperty("联系人id")
    private Integer contactsId;
}
