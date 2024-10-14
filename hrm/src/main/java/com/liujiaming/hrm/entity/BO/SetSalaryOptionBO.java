package com.liujiaming.hrm.entity.BO;

import com.liujiaming.hrm.entity.PO.HrmSalaryOption;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SetSalaryOptionBO {

    @ApiModelProperty("所有薪资项列表")
    private List<HrmSalaryOption> optionList;

    @ApiModelProperty("隐藏code列表")
    private List<Integer> hideList;
}
