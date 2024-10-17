package com.liujiaming.admin.entity.BO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminDeleteByBatchIdBO {
  @ApiModelProperty("批次ID")
    private String batchId;

    @ApiModelProperty("1 附件 2 图片")
    private Integer type;
}
