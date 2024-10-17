package com.liujiaming.admin.entity.BO;

import com.liujiaming.core.entity.PageEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangzhiwe
 * 用户列表业务对象
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@ApiModel("用户列表查询")
public class AdminUserBO extends PageEntity {

    @ApiModelProperty(value = "类型", required = true, allowableValues = "0,1,2,3,4", example = "0")
    private Integer label;

    @ApiModelProperty("昵称")
    private String realname;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("角色ID")
    private Integer roleId;

    @ApiModelProperty("部门ID")
    private Integer deptId;

    @ApiModelProperty("部门ID列表")
    private List<Integer> deptIdList = new ArrayList<>();


    @ApiModelProperty(value = "用户列表")
    private List<Long> userIdList;


    @ApiModelProperty("部门负责人ID")
    private Long deptOwnerUserId;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty(value = "是否展示子级部门 0不需要 1 需要")
    private Integer isNeedChild;
}
