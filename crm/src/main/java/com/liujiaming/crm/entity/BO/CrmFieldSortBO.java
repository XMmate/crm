package com.liujiaming.crm.entity.BO;

import com.liujiaming.crm.entity.PO.CrmFieldSort;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author liujiaming
 */
@Data
@ToString
@ApiModel(value = "CrmFieldSort字段调整对象", description = "字段排序表")
public class CrmFieldSortBO {

    @ApiModelProperty(value = "不隐藏的字段")
    private List<CrmFieldSort> noHideFields;

    @ApiModelProperty(value = "隐藏的字段")
    private List<CrmFieldSort> hideFields;

    @NotNull
    @ApiModelProperty(value = "label", required = true)
    private Integer label;
}
