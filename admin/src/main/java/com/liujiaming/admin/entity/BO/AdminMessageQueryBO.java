package com.liujiaming.admin.entity.BO;

import com.liujiaming.core.entity.PageEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("消息列表查询对象")
public class AdminMessageQueryBO extends PageEntity {

    @ApiModelProperty("是否已读")
    private Integer isRead;

    @ApiModelProperty(value = "label 消息大类 1 任务 2 日志 3 oa审批 4公告 5 日程 6 crm消息")
    private Integer label;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("type")
    private Integer type;
}
