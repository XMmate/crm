package com.liujiaming.core.feign.examine.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liujiaming
 * @date 2024/12/21
 */
@Data
public class ExamineMessageBO {

    private Integer categoryType;

    @ApiModelProperty("审核通知类型  1 待审核 2 通过 3 拒绝")
    private Integer examineType;

    private ExamineRecordLog examineLog;

    private Long ownerUserId;
}
