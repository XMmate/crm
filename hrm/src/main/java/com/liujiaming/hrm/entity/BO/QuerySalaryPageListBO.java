package com.liujiaming.hrm.entity.BO;

import com.liujiaming.core.entity.PageEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuerySalaryPageListBO extends PageEntity {

    @ApiModelProperty("薪资记录id")
    private Integer sRecordId;

    private Integer employeeId;

    private Integer deptId;

    private Integer type;

    private String employeeName;

}
