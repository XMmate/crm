package com.liujiaming.oa.entity.BO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.liujiaming.core.entity.PageEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("日志搜索BO")
public class LogBO extends PageEntity {

    @ApiModelProperty("类型")
    private Integer by;

    @ApiModelProperty("日期类型")
    private String type;

    private Integer completeType;

    @ApiModelProperty("创建人ID")
    private String createUserId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    @ApiModelProperty("部门列表")
    private List<Integer> deptIds;

    private Integer categoryId;

    private Integer crmType;
    private Integer order;
    private Integer today;
    private String search;
    private String sortField;
    private Integer logId;
}
