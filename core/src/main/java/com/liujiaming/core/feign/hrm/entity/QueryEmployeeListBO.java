package com.liujiaming.core.feign.hrm.entity;

import com.liujiaming.core.entity.PageEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryEmployeeListBO extends PageEntity {
    @ApiModelProperty("搜索")
    private String search;

    @ApiModelProperty("部门id")
    private Integer deptId;

}
