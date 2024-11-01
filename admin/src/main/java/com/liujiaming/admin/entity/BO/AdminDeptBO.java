package com.liujiaming.admin.entity.BO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/** 新增修改部门用的
 * @author liujiaming
 */
@Data
@ToString
@ApiModel(value="部门编辑对象", description="部门对象")
public class AdminDeptBO {

    @ApiModelProperty(value = "部门ID")
    private Integer deptId;

    @ApiModelProperty(value = "上级部门ID，0为最上级")
    @NotNull
    private Integer pid;

    @ApiModelProperty(value = "部门名称")
    @NotNull
    @Size(max = 20)
    private String name;

    @ApiModelProperty(value = "部门负责人")
    private Long ownerUserId;
}
