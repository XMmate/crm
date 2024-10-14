package com.liujiaming.crm.service;

import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.feign.crm.entity.SimpleCrmEntity;
import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.core.servlet.upload.FileEntity;
import com.liujiaming.crm.entity.BO.CrmContractSaveBO;
import com.liujiaming.crm.entity.BO.CrmSearchBO;
import com.liujiaming.crm.entity.BO.CrmUpdateInformationBO;
import com.liujiaming.crm.entity.PO.CrmInvoice;
import com.liujiaming.crm.entity.PO.CrmInvoiceInfo;
import com.liujiaming.crm.entity.VO.CrmModelFiledVO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liujiaming
 * @since 2024-07-06
 */
public interface ICrmInvoiceService extends BaseService<CrmInvoice> {

    List<SimpleCrmEntity> querySimpleEntity(List<Integer> ids);

    CrmInvoice queryById(Integer invoiceId);

    void updateInvoiceStatus(CrmInvoice crmInvoice);

    void deleteByIds(List<Integer> ids);

    void changeOwnerUser(List<Integer> ids, Long ownerUserId);

    List<FileEntity> queryFileList(Integer id);

    void saveInvoiceInfo(CrmInvoiceInfo crmInvoiceInfo);

    void updateInvoiceInfo(CrmInvoiceInfo crmInvoiceInfo);

    void deleteInvoiceInfo(Integer infoId);

    BasePage<Map<String, Object>> queryPageList(CrmSearchBO search);

    List<CrmModelFiledVO> queryField(Integer id);

    List<List<CrmModelFiledVO>> queryFormPositionField(Integer id);

    void addOrUpdate(CrmContractSaveBO crmModel, boolean isExcel);

    /**
     * 全部导出
     *
     * @param response resp
     * @param search   搜索对象
     */
    void exportExcel(HttpServletResponse response, CrmSearchBO search);

    List<CrmModelFiledVO> information(Integer invoiceId);

    void updateInformation(CrmUpdateInformationBO updateInformationBO);
}
