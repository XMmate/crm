package com.liujiaming.admin.entity.BO;

import com.liujiaming.core.entity.PageEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author zmj
 * 语言包服务
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class AdminLanguagePackBO extends PageEntity {

    @ApiModelProperty(value = "语言包ID")
    private Integer languagePackId;

    @ApiModelProperty("语言包名称")
    private String languagePackName;

}
