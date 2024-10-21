package com.liujiaming.crm.entity.BO;

import com.liujiaming.core.entity.UserInfo;
import com.liujiaming.crm.constant.CrmTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("上传文件业务对象")
public class UploadExcelBO {

    @ApiModelProperty("crm类型")
    private CrmTypeEnum crmTypeEnum;

    @ApiModelProperty("文件路径")
    private String filePath;

    @ApiModelProperty("公海ID")
    private Integer poolId;

    @ApiModelProperty("重复是否继续")
    private Integer repeatHandling;

    @ApiModelProperty("messageId")
    private Long messageId;

    @ApiModelProperty("上传人ID")
    private UserInfo userInfo;
}
