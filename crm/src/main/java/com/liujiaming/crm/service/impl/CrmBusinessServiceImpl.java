package com.liujiaming.crm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.TypeUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.liujiaming.core.common.FieldEnum;
import com.liujiaming.core.common.SystemCodeEnum;
import com.liujiaming.core.common.log.BehaviorEnum;
import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.exception.CrmException;
import com.liujiaming.core.feign.admin.service.AdminFileService;
import com.liujiaming.core.feign.crm.entity.CrmEventBO;
import com.liujiaming.core.feign.crm.entity.QueryEventCrmPageBO;
import com.liujiaming.core.feign.crm.entity.SimpleCrmEntity;
import com.liujiaming.core.field.service.FieldService;
import com.liujiaming.core.servlet.ApplicationContextHolder;
import com.liujiaming.core.servlet.BaseServiceImpl;
import com.liujiaming.core.servlet.upload.FileEntity;
import com.liujiaming.core.utils.ExcelParseUtil;
import com.liujiaming.core.utils.UserCacheUtil;
import com.liujiaming.core.utils.UserUtil;
import com.liujiaming.crm.common.*;
import com.liujiaming.crm.constant.CrmActivityEnum;
import com.liujiaming.crm.constant.CrmAuthEnum;
import com.liujiaming.crm.constant.CrmCodeEnum;
import com.liujiaming.crm.constant.CrmTypeEnum;
import com.liujiaming.crm.entity.BO.*;
import com.liujiaming.crm.entity.PO.*;
import com.liujiaming.crm.entity.VO.CrmFieldSortVO;
import com.liujiaming.crm.entity.VO.CrmInfoNumVO;
import com.liujiaming.crm.entity.VO.CrmListBusinessStatusVO;
import com.liujiaming.crm.entity.VO.CrmModelFiledVO;
import com.liujiaming.crm.mapper.CrmBusinessMapper;
import com.liujiaming.crm.service.*;
import com.liujiaming.crm.entity.BO.*;
import com.liujiaming.crm.entity.PO.*;
import com.liujiaming.crm.service.*;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.metrics.sum.ParsedSum;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 商机表 服务实现类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-27
 */
@Service
@Slf4j
@Primary
public class CrmBusinessServiceImpl extends BaseServiceImpl<CrmBusinessMapper, CrmBusiness> implements CrmPageService, ICrmBusinessService{

    @Autowired
    private ICrmFieldService crmFieldService;

    @Autowired
    private ICrmBusinessDataService crmBusinessDataService;

    @Autowired
    private ICrmActivityService crmActivityService;

    @Autowired
    private ICrmBusinessProductService crmBusinessProductService;

    @Autowired
    private ICrmContactsBusinessService crmContactsBusinessService;

    @Autowired
    private ICrmContractService crmContractService;

    @Autowired
    private ICrmActionRecordService crmActionRecordService;

    @Autowired
    private ICrmBusinessUserStarService crmBusinessUserStarService;

    @Autowired
    private ICrmCustomerService crmCustomerService;

    @Autowired
    private ActionRecordUtil actionRecordUtil;

    @Autowired
    private AdminFileService adminFileService;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private FieldService fieldService;

    @Autowired
    private RabbitMQSender rabbitMQSender;

    /**
     * 大的搜索框的搜索字段
     *
     * @return fields
     */
    @Override
    public String[] appendSearch() {
        return new String[]{"businessName"};
    }

    /**
     * 获取crm列表类型
     *
     * @return data
     */
    @Override
    public CrmTypeEnum getLabel() {
        return CrmTypeEnum.BUSINESS;
    }

    /**
     * 查询所有字段
     *
     * @return data
     */
    @Override
    public List<CrmModelFiledVO> queryDefaultField() {
        List<CrmModelFiledVO> filedList = crmFieldService.queryField(getLabel().getType());
        filedList.add(new CrmModelFiledVO("typeId", FieldEnum.SELECT, 1));
        filedList.add(new CrmModelFiledVO("statusId", FieldEnum.SELECT, 1));
        filedList.add(new CrmModelFiledVO("lastTime", FieldEnum.DATETIME, 1));
        filedList.add(new CrmModelFiledVO("updateTime", FieldEnum.DATETIME, 1));
        filedList.add(new CrmModelFiledVO("receiveTime", FieldEnum.DATETIME, 1));
        filedList.add(new CrmModelFiledVO("nextTime", FieldEnum.DATETIME, 1));
        filedList.add(new CrmModelFiledVO("createTime", FieldEnum.DATETIME, 1));
        filedList.add(new CrmModelFiledVO("ownerUserId", FieldEnum.USER, 1));
        filedList.add(new CrmModelFiledVO("createUserId", FieldEnum.USER, 1));
        filedList.add(new CrmModelFiledVO("status", FieldEnum.TEXT, 1));
        filedList.add(new CrmModelFiledVO("teamMemberIds", FieldEnum.USER, 0));
        return filedList;
    }

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


    private List<CrmModelFiledVO> queryField(Integer id,boolean appendInformation){
        CrmModel crmModel = queryById(id);
        if (id != null) {
            List<JSONObject> customerList = new ArrayList<>();
            if (crmModel.get("customerId")!=null){
                JSONObject customer = new JSONObject();
                customerList.add(customer.fluentPut("customerId", crmModel.get("customerId")).fluentPut("customerName", crmModel.get("customerName")));
            }
            crmModel.put("customerId", customerList);
        }
        List<CrmModelFiledVO> crmModelFiledVOS = crmFieldService.queryField(crmModel);
        Optional<CrmModelFiledVO> optional = crmModelFiledVOS.stream().filter(record -> "remark".equals(record.getFieldName())).findFirst();
        optional.ifPresent(crmModelFiledVOS::remove);
        crmModelFiledVOS.add(new CrmModelFiledVO().setFieldName("type_id").setName("商机状态组").setValue(crmModel.get("type_id")).setFormType("business_type").setSetting(new ArrayList<>()).setIsNull(1).setFieldType(1).setValue(crmModel.get("typeId")).setAuthLevel(3));
        Object statusId = crmModel.get("statusId");
        if(!Objects.equals(0,crmModel.get("isEnd"))){
            statusId = crmModel.get("isEnd");
        }
        crmModelFiledVOS.add(new CrmModelFiledVO().setFieldName("status_id").setName("商机阶段").setValue(crmModel.get("status_id")).setFormType("business_status").setSetting(new ArrayList<>()).setIsNull(1).setFieldType(1).setValue(statusId).setAuthLevel(3));
        optional.ifPresent(crmModelFiledVOS::add);
        JSONObject object = new JSONObject();
        object.fluentPut("discountRate", crmModel.get("discountRate")).fluentPut("product", crmBusinessProductService.queryList(id)).fluentPut("totalPrice", crmModel.get("totalPrice"));
        crmModelFiledVOS.add(new CrmModelFiledVO().setFieldName("product").setName("产品").setValue(object).setFormType("product").setSetting(new ArrayList<>()).setIsNull(0).setFieldType(1));
        if(appendInformation){
            List<CrmModelFiledVO> modelFiledVOS = appendInformation(crmModel);
            crmModelFiledVOS.addAll(modelFiledVOS);
        }
        return crmModelFiledVOS;

    }



    @Override
    public List<List<CrmModelFiledVO>> queryFormPositionField(Integer id) {
        CrmModel crmModel = queryById(id);
        if (id != null) {
            List<JSONObject> customerList = new ArrayList<>();
            if (crmModel.get("customerId")!=null){
                JSONObject customer = new JSONObject();
                customerList.add(customer.fluentPut("customerId", crmModel.get("customerId")).fluentPut("customerName", crmModel.get("customerName")));
            }
            crmModel.put("customerId", customerList);
        }
        List<List<CrmModelFiledVO>> crmModelFiledVOS = crmFieldService.queryFormPositionFieldVO(crmModel);
       /* Optional<CrmModelFiledVO> optional = Optional.empty();
        for (List<CrmModelFiledVO> crmModelFiledVOList : crmModelFiledVOS) {
            optional = crmModelFiledVOList.stream().filter(record -> "remark".equals(record.getFieldName())).findFirst();
        }
        if (optional.isPresent()){
            for (List<CrmModelFiledVO> crmModelFiledVOList : crmModelFiledVOS) {
                Optional<CrmModelFiledVO> finalOptional = optional;
                crmModelFiledVOList.removeIf(record -> Objects.equals(finalOptional.get().getFieldId(),record.getFieldId()));
            }
        }*/
        CrmModelFiledVO modelFiledVO = new CrmModelFiledVO().setFieldName("type_id").setName("商机状态组").setValue(crmModel.get("type_id")).setFormType("business_type").setSetting(new ArrayList<>()).setIsNull(1).setFieldType(1).setValue(crmModel.get("typeId")).setAuthLevel(3);
        Object statusId = crmModel.get("statusId");
        if(!Objects.equals(0,crmModel.get("isEnd"))){
            statusId = crmModel.get("isEnd");
        }
        CrmModelFiledVO crmModelFiledVO = new CrmModelFiledVO().setFieldName("status_id").setName("商机阶段").setValue(crmModel.get("status_id")).setFormType("business_status").setSetting(new ArrayList<>()).setIsNull(1).setFieldType(1).setValue(statusId).setAuthLevel(3);
        //默认两个一组
        modelFiledVO.setStylePercent(50);
        crmModelFiledVO.setStylePercent(50);
        crmModelFiledVOS.add(ListUtil.toList(modelFiledVO,crmModelFiledVO));

        JSONObject object = new JSONObject();
        object.fluentPut("discountRate", crmModel.get("discountRate")).fluentPut("product", crmBusinessProductService.queryList(id)).fluentPut("totalPrice", crmModel.get("totalPrice"));
        CrmModelFiledVO filedVO = new CrmModelFiledVO().setFieldName("product").setName("产品").setValue(object).setFormType("product").setSetting(new ArrayList<>()).setIsNull(0).setFieldType(1);
       /* optional.ifPresent(vo -> {
            vo.setStylePercent(100);
            crmModelFiledVOS.add(ListUtil.toList(vo));
        });*/
        filedVO.setStylePercent(100);
        crmModelFiledVOS.add(ListUtil.toList(filedVO));
        return crmModelFiledVOS;
    }

    @Autowired
    private ICrmBusinessTypeService businessTypeService;

    /**
     * 查询商机列表页数据
     *
     * @param crmSearchBO
     * @return
     */
    @Override
    public BasePage<Map<String, Object>> queryPageList(CrmSearchBO crmSearchBO) {
        CrmSearchBO search = ObjectUtil.cloneByStream(crmSearchBO);
        BasePage<Map<String, Object>> basePage = queryList(crmSearchBO,false);
        Long userId = UserUtil.getUserId();
        List<Integer> starIds = crmBusinessUserStarService.starList(userId);
        basePage.getList().forEach(map -> {
            map.put("star", starIds.contains((Integer)map.get("businessId"))?1:0);
            Integer isEnd = TypeUtils.castToInt(map.get("isEnd"));
            CrmListBusinessStatusVO crmListBusinessStatusVO = businessTypeService.queryListBusinessStatus(TypeUtils.castToInt(map.get("typeId")), TypeUtils.castToInt(map.get("statusId")), isEnd);
            map.put("businessStatusCount",crmListBusinessStatusVO);
            if(Objects.equals(1,isEnd)) {
                map.put("statusName","赢单");
            } else if(Objects.equals(2,isEnd)) {
                map.put("statusName","输单");
            } else if(Objects.equals(3,isEnd)) {
                map.put("statusName","无效");
            }
        });
        SearchRequest searchRequest = new SearchRequest(getIndex());
        searchRequest.types(getDocType());
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder queryBuilder = createQueryBuilder(search);
        sourceBuilder.query(queryBuilder);
        sourceBuilder.aggregation(AggregationBuilders.sum("businessSumMoney").field("money"));
        searchRequest.source(sourceBuilder);
        try {
            SearchResponse searchCount = elasticsearchRestTemplate.getClient().search(searchRequest, RequestOptions.DEFAULT);
            Aggregations aggregations = searchCount.getAggregations();
            Map<String, Object> countMap = new HashMap<>();
            ParsedSum businessSumMoney = aggregations.get("businessSumMoney");
            countMap.put("businessSumMoney", businessSumMoney.getValue());
            basePage.setExtraData(new JSONObject().fluentPut("money", countMap));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return basePage;
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
            crmModel = getBaseMapper().queryById(id, UserUtil.getUserId());
            crmModel.setLabel(CrmTypeEnum.BUSINESS.getType());
            crmModel.setOwnerUserName(UserCacheUtil.getUserName(crmModel.getOwnerUserId()));
            crmModel.put("createUserName", UserCacheUtil.getUserName((Long) crmModel.get("createUserId")));
            crmBusinessDataService.setDataByBatchId(crmModel);
            List<String> stringList = ApplicationContextHolder.getBean(ICrmRoleFieldService.class).queryNoAuthField(crmModel.getLabel());
            stringList.forEach(crmModel::remove);
        } else {
            crmModel = new CrmModel(CrmTypeEnum.BUSINESS.getType());
        }
        return crmModel;
    }


    /**
     * 保存或新增商机信息
     *TODO 保存到ES改成异步
     * @param crmModel model
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdate(CrmBusinessSaveBO crmModel) {
        CrmBusiness crmBusiness = BeanUtil.copyProperties(crmModel.getEntity(), CrmBusiness.class);
        List<CrmBusinessProduct> businessProductList = crmModel.getProduct();
        String batchId = StrUtil.isNotEmpty(crmBusiness.getBatchId()) ? crmBusiness.getBatchId() : IdUtil.simpleUUID();
        actionRecordUtil.updateRecord(crmModel.getField(), Dict.create().set("batchId", batchId).set("dataTableName", "wk_crm_business_data"));
        crmBusinessDataService.saveData(crmModel.getField(), batchId);
        if (crmBusiness.getBusinessId() != null) {
            CrmBusiness oldBusiness = getById(crmBusiness.getBusinessId());
            if(Objects.equals(crmBusiness.getTypeId(),oldBusiness.getTypeId())){
               /*
                不修改销售阶段
                */
                crmBusiness.setStatusId(null);
            }
            actionRecordUtil.updateRecord(BeanUtil.beanToMap(oldBusiness), BeanUtil.beanToMap(crmBusiness), CrmTypeEnum.BUSINESS, crmBusiness.getBusinessName(), crmBusiness.getBusinessId());
            updateById(crmBusiness);
            crmBusinessProductService.deleteByBusinessId(crmBusiness.getBusinessId());
            crmBusiness = getById(crmBusiness.getBusinessId());
            ElasticUtil.batchUpdateEsData(elasticsearchRestTemplate.getClient(), "business", crmBusiness.getBusinessId().toString(), crmBusiness.getBusinessName());
        } else
        {
            crmBusiness.setOwnerUserId(UserUtil.getUserId());
            crmBusiness.setIsEnd(0);
            crmBusiness.setBatchId(batchId);
            save(crmBusiness);
            if (crmModel.getContactsId() != null) {
                crmContactsBusinessService.save(crmBusiness.getBusinessId(), crmModel.getContactsId());
            }
            crmActivityService.addActivity(2, CrmActivityEnum.BUSINESS, crmBusiness.getBusinessId());
            actionRecordUtil.addRecord(crmBusiness.getBusinessId(), CrmTypeEnum.BUSINESS, crmBusiness.getBusinessName());
        }
        for (CrmBusinessProduct crmBusinessProduct : businessProductList) {
            crmBusinessProduct.setBusinessId(crmBusiness.getBusinessId());
        }
        crmBusinessProductService.save(businessProductList);
        crmModel.setEntity(BeanUtil.beanToMap(crmBusiness));
        savePage(crmModel, crmBusiness.getBusinessId(), false);
//        String esMessage = JSON.toJSONString(new EsMessageBO(crmModel, crmBusiness.getBusinessId(), false));
//        rabbitMQSender.sendMessage(esMessage);
    }

    @Override
    public void setOtherField(Map<String, Object> map) {
        String ownerUserName = UserCacheUtil.getUserName((Long) map.get("ownerUserId"));
        map.put("ownerUserName", ownerUserName);
        String createUserName = UserCacheUtil.getUserName((Long) map.get("createUserId"));
        map.put("createUserName", createUserName);
        String customerName = crmCustomerService.getCustomerName((Integer) map.get("customerId"));
        map.put("customerName", customerName);
        ICrmBusinessStatusService businessStatusService = ApplicationContextHolder.getBean(ICrmBusinessStatusService.class);
        ICrmBusinessTypeService businessTypeService = ApplicationContextHolder.getBean(ICrmBusinessTypeService.class);
        Integer typeId = (Integer) map.get("typeId");
        Integer statusId = (Integer) map.get("statusId");
        CrmBusinessType crmBusinessType = businessTypeService.query().select("name").eq("type_id", typeId).one();
        map.put("typeName", crmBusinessType != null ? crmBusinessType.getName() : "");
        CrmBusinessStatus crmBusinessStatus = businessStatusService.query().select("name").eq("status_id", statusId).one();
        map.put("statusName", crmBusinessStatus != null ? crmBusinessStatus.getName() : "");
    }

    @Override
    public Dict getSearchTransferMap() {
        return Dict.create().set("customerId", "customerName").set("customer_id","customerId");
    }

    /**
     * 删除商机数据
     *
     * @param ids ids
     */
    @Override
    public void deleteByIds(List<Integer> ids) {
        ids.forEach(id -> {
            if (AuthUtil.isRwAuth(id, CrmTypeEnum.BUSINESS, CrmAuthEnum.DELETE)) {
                throw new CrmException(SystemCodeEnum.SYSTEM_NO_AUTH);
            }
        });
        int number = crmContractService.lambdaQuery().in(CrmContract::getBusinessId, ids).ne(CrmContract::getCheckStatus, 7).count();
        if (number > 0) {
            throw new CrmException(CrmCodeEnum.CRM_DATA_JOIN_ERROR);
        }
        LambdaQueryWrapper<CrmBusiness> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(CrmBusiness::getBatchId);
        wrapper.in(CrmBusiness::getBusinessId, ids);
        List<String> batchIdList = listObjs(wrapper, Object::toString);

        //删除跟进记录
        crmActivityService.deleteActivityRecord(CrmActivityEnum.BUSINESS, ids);
        //删除字段操作记录
        crmActionRecordService.deleteActionRecord(CrmTypeEnum.BUSINESS, ids);
        //todo 文件相关暂不处理
        //删除商机和联系人关联
        crmContactsBusinessService.removeByBusinessId(ids.toArray(new Integer[0]));
        //删除商机产品关联
        crmBusinessProductService.deleteByBusinessId(ids.toArray(new Integer[0]));
        //删除自定义字段
        crmBusinessDataService.deleteByBatchId(batchIdList);
        //修改商机状态
        LambdaUpdateWrapper<CrmBusiness> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(CrmBusiness::getStatus, 3);
        updateWrapper.in(CrmBusiness::getBusinessId, ids);
        update(updateWrapper);
        deletePage(ids);

    }

    @Override
    public BasePage<CrmContacts> queryContacts(CrmContactsPageBO pageEntity) {
        BasePage<CrmContacts> contactsBasePage = pageEntity.parse();
        return getBaseMapper().queryContacts(contactsBasePage, pageEntity.getBusinessId());
    }

    /**
     * 查询详情信息
     *
     * @param businessId 商机id
     * @return data
     */
    @Override
    public List<CrmModelFiledVO> information(Integer businessId) {
        return queryField(businessId,true);
    }

    /**
     * 修改商机负责人
     *
     * @param changOwnerUserBO data
     */
    @Override
    public void changeOwnerUser(CrmChangeOwnerUserBO changOwnerUserBO) {
        if (changOwnerUserBO.getIds().size() == 0) {
            return;
        }
        String ownerUserName = UserCacheUtil.getUserName(changOwnerUserBO.getOwnerUserId());
        changOwnerUserBO.getIds().forEach(id -> {
            if (AuthUtil.isChangeOwnerUserAuth(id, CrmTypeEnum.BUSINESS,CrmAuthEnum.EDIT)) {
                throw new CrmException(SystemCodeEnum.SYSTEM_NO_AUTH);
            }
            CrmBusiness business = getById(id);
            if (Objects.equals(2, changOwnerUserBO.getTransferType()) && !Objects.equals(business.getOwnerUserId(), changOwnerUserBO.getOwnerUserId())) {
                ApplicationContextHolder.getBean(ICrmTeamMembersService.class).addSingleMember(getLabel(),business.getBusinessId(),business.getOwnerUserId(),changOwnerUserBO.getPower(),changOwnerUserBO.getExpiresTime(),business.getBusinessName());
            }
            ApplicationContextHolder.getBean(ICrmTeamMembersService.class).deleteMember(getLabel(),new CrmMemberSaveBO(id,changOwnerUserBO.getOwnerUserId()));
            business.setOwnerUserId(changOwnerUserBO.getOwnerUserId());
            updateById(business);
            actionRecordUtil.addConversionRecord(id, CrmTypeEnum.BUSINESS, changOwnerUserBO.getOwnerUserId(), business.getBusinessName());
        });
        //修改es
        Map<String, Object> map = new HashMap<>();
        map.put("ownerUserId", changOwnerUserBO.getOwnerUserId());
        map.put("ownerUserName", ownerUserName);
        updateField(map, changOwnerUserBO.getIds());
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
                return "商机";
            }
        },headList,response);
    }

    /**
     * 标星
     *
     * @param businessId 商机id
     */
    @Override
    public void star(Integer businessId) {
        LambdaQueryWrapper<CrmBusinessUserStar> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CrmBusinessUserStar::getBusinessId, businessId);
        wrapper.eq(CrmBusinessUserStar::getUserId, UserUtil.getUserId());
        CrmBusinessUserStar star = crmBusinessUserStarService.getOne(wrapper);
        if (star == null) {
            star = new CrmBusinessUserStar();
            star.setBusinessId(businessId);
            star.setUserId(UserUtil.getUserId());
            crmBusinessUserStarService.save(star);
        } else {
            crmBusinessUserStarService.removeById(star.getId());
        }
    }

    /**
     * 查询文件数量
     *
     * @param businessId id
     * @return data
     */
    @Override
    public CrmInfoNumVO num(Integer businessId) {
        CrmBusiness crmBusiness = getById(businessId);
        String conditions = AuthUtil.getCrmAuthSql(CrmTypeEnum.CONTRACT, 1,CrmAuthEnum.READ);
        String contactsConditions = AuthUtil.getCrmAuthSql(CrmTypeEnum.CONTACTS, "b", 1,CrmAuthEnum.READ);
        Map<String, Object> map = new HashMap<>();
        map.put("businessId", businessId);
        map.put("conditions", conditions);
        map.put("contactsConditions", contactsConditions);
        CrmInfoNumVO infoNumVO = getBaseMapper().queryNum(map);
        List<CrmField> crmFields = crmFieldService.queryFileField();
        List<String> batchIdList = new ArrayList<>();
        if (crmFields.size() > 0) {
            LambdaQueryWrapper<CrmBusinessData> wrapper = new LambdaQueryWrapper<>();
            wrapper.select(CrmBusinessData::getValue);
            wrapper.eq(CrmBusinessData::getBatchId, crmBusiness.getBatchId());
            wrapper.in(CrmBusinessData::getFieldId, crmFields.stream().map(CrmField::getFieldId).collect(Collectors.toList()));
            batchIdList.addAll(crmBusinessDataService.listObjs(wrapper, Object::toString));
        }
        batchIdList.add(crmBusiness.getBatchId());
        batchIdList.addAll(crmActivityService.queryFileBatchId(crmBusiness.getBusinessId(), getLabel().getType()));
        infoNumVO.setFileCount(adminFileService.queryNum(batchIdList).getData());
        infoNumVO.setMemberCount(ApplicationContextHolder.getBean(ICrmTeamMembersService.class).queryMemberCount(getLabel(),crmBusiness.getBusinessId(),crmBusiness.getOwnerUserId()));
        Integer productCount = crmBusinessProductService.lambdaQuery().eq(CrmBusinessProduct::getBusinessId, businessId).count();
        infoNumVO.setProductCount(productCount);
        return infoNumVO;
    }

    /**
     * 查询文件列表
     *
     * @param businessId id
     * @return file
     */
    @Override
    public List<FileEntity> queryFileList(Integer businessId) {
        List<FileEntity> fileEntityList = new ArrayList<>();
        CrmBusiness crmBusiness = getById(businessId);
        adminFileService.queryFileList(crmBusiness.getBatchId()).getData().forEach(fileEntity -> {
            fileEntity.setSource("附件上传");
            fileEntity.setReadOnly(0);
            fileEntityList.add(fileEntity);
        });
        List<CrmField> crmFields = crmFieldService.queryFileField();
        if (crmFields.size() > 0) {
            LambdaQueryWrapper<CrmBusinessData> wrapper = new LambdaQueryWrapper<>();
            wrapper.select(CrmBusinessData::getValue);
            wrapper.eq(CrmBusinessData::getBatchId, crmBusiness.getBatchId());
            wrapper.in(CrmBusinessData::getFieldId, crmFields.stream().map(CrmField::getFieldId).collect(Collectors.toList()));
            List<FileEntity> data = adminFileService.queryFileList(crmBusinessDataService.listObjs(wrapper, Object::toString)).getData();
            data.forEach(fileEntity -> {
                fileEntity.setSource("商机详情");
                fileEntity.setReadOnly(1);
                fileEntityList.add(fileEntity);
            });
        }
        List<String> stringList = crmActivityService.queryFileBatchId(crmBusiness.getBusinessId(), getLabel().getType());
        if (stringList.size() > 0) {
            List<FileEntity> data = adminFileService.queryFileList(stringList).getData();
            data.forEach(fileEntity -> {
                fileEntity.setSource("跟进记录");
                fileEntity.setReadOnly(1);
                fileEntityList.add(fileEntity);
            });
        }
        return fileEntityList;
    }

    /**
     * 设置首要联系人
     *
     * @param contactsBO data
     */
    @Override
    public void setContacts(CrmFirstContactsBO contactsBO) {
        CrmBusiness crmBusiness = getById(contactsBO.getBusinessId());
        crmBusiness.setContactsId(contactsBO.getContactsId());
        updateById(crmBusiness);
    }

    /**
     * 商机关联联系人
     *
     * @param relevanceBusinessBO 业务对象
     */
    @Override
    public void relateContacts(CrmRelevanceBusinessBO relevanceBusinessBO) {
        relevanceBusinessBO.getContactsIds().forEach(id -> {
            crmContactsBusinessService.save(relevanceBusinessBO.getBusinessId(), id);
        });
    }

    /**
     * 商机解除关联联系人
     *
     * @param relevanceBusinessBO 业务对象
     */
    @Override
    public void unrelateContacts(CrmRelevanceBusinessBO relevanceBusinessBO) {
        CrmBusiness crmBusiness = getById(relevanceBusinessBO.getBusinessId());
        relevanceBusinessBO.getContactsIds().forEach(r -> {
            if (Objects.equals(r, crmBusiness.getContactsId())) {
                lambdaUpdate().set(CrmBusiness::getContactsId,null).eq(CrmBusiness::getBusinessId, crmBusiness.getBusinessId()).update();
            }
        });
        LambdaQueryWrapper<CrmContactsBusiness> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CrmContactsBusiness::getBusinessId, relevanceBusinessBO.getBusinessId());
        wrapper.in(CrmContactsBusiness::getContactsId, relevanceBusinessBO.getContactsIds());
        crmContactsBusinessService.remove(wrapper);
    }

    @Override
    public List<SimpleCrmEntity> querySimpleEntity(List<Integer> ids) {
        if (ids.size() == 0) {
            return new ArrayList<>();
        }
        List<CrmBusiness> list = lambdaQuery().select(CrmBusiness::getBusinessId,CrmBusiness::getBusinessName).in(CrmBusiness::getBusinessId, ids).list();
        return list.stream().map(crmBusiness -> {
            SimpleCrmEntity simpleCrmEntity = new SimpleCrmEntity();
            simpleCrmEntity.setId(crmBusiness.getBusinessId());
            simpleCrmEntity.setName(crmBusiness.getBusinessName());
            return simpleCrmEntity;
        }).collect(Collectors.toList());
    }

    @Override
    public String getBusinessName(int businessId) {
        Optional<CrmBusiness> crmBusiness = lambdaQuery().select(CrmBusiness::getBusinessName).eq(CrmBusiness::getBusinessId, businessId).oneOpt();
        return crmBusiness.map(CrmBusiness::getBusinessName).orElse("");
    }


    @Override
    public void updateInformation(CrmUpdateInformationBO updateInformationBO) {
        String batchId = updateInformationBO.getBatchId();
        Integer businessId = updateInformationBO.getId();
        updateInformationBO.getList().forEach(record -> {
            CrmBusiness oldBusiness = getById(updateInformationBO.getId());
            uniqueFieldIsAbnormal(record.getString("name"),record.getInteger("fieldId"),record.getString("value"),batchId);
            Map<String, Object> oldBusinessMap = BeanUtil.beanToMap(oldBusiness);
            if (record.getInteger("fieldType") == 1) {
                Map<String, Object> crmBusinessMap = new HashMap<>(oldBusinessMap);
                crmBusinessMap.put(record.getString("fieldName"), record.get("value"));
                CrmBusiness crmBusiness = BeanUtil.mapToBean(crmBusinessMap, CrmBusiness.class, true);
                actionRecordUtil.updateRecord(oldBusinessMap, crmBusinessMap, CrmTypeEnum.BUSINESS, crmBusiness.getBusinessName(), crmBusiness.getBusinessId());
                update().set(StrUtil.toUnderlineCase(record.getString("fieldName")), record.get("value")).eq("business_id",updateInformationBO.getId()).update();
                if ("businessName".equals(record.getString("fieldName"))) {
                    ElasticUtil.batchUpdateEsData(elasticsearchRestTemplate.getClient(), "business", crmBusiness.getBusinessId().toString(), crmBusiness.getBusinessName());
                }
            } else if (record.getInteger("fieldType") == 0 || record.getInteger("fieldType") == 2) {

                CrmBusinessData businessData = crmBusinessDataService.lambdaQuery().select(CrmBusinessData::getValue,CrmBusinessData::getId).eq(CrmBusinessData::getFieldId, record.getInteger("fieldId"))
                        .eq(CrmBusinessData::getBatchId, batchId).one();
                String value = businessData != null ? businessData.getValue() : null;
                actionRecordUtil.publicContentRecord(CrmTypeEnum.BUSINESS, BehaviorEnum.UPDATE, businessId, oldBusiness.getBusinessName(), record,value);
                String newValue = fieldService.convertObjectValueToString(record.getInteger("type"),record.get("value"),record.getString("value"));

                CrmBusinessData crmBusinessData = new CrmBusinessData();
                crmBusinessData.setId(businessData != null ? businessData.getId() : null);
                crmBusinessData.setFieldId(record.getInteger("fieldId"));
                crmBusinessData.setName(record.getString("fieldName"));
                crmBusinessData.setValue(newValue);
                crmBusinessData.setCreateTime(new Date());
                crmBusinessData.setBatchId(batchId);
                crmBusinessDataService.saveOrUpdate(crmBusinessData);

            }
            updateField(record, businessId);
        });
        this.lambdaUpdate().set(CrmBusiness::getUpdateTime,new Date()).eq(CrmBusiness::getBusinessId,businessId).update();
    }

    @Override
    public JSONObject queryProduct(CrmBusinessQueryRelationBO businessQueryProductBO) {
        Integer businessId = businessQueryProductBO.getBusinessId();
        CrmBusiness business = getById(businessId);
        JSONObject record = getBaseMapper().querySubtotalByBusinessId(businessId);
        record.put("money", business.getTotalPrice());
        BasePage<JSONObject> page = getBaseMapper().queryProduct(businessQueryProductBO.parse(), businessId);
        record.put("list", page.getList());
        return record;
    }

    @Override
    public BasePage<JSONObject> queryContract(CrmBusinessQueryRelationBO businessQueryRelationBO) {
        String conditions = AuthUtil.getCrmAuthSql(CrmTypeEnum.BUSINESS, "a", 1,CrmAuthEnum.READ);
        Integer businessId = businessQueryRelationBO.getBusinessId();
        BasePage<JSONObject> page = getBaseMapper().queryContract(businessQueryRelationBO.parse(), businessId, conditions);
        return page;
    }

    @Override
    public List<String> eventDealBusiness(CrmEventBO crmEventBO) {
        return getBaseMapper().eventDealBusiness(crmEventBO);
    }

    @Override
    public BasePage<Map<String, Object>> eventDealBusinessPageList(QueryEventCrmPageBO eventCrmPageBO) {
        Long userId = eventCrmPageBO.getUserId();
        Long time = eventCrmPageBO.getTime();
        if (userId == null) {
            userId = UserUtil.getUserId();
        }
        List<Integer> businessIds = getBaseMapper().eventDealBusinessPageList(userId, new Date(time));
        if (businessIds.size() == 0) {
            return new BasePage<>();
        }
        List<String> collect = businessIds.stream().map(Object::toString).collect(Collectors.toList());
        CrmSearchBO crmSearchBO = new CrmSearchBO();
        crmSearchBO.setSearchList(Collections.singletonList(new CrmSearchBO.Search("_id", "text", CrmSearchBO.FieldSearchEnum.ID, collect)));
        crmSearchBO.setLabel(CrmTypeEnum.CUSTOMER.getType());
        crmSearchBO.setPage(eventCrmPageBO.getPage());
        crmSearchBO.setLimit(eventCrmPageBO.getLimit());
        BasePage<Map<String, Object>> page = queryPageList(crmSearchBO);
        return page;
    }

    @Override
    public List<String> eventBusiness(CrmEventBO crmEventBO) {
        return getBaseMapper().eventBusiness(crmEventBO);
    }

    @Override
    public BasePage<Map<String, Object>> eventBusinessPageList(QueryEventCrmPageBO eventCrmPageBO) {
        Long userId = eventCrmPageBO.getUserId();
        Long time = eventCrmPageBO.getTime();
        if (userId == null) {
            userId = UserUtil.getUserId();
        }
        List<Integer> businessIds = getBaseMapper().eventBusinessPageList(userId, new Date(time));
        if (businessIds.size() == 0) {
            return new BasePage<>();
        }
        List<String> collect = businessIds.stream().map(Object::toString).collect(Collectors.toList());
        CrmSearchBO crmSearchBO = new CrmSearchBO();
        crmSearchBO.setSearchList(Collections.singletonList(new CrmSearchBO.Search("_id", "text", CrmSearchBO.FieldSearchEnum.ID, collect)));
        crmSearchBO.setLabel(CrmTypeEnum.CUSTOMER.getType());
        crmSearchBO.setPage(eventCrmPageBO.getPage());
        crmSearchBO.setLimit(eventCrmPageBO.getLimit());
        BasePage<Map<String, Object>> page = queryPageList(crmSearchBO);
        return page;
    }
}
