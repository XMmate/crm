package com.liujiaming.oa.entity.BO;

import com.liujiaming.core.entity.PageEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ExamineExportBO extends PageEntity {

    private Integer categoryId;

    private Integer queryType;

    private Integer status;

    private Date startTime;

    private Date enbTime;

    private Long createUserId;

    private Integer checkStatus;

    public void setStatus(Integer status) {
        this.status = status;
        this.checkStatus = status;
    }
}
