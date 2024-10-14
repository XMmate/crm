package com.liujiaming.crm.entity.VO;

import com.liujiaming.crm.entity.PO.CrmActivity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("活动记录VO")
public class CrmActivityVO {

    @ApiModelProperty("time")
    private String time;

    @ApiModelProperty("list")
    private List<CrmActivity> list;

    @ApiModelProperty("是否最后一页")
    private Boolean lastPage;
}
