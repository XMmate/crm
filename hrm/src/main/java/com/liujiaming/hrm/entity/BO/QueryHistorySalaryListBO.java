package com.liujiaming.hrm.entity.BO;

import com.liujiaming.core.entity.PageEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryHistorySalaryListBO extends PageEntity {

    @ApiModelProperty("年")
    private Integer year;
}
