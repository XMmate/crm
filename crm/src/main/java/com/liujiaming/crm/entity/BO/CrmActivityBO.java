package com.liujiaming.crm.entity.BO;

import com.liujiaming.core.entity.PageEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("活动记录BO")
public class CrmActivityBO extends PageEntity {

    @ApiModelProperty("crm类型")
    private Integer crmType;

    @ApiModelProperty("活动类型")
    private Integer activityType;

    @ApiModelProperty("活动类型ID")
    private Integer activityTypeId;


    @ApiModelProperty("搜索关键字")
    private String search;

    @ApiModelProperty("间隔日")
    private Integer intervalDay;

   @ApiModelProperty("开始时间")
    private String startDate;

    @ApiModelProperty("结束时间")
    private String endDate;
}
