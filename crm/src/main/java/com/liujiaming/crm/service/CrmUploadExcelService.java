package com.liujiaming.crm.service;

import com.liujiaming.crm.entity.BO.UploadExcelBO;
import org.springframework.web.multipart.MultipartFile;

public interface CrmUploadExcelService {

    /**
     * 导入excel
     */
    public Long uploadExcel(MultipartFile file, UploadExcelBO uploadExcelBO);


}
