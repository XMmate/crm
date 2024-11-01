package com.liujiaming.crm.common;

import io.swagger.annotations.ApiModelProperty;

import java.util.HashMap;

/**
 * @author liujiaming
 * crm模块单个的实体类对象
 * 这里继承了Map
 */
public class CrmModel extends HashMap<String, Object> {




    @ApiModelProperty(value = "crm类型对应这个CrmTypeEnum枚举类")
    private transient Integer label;


    @ApiModelProperty(value = "主键id")
    private transient Integer id;


    public CrmModel() {

    }

    public CrmModel(Integer label) {
        this.label = label;
    }


    public Integer getLabel() {
        return label;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLabel(Integer label) {
        this.label = label;
    }

    public Long getOwnerUserId() {
        return (Long) get("ownerUserId");
    }

    public void setOwnerUserId(Long ownerUserId) {
        put("ownerUserId",ownerUserId);
    }

    public String getOwnerUserName() {
        return (String) get("ownerUserName");
    }

    public void setOwnerUserName(String ownerUserName) {
        put("ownerUserName",ownerUserName);
    }

    public String getBatchId(){
        return (String) get("batchId");
    }
}
