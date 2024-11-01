package com.liujiaming.crm.entity.BO;

import com.liujiaming.core.entity.PageEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("待办事项模块查询BO")
public class CrmBackLogBO extends PageEntity {

    @ApiModelProperty("类型 1今日需要联系的 2已逾期 3已联系")
    private Integer type;

    @ApiModelProperty("isSub 1我的 2我的下属")
    private Integer isSub;

    @ApiModelProperty(value = "高级筛选列表")
    private List<CrmSearchBO.Search> data = new ArrayList<>();


}
