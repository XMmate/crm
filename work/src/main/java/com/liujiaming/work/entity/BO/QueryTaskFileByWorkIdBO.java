package com.liujiaming.work.entity.BO;

import com.liujiaming.core.entity.PageEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryTaskFileByWorkIdBO extends PageEntity {

    private Integer workId;
}
