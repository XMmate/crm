package com.liujiaming.hrm.entity.BO;

import com.liujiaming.core.entity.PageEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryHistorySalaryDetailBO extends PageEntity {

    private Integer sRecordId;

    private String employeeName;

    private String jobNumber;

    private Integer deptId;
}
