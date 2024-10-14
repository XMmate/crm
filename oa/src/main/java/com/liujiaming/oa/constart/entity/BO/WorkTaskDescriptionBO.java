package com.liujiaming.oa.constart.entity.BO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liujiaming
 */
@Data
@ApiModel("任务描述参数")
public class WorkTaskDescriptionBO {

    @ApiModelProperty("任务id")
    private Integer taskId;

    @ApiModelProperty("任务描述")
    private String description;
}
