package com.liujiaming.oa.entity.BO;

import com.liujiaming.oa.entity.PO.OaEvent;
import com.liujiaming.oa.entity.PO.OaEventNotice;
import com.liujiaming.oa.entity.PO.OaEventRelation;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SetEventBO {

    @ApiModelProperty("日程")
    private OaEvent event;

    @ApiModelProperty("关联业务")
    private OaEventRelation relation;

    @ApiModelProperty("日程通知 多久通知一次")
    private List<OaEventNotice> notice;

    private Integer type;

    private Long time;

}
