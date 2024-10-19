package com.liujiaming.core.feign.crm.entity;

import com.liujiaming.core.entity.PageEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author liujiaming
 * bi参数
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel("bi查询相关参数")
@Data
public class BiParams extends PageEntity {

    @ApiModelProperty("部门ID")
    private Integer deptId;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("用户组ids")
    private List<Long> userIds;


    @ApiModelProperty("类型 今天,昨天,本年，去年，本季等")
    private String type;



    @ApiModelProperty("开始时间")
    private String startTime;

    @ApiModelProperty("结束时间")
    private String endTime;

    @ApiModelProperty("0 部门 1员工")
    private Integer isUser = 1;

    @ApiModelProperty("类型ID")
    private Integer typeId;

    @ApiModelProperty("年份")
    private Integer year;

    @ApiModelProperty("菜单ID")
    private Integer menuId;

    @ApiModelProperty("月份")
    private Integer moneyType;

    @ApiModelProperty("数据类型")
    private Integer dataType;

    @ApiModelProperty("查询类型，跟进记录需要")
    private Integer queryType;

    @ApiModelProperty("crm类型")
    private Integer label;


    @ApiModelProperty("排序方式")
    private Integer order;

    @ApiModelProperty("排序字段")
    private String sortField;

    @ApiModelProperty("检查状态")
    private Integer checkStatus;

  @ApiModelProperty("子用户")
    private Integer subUser;

    @ApiModelProperty("搜索关键字")
    private String search;

    private Integer day;
}
