package com.kakarote.examine.entity.BO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("crm审核对象")
public class ExamineBO {

    @ApiModelProperty("审核记录ID")
    private Integer recordId;

    @ApiModelProperty("审核流程ID")
    private Integer flowId;

    @ApiModelProperty("审核状态   0待审核、1通过、2拒绝、3审核中 4:撤回 5 未提交 6 创建 7 已删除 8 作废 10 忽略 即未启用审批流")
    private Integer status;

    @ApiModelProperty("相关审核ID")
    private Integer typeId;

    @ApiModelProperty("审核意见")
    private String remarks;

    @ApiModelProperty("审核人ID")
    private Long examineUserId;


}
