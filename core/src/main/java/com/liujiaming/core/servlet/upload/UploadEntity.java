package com.liujiaming.core.servlet.upload;

import lombok.Data;

/**
 * 上传文件entity对象
 *
 * @author liujiaming
 */
@Data
public class UploadEntity {

    public UploadEntity() {

    }

    public UploadEntity(String fileId, String name, Long size, String batchId,String isPublic) {
        this.fileId = fileId;
        this.name = name;
        this.size = size;
        this.batchId = batchId;
        this.isPublic = isPublic;
    }

    /**
     * 文件ID
     */
    private String fileId;
    /**
     * 文件名称
     */
    private String name;
    /**
     * 文件大小
     */
    private Long size;

    /**
     * 批次ID
     */
    private String batchId;

    /**
     * 上传类型
     */
    private Integer type;

    /**
     * 上传路径
     */
    private String path;


    /**
     * 文件URL
     */
    private String url;

    /**
     * 是否公开
     */
    private String isPublic;

    public String getUrl() {
        if ("1".equals(isPublic)){
            return path;
        }else {
            return "/adminFile/down/" + fileId;
        }
    }

    public void setUrl(String url) {
        this.path = url;
    }
}
