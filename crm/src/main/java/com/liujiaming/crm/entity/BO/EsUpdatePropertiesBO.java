package com.liujiaming.crm.entity.BO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@ApiModel(value = "es特性更新BO")
public class EsUpdatePropertiesBO {

    private String idField;

    private String nameField;

    private List<String> indexs;

	@ApiModelProperty("条件集合")
	private Map<String, String> conditions;

	public EsUpdatePropertiesBO(String idField, String nameField, List<String> indexs) {
		this.idField = idField;
		this.nameField = nameField;
		this.indexs = indexs;
	}
}
