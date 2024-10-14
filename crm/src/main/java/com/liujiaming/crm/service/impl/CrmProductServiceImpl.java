package com.liujiaming.crm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.liujiaming.core.common.FieldEnum;
import com.liujiaming.core.common.log.BehaviorEnum;
import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.exception.CrmException;
import com.liujiaming.core.feign.admin.service.AdminFileService;
import com.liujiaming.core.feign.crm.entity.SimpleCrmEntity;
import com.liujiaming.core.field.service.FieldService;
import com.liujiaming.core.servlet.ApplicationContextHolder;
import com.liujiaming.core.servlet.BaseServiceImpl;
import com.liujiaming.core.servlet.upload.FileEntity;
import com.liujiaming.core.utils.*;
import com.liujiaming.crm.common.ActionRecordUtil;
import com.liujiaming.crm.common.CrmModel;
import com.liujiaming.crm.constant.CrmCodeEnum;
import com.liujiaming.crm.constant.CrmEnum;
import com.liujiaming.crm.entity.BO.CrmModelSaveBO;
import com.liujiaming.crm.entity.BO.CrmProductStatusBO;
import com.liujiaming.crm.entity.BO.CrmSearchBO;
import com.liujiaming.crm.entity.BO.CrmUpdateInformationBO;
import com.liujiaming.crm.entity.PO.*;
import com.liujiaming.crm.entity.VO.CrmFieldSortVO;
import com.liujiaming.crm.entity.VO.CrmInfoNumVO;
import com.liujiaming.crm.entity.VO.CrmModelFiledVO;
import com.liujiaming.crm.mapper.CrmProductMapper;
import com.liujiaming.crm.service.*;
import com.liujiaming.crm.entity.PO.*;
import com.liujiaming.crm.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 产品表 服务实现类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-26
 */
@Service
@Slf4j
public class CrmProductServiceImpl extends BaseServiceImpl<CrmProductMapper, CrmProduct> implements ICrmProductService, CrmPageService {

    private static final String PRODUCT_STATUS_URL = "/crmProduct/updateStatus";

    @Autowired
    private ICrmProductDataService crmProductDataService;

    @Autowired
    private ICrmFieldService crmFieldService;

    @Autowired
    private ICrmActivityService crmActivityService;

    @Autowired
    private ICrmActionRecordService crmActionRecordService;

    @Autowired
    private ICrmProductCategoryService crmProductCategoryService;

    @Autowired
    private ActionRecordUtil actionRecordUtil;

    @Autowired
    private AdminFileService adminFileService;



    @Autowired
    private ICrmProductDetailImgService productDetailImgService;

    @Autowired
    private FieldService fieldService;

    /**
     * 查询字段配置
     *
     * @param id 主键ID
     * @return data
     */
    @Override
    public List<CrmModelFiledVO> queryField(Integer id) {
        return queryField(id,false);
    }

    private List<CrmModelFiledVO> queryField(Integer id,boolean appendInformation) {
        CrmModel crmModel = queryById(id);
        crmModel.setLabel(getLabel().getType());
        List<CrmModelFiledVO> crmModelFiledVoS = crmFieldService.queryField(crmModel);
        for (CrmModelFiledVO crmModelFiledVO : crmModelFiledVoS) {
            if ("categoryId".equals(crmModelFiledVO.getFieldName())) {
                List<Integer> list = crmProductCategoryService.queryId(null, (Integer) crmModelFiledVO.getValue());
                if (CollUtil.isNotEmpty(list)){
                    crmModelFiledVO.setValue(list);
                }else {
                    crmModelFiledVO.setValue(null);
                }
            }
        }

        int authLevel = 3;
        Long userId = UserUtil.getUserId();
        String key = userId.toString();
        List<String> noAuthMenuUrls = BaseUtil.getRedis().get(key);
        if (noAuthMenuUrls != null && noAuthMenuUrls.contains(PRODUCT_STATUS_URL)) {
            authLevel = 2;
        }
        List<Object> statusList = new ArrayList<>();
        statusList.add(new JSONObject().fluentPut("name", "上架").fluentPut("value", 1));
        statusList.add(new JSONObject().fluentPut("name", "下架").fluentPut("value", 0));
        crmModelFiledVoS.add(new CrmModelFiledVO("status", FieldEnum.SELECT, "是否上下架", 1).setIsNull(1).setSetting(statusList).setValue(crmModel.get("status")).setAuthLevel(authLevel));
        if(appendInformation){
            List<CrmModelFiledVO> modelFiledVOS = appendInformation(crmModel);
            crmModelFiledVoS.addAll(modelFiledVOS);
        }
        return crmModelFiledVoS;
    }

    @Override
    public List<List<CrmModelFiledVO>> queryFormPositionField(Integer id) {
        CrmModel crmModel = queryById(id);
        crmModel.setLabel(getLabel().getType());
        List<List<CrmModelFiledVO>> crmModelFiledVoS = crmFieldService.queryFormPositionFieldVO(crmModel);
        for (List<CrmModelFiledVO> filedVOList : crmModelFiledVoS) {
            for (CrmModelFiledVO crmModelFiledVO : filedVOList) {
                if ("categoryId".equals(crmModelFiledVO.getFieldName())) {
                    List<Integer> list = crmProductCategoryService.queryId(null, (Integer) crmModelFiledVO.getValue());
                    if (CollUtil.isNotEmpty(list)) {
                        crmModelFiledVO.setValue(list);
                    } else {
                        crmModelFiledVO.setValue(null);
                    }
                }
            }
        }

        int authLevel = 3;
        Long userId = UserUtil.getUserId();
        String key = userId.toString();
        List<String> noAuthMenuUrls = BaseUtil.getRedis().get(key);
        if (noAuthMenuUrls != null && noAuthMenuUrls.contains(PRODUCT_STATUS_URL)) {
            authLevel = 2;
        }
        List<Object> statusList = new ArrayList<>();
        statusList.add(new JSONObject().fluentPut("name", "上架").fluentPut("value", 1));
        statusList.add(new JSONObject().fluentPut("name", "下架").fluentPut("value", 0));
        CrmModelFiledVO crmModelFiledVO = new CrmModelFiledVO("status", FieldEnum.SELECT, "是否上下架", 1).setIsNull(1).setSetting(statusList).setValue(crmModel.get("status")).setAuthLevel(authLevel);
        crmModelFiledVO.setStylePercent(50);
        crmModelFiledVoS.add(ListUtil.toList(crmModelFiledVO));
        return crmModelFiledVoS;
    }

    /**
     * 分页查询
     *
     * @param search 搜索添加
     * @return data
     */
    @Override
    public BasePage<Map<String, Object>> queryPageList(CrmSearchBO search) {
        BasePage<Map<String, Object>> basePage = queryList(search,false);
        basePage.getList().forEach(map -> {
            String status = map.get("status").toString();
            map.put("status", Objects.equals("1", status) ? "上架" : "下架");
        });
        return basePage;
    }

    @Override
    public List<SimpleCrmEntity> querySimpleEntity(List<Integer> ids) {
        if (ids.size() == 0) {
            return new ArrayList<>();
        }
        List<CrmProduct> list = lambdaQuery().select(CrmProduct::getProductId,CrmProduct::getName).in(CrmProduct::getProductId, ids).list();
        return list.stream().map(crmProduct -> {
            SimpleCrmEntity simpleCrmEntity = new SimpleCrmEntity();
            simpleCrmEntity.setId(crmProduct.getProductId());
            simpleCrmEntity.setName(crmProduct.getName());
            return simpleCrmEntity;
        }).collect(Collectors.toList());
    }

    /**
     * 查询字段配置
     *
     * @param id 主键ID
     * @return data
     */
    @Override
    public CrmModel queryById(Integer id) {
        CrmModel crmModel;
        if (id != null) {
            Integer count = lambdaQuery().eq(CrmProduct::getProductId, id).ne(CrmProduct::getStatus, 3).count();
            if (count == 0) {
                throw new CrmException(CrmCodeEnum.CRM_DATE_REMOVE_ERROR);
            }
            crmModel = getBaseMapper().queryById(id, UserUtil.getUserId());
            crmModel.setLabel(CrmEnum.PRODUCT.getType());
            crmModel.setOwnerUserName(UserCacheUtil.getUserName(crmModel.getOwnerUserId()));
            crmProductDataService.setDataByBatchId(crmModel);
            List<String> stringList = ApplicationContextHolder.getBean(ICrmRoleFieldService.class).queryNoAuthField(crmModel.getLabel());
            stringList.forEach(crmModel::remove);
            Optional<CrmProductDetailImg> detailImgOpt = productDetailImgService.lambdaQuery().eq(CrmProductDetailImg::getProductId, id).oneOpt();
            if (detailImgOpt.isPresent()) {
                CrmProductDetailImg detailImg = detailImgOpt.get();
                if (detailImg.getMainFileIds() != null) {
                    List<FileEntity> mainFileList = adminFileService.queryByIds(TagUtil.toLongSet(detailImg.getMainFileIds())).getData();
                    crmModel.put("mainFileList", mainFileList);
                } else {
                    crmModel.put("mainFileList", new ArrayList<>());
                }
                if (detailImg.getDetailFileIds() != null) {
                    List<FileEntity> detailFileList = adminFileService.queryByIds(TagUtil.toLongSet(detailImg.getDetailFileIds())).getData();
                    crmModel.put("detailFileList", detailFileList);
                } else {
                    crmModel.put("detailFileList", new ArrayList<>());
                }
            } else {
                crmModel.put("mainFileList", new ArrayList<>());
                crmModel.put("detailFileList", new ArrayList<>());
            }
        } else {
            crmModel = new CrmModel(CrmEnum.PRODUCT.getType());
        }
        return crmModel;
    }

    /**
     * 保存或新增信息
     *
     * @param crmModel model
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdate(CrmModelSaveBO crmModel, boolean isExcel) {
        CrmProduct crmProduct = BeanUtil.copyProperties(crmModel.getEntity(), CrmProduct.class);
        String batchId = StrUtil.isNotEmpty(crmProduct.getBatchId()) ? crmProduct.getBatchId() : IdUtil.simpleUUID();
        actionRecordUtil.updateRecord(crmModel.getField(), Dict.create().set("batchId", batchId).set("dataTableName", "wk_crm_product_data"));
        crmProductDataService.saveData(crmModel.getField(), batchId);
        if (crmProduct.getProductId() == null) {
            crmProduct.setCreateUserId(UserUtil.getUserId());
            crmProduct.setCreateTime(DateUtil.date());
            crmProduct.setUpdateTime(DateUtil.date());
            if (crmProduct.getOwnerUserId() == null) {
                crmProduct.setOwnerUserId(UserUtil.getUserId());
            }
            crmProduct.setBatchId(batchId);
            save(crmProduct);
            actionRecordUtil.addRecord(crmProduct.getProductId(), CrmEnum.PRODUCT, crmProduct.getName());
        } else {
            actionRecordUtil.updateRecord(BeanUtil.beanToMap(getById(crmProduct.getProductId())), BeanUtil.beanToMap(crmProduct), CrmEnum.PRODUCT, crmProduct.getName(), crmProduct.getProductId());
            crmProduct.setUpdateTime(DateUtil.date());
            updateById(crmProduct);
            crmProduct = getById(crmProduct.getProductId());
        }
        Optional<CrmProductDetailImg> detailImgOpt = productDetailImgService.lambdaQuery().eq(CrmProductDetailImg::getProductId, crmProduct.getProductId()).oneOpt();
        if (detailImgOpt.isPresent()){
            CrmProductDetailImg crmProductDetailImg = detailImgOpt.get();
            crmProductDetailImg.setDetailFileIds((String) crmModel.getEntity().get("detailFileIds"));
            crmProductDetailImg.setMainFileIds((String) crmModel.getEntity().get("mainFileIds"));
            productDetailImgService.updateById(crmProductDetailImg);
        }else {
            CrmProductDetailImg crmProductDetailImg = new CrmProductDetailImg();
            crmProductDetailImg.setProductId(crmProduct.getProductId());
            crmProductDetailImg.setDetailFileIds((String) crmModel.getEntity().get("detailFileIds"));
            crmProductDetailImg.setMainFileIds((String) crmModel.getEntity().get("mainFileIds"));
            productDetailImgService.save(crmProductDetailImg);
        }
        crmModel.setEntity(BeanUtil.beanToMap(crmProduct));
        savePage(crmModel, crmProduct.getProductId(),isExcel);
    }

    @Override
    public void setOtherField(Map<String, Object> map) {
        String createUserName = UserCacheUtil.getUserName((Long) map.get("createUserId"));
        map.put("createUserName", createUserName);
        CrmProductCategory productCategory = crmProductCategoryService.getById((Serializable) map.get("categoryId"));
        if (productCategory != null){
            map.put("categoryName", productCategory.getName());
        }else {
            map.put("categoryName", "");
        }
        String ownerUserName = UserCacheUtil.getUserName((Long) map.get("ownerUserId"));
        map.put("ownerUserName",ownerUserName);
    }


    /**
     * 删除数据
     *
     * @param ids ids
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByIds(List<Integer> ids) {
        LambdaQueryWrapper<CrmProduct> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(CrmProduct::getBatchId);
        wrapper.in(CrmProduct::getProductId, ids);
        List<String> batchIdList = listObjs(wrapper, Object::toString);
        //删除字段操作记录
        crmActionRecordService.deleteActionRecord(CrmEnum.PRODUCT, ids);
        if (CollUtil.isNotEmpty(batchIdList)){
            //删除自定义字段
            //TODO 不删除,产品单位是自定义字段,删除后关联的产品没有单位
//            crmProductDataService.deleteByBatchId(batchIdList);
        }
        LambdaUpdateWrapper<CrmProduct> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(CrmProduct::getStatus, 3);
        updateWrapper.in(CrmProduct::getProductId, ids);
        update(updateWrapper);
        //todo 删除文件,暂不处理
        //删除es数据
        deletePage(ids);
    }

    /**
     * 修改负责人
     *
     * @param ids            id列表
     * @param newOwnerUserId 新负责人ID
     */
    @Override
    public void changeOwnerUser(List<Integer> ids, Long newOwnerUserId) {
        LambdaUpdateWrapper<CrmProduct> wrapper = new LambdaUpdateWrapper<>();
        wrapper.in(CrmProduct::getProductId, ids);
        wrapper.set(CrmProduct::getOwnerUserId, newOwnerUserId);
        update(wrapper);
        for (Integer id : ids) {
            actionRecordUtil.addConversionRecord(id,CrmEnum.PRODUCT,newOwnerUserId,getById(id).getName());
        }
        //修改es
        String ownerUserName = UserCacheUtil.getUserName(newOwnerUserId);
        Map<String, Object> map = new HashMap<>();
        map.put("ownerUserId", newOwnerUserId);
        map.put("ownerUserName", ownerUserName);
        updateField(map, ids);
    }

    /**
     * 下载导入模板
     *
     * @param response 产品id
     * @throws IOException exception
     */
    @Override
    public void downloadExcel(HttpServletResponse response) throws IOException {
        List<CrmModelFiledVO> crmModelFiledList = queryField(null);
        int k = 0;
        for (int i = 0; i < crmModelFiledList.size(); i++) {
            CrmModelFiledVO modelFiledVO = crmModelFiledList.get(i);
            if(modelFiledVO.getFieldName().equals("name")){
                k=i;continue;
            }
            if ("categoryId".equals(modelFiledVO.getFieldName())) {
                modelFiledVO.setSetting(crmProductCategoryService.queryListName());
            }
        }
        crmModelFiledList.add(k+1,new CrmModelFiledVO("ownerUserId",FieldEnum.TEXT,"负责人",1).setIsNull(1));
        ExcelParseUtil.importExcel(new ExcelParseUtil.ExcelParseService() {
            @Override
            public void castData(Map<String, Object> record, Map<String, Integer> headMap) {

            }
            @Override
            public String getExcelName() {
                return "产品";
            }
        }, crmModelFiledList, response,"crm");
    }

    /**
     * 全部导出
     *
     * @param response resp
     * @param search   搜索对象
     */
    @Override
    public void exportExcel(HttpServletResponse response, CrmSearchBO search) {
        List<Map<String, Object>> dataList = queryList(search,true).getList();
        List<CrmFieldSortVO> headList = crmFieldService.queryListHead(getLabel().getType());
        ExcelParseUtil.exportExcel(dataList, new ExcelParseUtil.ExcelParseService() {
            @Override
            public void castData(Map<String, Object> record, Map<String, Integer> headMap) {
                for (String fieldName : headMap.keySet()) {
                    record.put(fieldName,ActionRecordUtil.parseValue(record.get(fieldName),headMap.get(fieldName),false));
                }
            }
            @Override
            public String getExcelName() {
                return "产品";
            }
        },headList,response);
    }

    /**
     * 修改产品状态
     *
     * @param productStatus status
     */
    @Override
    public void updateStatus(CrmProductStatusBO productStatus) {
        Integer status = Objects.equals(0, productStatus.getStatus()) ? 0 : 1;
        LambdaUpdateWrapper<CrmProduct> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(CrmProduct::getStatus, status);
        wrapper.in(CrmProduct::getProductId, productStatus.getIds());
        update(wrapper);
        updateField("status",status,productStatus.getIds());
    }

    @Override
    public List<CrmModelFiledVO> information(Integer productId) {
        return queryField(productId,true);
    }

    /**
     * 查询文件数量
     *
     * @param productId id
     * @return data
     */
    @Override
    public CrmInfoNumVO num(Integer productId) {
        CrmProduct crmProduct = getById(productId);
        AdminFileService fileService = ApplicationContextHolder.getBean(AdminFileService.class);
        List<CrmField> crmFields = crmFieldService.queryFileField();
        List<String> batchIdList = new ArrayList<>();
        if (crmFields.size() > 0) {
            LambdaQueryWrapper<CrmProductData> wrapper = new LambdaQueryWrapper<>();
            wrapper.select(CrmProductData::getValue);
            wrapper.eq(CrmProductData::getBatchId, crmProduct.getBatchId());
            wrapper.in(CrmProductData::getFieldId, crmFields.stream().map(CrmField::getFieldId).collect(Collectors.toList()));
            batchIdList.addAll(crmProductDataService.listObjs(wrapper, Object::toString));
        }
        batchIdList.add(crmProduct.getBatchId());
        batchIdList.addAll(crmActivityService.queryFileBatchId(crmProduct.getProductId(), getLabel().getType()));
        CrmInfoNumVO infoNumVO = new CrmInfoNumVO();
        infoNumVO.setFileCount(fileService.queryNum(batchIdList).getData());
        return infoNumVO;
    }

    /**
     * 查询文件列表
     *
     * @param productId id
     * @return file
     */
    @Override
    public List<FileEntity> queryFileList(Integer productId) {
        List<FileEntity> fileEntityList = new ArrayList<>();
        CrmProduct crmProduct = getById(productId);
        AdminFileService fileService = ApplicationContextHolder.getBean(AdminFileService.class);
        fileService.queryFileList(crmProduct.getBatchId()).getData().forEach(fileEntity -> {
            fileEntity.setSource("附件上传");
            fileEntity.setReadOnly(0);
            fileEntityList.add(fileEntity);
        });
        List<CrmField> crmFields = crmFieldService.queryFileField();
        if (crmFields.size() > 0) {
            LambdaQueryWrapper<CrmProductData> wrapper = new LambdaQueryWrapper<>();
            wrapper.select(CrmProductData::getValue);
            wrapper.eq(CrmProductData::getBatchId, crmProduct.getBatchId());
            wrapper.in(CrmProductData::getFieldId, crmFields.stream().map(CrmField::getFieldId).collect(Collectors.toList()));
            List<FileEntity> data = fileService.queryFileList(crmProductDataService.listObjs(wrapper, Object::toString)).getData();
            data.forEach(fileEntity -> {
                fileEntity.setSource("产品详情");
                fileEntity.setReadOnly(1);
                fileEntityList.add(fileEntity);
            });
        }
        return fileEntityList;
    }


    /**
     * 查询产品对象
     *
     * @return list
     */
    @Override
    public List<SimpleCrmEntity> querySimpleEntity() {
        List<CrmProduct> list = lambdaQuery().ne(CrmProduct::getStatus,3).list();
        return list.stream().map(crmProduct -> {
            SimpleCrmEntity simpleCrmEntity = new SimpleCrmEntity();
            simpleCrmEntity.setId(crmProduct.getProductId());
            simpleCrmEntity.setName(crmProduct.getName());
            return simpleCrmEntity;
        }).collect(Collectors.toList());
    }

    /**
     * 大的搜索框的搜索字段
     *
     * @return fields
     */
    @Override
    public String[] appendSearch() {
        return new String[]{"name"};
    }

    /**
     * 获取crm列表类型
     *
     * @return data
     */
    @Override
    public CrmEnum getLabel() {
        return CrmEnum.PRODUCT;
    }

    /**
     * 查询所有字段
     *
     * @return data
     */
    @Override
    public List<CrmModelFiledVO> queryDefaultField() {
        List<CrmModelFiledVO> filedList = crmFieldService.queryField(getLabel().getType());
        filedList.add(new CrmModelFiledVO("updateTime", FieldEnum.DATETIME,1));
        filedList.add(new CrmModelFiledVO("createTime", FieldEnum.DATETIME,1));
        filedList.add(new CrmModelFiledVO("createUserId", FieldEnum.USER,1));
        filedList.add(new CrmModelFiledVO("status", FieldEnum.TEXT,1));
        return filedList;
    }


    @Override
    public void updateInformation(CrmUpdateInformationBO updateInformationBO) {
        String batchId = updateInformationBO.getBatchId();
        Integer productId = updateInformationBO.getId();
        updateInformationBO.getList().forEach(record -> {
            CrmProduct oldProduct = getById(updateInformationBO.getId());
            uniqueFieldIsAbnormal(record.getString("name"),record.getInteger("fieldId"),record.getString("value"),batchId);
            Map<String, Object> oldProductMap = BeanUtil.beanToMap(oldProduct);
            if (record.getInteger("fieldType") == 1) {
                Map<String, Object> crmProductMap = new HashMap<>(oldProductMap);
                crmProductMap.put(record.getString("fieldName"), record.get("value"));
                CrmProduct crmProduct = BeanUtil.mapToBean(crmProductMap, CrmProduct.class, true);
                actionRecordUtil.updateRecord(oldProductMap, crmProductMap, CrmEnum.PRODUCT, crmProduct.getName(), crmProduct.getProductId());
                update().set(StrUtil.toUnderlineCase(record.getString("fieldName")), record.get("value")).eq("product_id",updateInformationBO.getId()).update();
            } else if (record.getInteger("fieldType") == 0 || record.getInteger("fieldType") == 2) {
                CrmProductData productData = crmProductDataService.lambdaQuery().select(CrmProductData::getValue,CrmProductData::getId).eq(CrmProductData::getFieldId, record.getInteger("fieldId"))
                        .eq(CrmProductData::getBatchId, batchId).one();
                String value = productData != null ? productData.getValue() : null;
                actionRecordUtil.publicContentRecord(CrmEnum.PRODUCT, BehaviorEnum.UPDATE, productId, oldProduct.getName(), record,value);
                String newValue = fieldService.convertObjectValueToString(record.getInteger("type"),record.get("value"),record.getString("value"));
                CrmProductData crmProductData = new CrmProductData();
                crmProductData.setId(productData != null ? productData.getId() : null);
                crmProductData.setFieldId(record.getInteger("fieldId"));
                crmProductData.setName(record.getString("fieldName"));
                crmProductData.setValue(newValue);
                crmProductData.setCreateTime(new Date());
                crmProductData.setBatchId(batchId);
                crmProductDataService.saveOrUpdate(crmProductData);
            }
            updateField(record,productId);
        });
        this.lambdaUpdate().set(CrmProduct::getUpdateTime,new Date()).eq(CrmProduct::getProductId,productId).update();
    }
}
