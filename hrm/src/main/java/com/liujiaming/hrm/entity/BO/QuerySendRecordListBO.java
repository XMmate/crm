package com.liujiaming.hrm.entity.BO;

import com.liujiaming.core.entity.PageEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuerySendRecordListBO extends PageEntity {

    private Integer year;

    private Integer month;
}
