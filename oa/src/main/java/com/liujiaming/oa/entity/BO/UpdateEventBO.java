package com.liujiaming.oa.entity.BO;

import com.liujiaming.oa.entity.PO.OaEvent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateEventBO {

    private Integer type;

    private Long time;

    private OaEvent event;
}
