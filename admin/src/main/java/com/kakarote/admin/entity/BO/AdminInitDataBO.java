package com.kakarote.admin.entity.BO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author liujiaming
 * @date 2024/11/17
 */
@Data
public class AdminInitDataBO {

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("临时码")
    private String temporaryCode;

    @ApiModelProperty("模块")
    private List<String> modules;

}
