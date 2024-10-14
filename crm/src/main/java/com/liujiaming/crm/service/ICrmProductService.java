package com.liujiaming.crm.service;

import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.feign.crm.entity.SimpleCrmEntity;
import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.core.servlet.upload.FileEntity;
import com.liujiaming.crm.common.CrmModel;
import com.liujiaming.crm.entity.BO.CrmModelSaveBO;
import com.liujiaming.crm.entity.BO.CrmProductStatusBO;
import com.liujiaming.crm.entity.BO.CrmSearchBO;
import com.liujiaming.crm.entity.BO.CrmUpdateInformationBO;
import com.liujiaming.crm.entity.PO.CrmProduct;
import com.liujiaming.crm.entity.VO.CrmInfoNumVO;
import com.liujiaming.crm.entity.VO.CrmModelFiledVO;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 产品表 服务类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-26
 */
public interface ICrmProductService extends BaseService<CrmProduct> {
    /**
     * 查询字段配置
     *
     * @param id 主键ID
     * @return data
     */
    public List<CrmModelFiledVO> queryField(Integer id);

    public List<List<CrmModelFiledVO>> queryFormPositionField(Integer id);

    /**
     * 查询所有数据
     *
     * @param search 搜索对象
     * @return data
     */
    public BasePage<Map<String, Object>> queryPageList(CrmSearchBO search);

    public List<SimpleCrmEntity> querySimpleEntity(List<Integer> ids);

    /**
     * 查询字段配置
     *
     * @param id 主键ID
     * @return data
     */
    public CrmModel queryById(Integer id);

    /**
     * 保存或新增信息
     *
     * @param crmModel model
     */
    public void addOrUpdate(CrmModelSaveBO crmModel, boolean isExcel);

    /**
     * 删除数据
     *
     * @param ids ids
     */
    public void deleteByIds(List<Integer> ids);

    /**
     * 修改负责人
     *
     * @param ids       id列表
     * @param newOwnerUserId 新负责人ID
     */
    public void changeOwnerUser(List<Integer> ids, Long newOwnerUserId);


    /**
     * 下载导入模板
     *
     * @param response 线索id
     * @throws IOException exception
     */
    public void downloadExcel(HttpServletResponse response) throws IOException;

    /**
     * 全部导出
     *
     * @param response resp
     * @param search   搜索对象
     */
    public void exportExcel(HttpServletResponse response, CrmSearchBO search);

    /**
     * 修改产品状态
     * @param productStatus status
     */
    public void updateStatus(CrmProductStatusBO productStatus);

    /**
     * 查询详情页基本信息
     * @param productId id
     * @return data
     */
    public List<CrmModelFiledVO> information(Integer productId);

    /**
     * 查询文件数量
     *
     * @param productId id
     * @return data
     */
    public CrmInfoNumVO num(Integer productId);

    /**
     * 查询文件列表
     * @param productId id
     * @return file
     */
    public List<FileEntity> queryFileList(Integer productId);

    /**
     * 查询产品对象
     * @return list
     */
    public List<SimpleCrmEntity> querySimpleEntity();

    void updateInformation(CrmUpdateInformationBO updateInformationBO);

}
