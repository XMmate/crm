package com.liujiaming.crm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.TypeUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liujiaming.core.common.Const;
import com.liujiaming.core.common.SystemCodeEnum;
import com.liujiaming.core.common.cache.CrmCacheKey;
import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.exception.CrmException;
import com.liujiaming.core.feign.admin.entity.AdminConfig;
import com.liujiaming.core.feign.admin.service.AdminService;
import com.liujiaming.core.feign.examine.service.ExamineService;
import com.liujiaming.core.redis.service.Redis;
import com.liujiaming.core.servlet.ApplicationContextHolder;
import com.liujiaming.core.utils.UserUtil;
import com.liujiaming.crm.common.AuthUtil;
import com.liujiaming.crm.common.ElasticUtil;
import com.liujiaming.crm.constant.CrmBackLogEnum;
import com.liujiaming.crm.constant.CrmCodeEnum;
import com.liujiaming.crm.constant.CrmModelEnum;
import com.liujiaming.crm.constant.CrmTypeEnum;
import com.liujiaming.crm.entity.BO.CrmBackLogBO;
import com.liujiaming.crm.entity.BO.CrmSearchBO;
import com.liujiaming.crm.entity.PO.*;
import com.liujiaming.crm.mapper.CrmBackLogMapper;
import com.liujiaming.crm.service.*;
import com.liujiaming.crm.entity.PO.*;
import com.liujiaming.crm.service.*;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 待办事项 服务类接口
 *
 * @author liujiaming
 * @since 2024-05-23
 */
@Service
@Slf4j
public class CrmBackLogServiceImpl implements ICrmBackLogService {

    @Autowired
    private Redis redis;

    @Autowired
    private CrmBackLogMapper crmBackLogMapper;

    @Autowired
    private ICrmCustomerPoolService customerPoolService;

    @Autowired
    private ICrmBackLogDealService backLogDealService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private ICrmCustomerService crmCustomerService;

    @Autowired
    private ElasticsearchRestTemplate restTemplate;

    @Autowired
    private ICrmLeadsService crmLeadsService;

    @Autowired
    private ICrmBusinessService crmBusinessService;

    @Autowired
    private ExamineService examineService;

    @Autowired
    private ICrmContractService crmContractService;

    @Autowired
    private ICrmReceivablesService crmReceivablesService;

    @Autowired
    private ICrmInvoiceService crmInvoiceService;

    /**
     * 查询待办事项数量
     *
     * @return num
     */
    @Override
    public JSONObject num() {
        Long userId = UserUtil.getUserId();
        JSONObject kv = redis.get(CrmCacheKey.CRM_BACKLOG_NUM_CACHE_KEY + userId.toString());
        if (kv != null) {
            return kv;
        } else {
            kv = new JSONObject();
        }
        List<String> authList = UserUtil.getUser().getAuthoritiesUrlList();
        authList.add("crm:customer:index");
        authList.add("crm:leads:index");
        authList.add("crm:contract:index");
        authList.add("crm:receivables:index");
        authList.add("crm:receivables:index");
        authList.add("crm:invoice:index");
        Map<String, Object> paras = new HashMap<>();
        paras.put("userId", userId);
        //客户
        if (authList.contains("crm:customer:index")) {
            Integer todayLeads = crmBackLogMapper.todayLeadsNum(paras);
            Integer todayCustomer =crmBackLogMapper.todayCustomerNum(paras);
            Integer todayBusiness = crmBackLogMapper.todayBusinessNum(paras);
            Integer followCustomer = crmBackLogMapper.followCustomerNum(paras);
            kv.put("todayLeads", todayLeads);
            kv.put("todayCustomer", todayCustomer);
            kv.put("todayBusiness", todayBusiness);
            kv.put("followCustomer", followCustomer);
            List<CrmCustomerPool> poolList = customerPoolService.lambdaQuery().eq(CrmCustomerPool::getStatus, 1).eq(CrmCustomerPool::getPutInRule, 1).eq(CrmCustomerPool::getRemindSetting, 1).list();
            Set<Integer> customerIdSet = new HashSet<>();
            poolList.forEach(pool -> {
                List<JSONObject> recordList = new ArrayList<>();
                List<CrmCustomerPoolRule> ruleList = ApplicationContextHolder.getBean(ICrmCustomerPoolRuleService.class).lambdaQuery().eq(CrmCustomerPoolRule::getPoolId, pool.getPoolId()).list();
                for (CrmCustomerPoolRule rule : ruleList) {
                    Map<String, Object> record = BeanUtil.beanToMap(rule);
                    record.put("remindDay", pool.getRemindDay());
                    record.put("ids", Collections.singletonList(userId));
                    if (rule.getType().equals(1)) {
                        recordList.addAll(crmBackLogMapper.putInPoolByRecord(record));
                    } else if (rule.getType().equals(2)) {
                        recordList.addAll(crmBackLogMapper.putInPoolByBusiness(record));
                    } else if (rule.getType().equals(3)) {
                        Integer startDay = rule.getLimitDay() - pool.getRemindDay();
                        record.put("startDay", startDay);
                        recordList.addAll(crmBackLogMapper.putInPoolByDealStatus(record));
                    }
                }
                List<Integer> customerIdList = recordList.stream().map(record -> record.getInteger("customerId")).collect(Collectors.toList());

                ICrmBackLogDealService logDealService = ApplicationContextHolder.getBean(ICrmBackLogDealService.class);
                LambdaQueryWrapper<CrmBackLogDeal> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(CrmBackLogDeal::getModel, 4);
                //todo 9是什么
                wrapper.eq(CrmBackLogDeal::getCrmType, 9);
                wrapper.eq(CrmBackLogDeal::getCreateUserId, userId);
                wrapper.select(CrmBackLogDeal::getTypeId);
                List<Integer> dealIdList = logDealService.listObjs(wrapper, obj -> Integer.valueOf(obj.toString()));
                customerIdList.removeAll(dealIdList);
                customerIdSet.addAll(customerIdList);
            });
            if (poolList.size() > 0) {
                kv.put("putInPoolRemind", customerIdSet.size());
            }
        }
        //线索
        if (authList.contains("crm:leads:index")) {
            Integer followLeads = crmBackLogMapper.followLeadsNum(paras);
            kv.put("followLeads", followLeads);
        }
        //合同
        if (authList.contains("crm:contract:index")) {
            AdminConfig adminConfig = adminService.queryFirstConfigByName("expiringContractDays").getData();
            if (1 == adminConfig.getStatus()) {
                paras.put("remindDay", adminConfig.getValue());
                Integer endContract = crmBackLogMapper.endContractNum(paras);
                kv.put("endContract", endContract);
            }
            List<Integer> ids = examineService.queryCrmExamineIdList(1, 1).getData();
            Integer checkContract = null;
            if (CollUtil.isNotEmpty(ids)) {
                LambdaQueryWrapper<CrmBackLogDeal> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(CrmBackLogDeal::getModel, 5);
                wrapper.eq(CrmBackLogDeal::getCrmType, CrmTypeEnum.CONTRACT.getType());
                wrapper.eq(CrmBackLogDeal::getCreateUserId, userId);
                wrapper.select(CrmBackLogDeal::getTypeId);
                List<Integer> dealIdList = backLogDealService.listObjs(wrapper, TypeUtils::castToInt);
                ids.removeAll(dealIdList);
                if (ids.size()>0) {
                    checkContract = crmContractService.lambdaQuery().in(CrmContract::getContractId, ids).in(CrmContract::getCheckStatus, 0, 3).count();
                }
            }
            if (checkContract == null) {
                checkContract = 0;
            }
            kv.put("checkContract", checkContract);

            AdminConfig returnVisitRemindConfig = adminService.queryFirstConfigByName("returnVisitRemindConfig").getData();
            if (Objects.equals(1, returnVisitRemindConfig.getStatus())) {
                paras.put("remindDay", returnVisitRemindConfig.getValue());
                Integer returnVisitRemind = crmBackLogMapper.returnVisitRemindNum(paras);
                kv.put("returnVisitRemind", returnVisitRemind);
            }
        }
        /**
         * 回款
         */
        if (authList.contains("crm:receivables:index")) {
            Integer remindReceivablesPlan = crmBackLogMapper.remindReceivablesPlanNum(paras);
            List<Integer> ids = examineService.queryCrmExamineIdList(2, 1).getData();
            Integer checkReceivables = null;
            if (CollUtil.isNotEmpty(ids)) {
                LambdaQueryWrapper<CrmBackLogDeal> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(CrmBackLogDeal::getModel, CrmModelEnum.PENDING_PAYMENT_APPROVAL.getModelID());
                wrapper.eq(CrmBackLogDeal::getCrmType, CrmTypeEnum.RECEIVABLES.getType());
                wrapper.eq(CrmBackLogDeal::getCreateUserId, userId);
                wrapper.select(CrmBackLogDeal::getTypeId);
                List<Integer> dealIdList = backLogDealService.listObjs(wrapper, TypeUtils::castToInt);
                ids.removeAll(dealIdList);
                if (ids.size() > 0) {
                    checkReceivables = crmReceivablesService.lambdaQuery().in(CrmReceivables::getReceivablesId, ids).in(CrmReceivables::getCheckStatus, 0, 3).count();
                }
            }
            if (checkReceivables == null) {
                checkReceivables = 0;
            }
            kv.put("checkReceivables", checkReceivables);
            kv.put("remindReceivablesPlan", remindReceivablesPlan);
        }
        //发票
        if (authList.contains("crm:invoice:index")) {
            List<Integer> ids = examineService.queryCrmExamineIdList(3, 1).getData();
            Integer checkInvoice = null;
            if (CollUtil.isNotEmpty(ids)) {
                LambdaQueryWrapper<CrmBackLogDeal> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(CrmBackLogDeal::getModel, 10);
                wrapper.eq(CrmBackLogDeal::getCrmType, CrmTypeEnum.INVOICE.getType());
                wrapper.eq(CrmBackLogDeal::getCreateUserId, userId);
                wrapper.select(CrmBackLogDeal::getTypeId);
                List<Integer> dealIdList = backLogDealService.listObjs(wrapper, TypeUtils::castToInt);
                ids.removeAll(dealIdList);
                if(ids.size() > 0){
                    checkInvoice = crmInvoiceService.lambdaQuery().in(CrmInvoice::getInvoiceId, ids).in(CrmInvoice::getCheckStatus, 0, 3).count();
                }
            }
            if (checkInvoice == null) {
                checkInvoice = 0;
            }
            kv.put("checkInvoice", checkInvoice);
        }


        redis.setex(CrmCacheKey.CRM_BACKLOG_NUM_CACHE_KEY + userId.toString(), 600, kv);
        return kv;
    }


    @Override
    public BasePage<Map<String, Object>> todayLeads(CrmBackLogBO crmBackLogBO) {
        setCrmBackLogBO(crmBackLogBO, CrmTypeEnum.LEADS);
        CrmSearchBO searchBO = new CrmSearchBO();
        searchBO.setLabel(CrmTypeEnum.LEADS.getType());
        searchBO.setPage(crmBackLogBO.getPage());
        searchBO.setLimit(crmBackLogBO.getLimit());
        searchBO.setSearchList(crmBackLogBO.getData());
        BasePage<Map<String, Object>> basePage = crmLeadsService.queryPageList(searchBO);
        Integer overtimeNum = crmBackLogMapper.todayOvertimeNum(getOvertimeQueryData(CrmTypeEnum.LEADS));
        basePage.setExtraData(Collections.singletonMap("overtime",overtimeNum));
        return basePage;
    }

    /**
     * 今日需联系客户
     *
     * @return data
     */
    @Override
    public BasePage<Map<String, Object>> todayCustomer(CrmBackLogBO crmBackLogBO) {
        setCrmBackLogBO(crmBackLogBO, CrmTypeEnum.CUSTOMER);
        CrmSearchBO searchBO = new CrmSearchBO();
        searchBO.setLabel(CrmTypeEnum.CUSTOMER.getType());
        searchBO.setPage(crmBackLogBO.getPage());
        searchBO.setLimit(crmBackLogBO.getLimit());
        searchBO.setSearchList(crmBackLogBO.getData());
        BasePage<Map<String, Object>> basePage = crmCustomerService.queryPageList(searchBO);
        Integer overtimeNum = crmBackLogMapper.todayOvertimeNum(getOvertimeQueryData(CrmTypeEnum.CUSTOMER));
        basePage.setExtraData(Collections.singletonMap("overtime",overtimeNum));
        return basePage;
    }

    @Override
    public BasePage<Map<String, Object>> todayBusiness(CrmBackLogBO crmBackLogBO) {
        setCrmBackLogBO(crmBackLogBO, CrmTypeEnum.BUSINESS);
        CrmSearchBO searchBO = new CrmSearchBO();
        searchBO.setLabel(CrmTypeEnum.BUSINESS.getType());
        searchBO.setPage(crmBackLogBO.getPage());
        searchBO.setLimit(crmBackLogBO.getLimit());
        searchBO.setSearchList(crmBackLogBO.getData());
        BasePage<Map<String, Object>> basePage = crmBusinessService.queryPageList(searchBO);
        if(basePage.getExtraData() != null && basePage.getExtraData() instanceof JSONObject){
            Integer overtimeNum = crmBackLogMapper.todayOvertimeNum(getOvertimeQueryData(CrmTypeEnum.BUSINESS));
            ((JSONObject) basePage.getExtraData()).put("overtime",overtimeNum);
        }
        return basePage;
    }

    private void setCrmBackLogBO(CrmBackLogBO crmBackLogBO, CrmTypeEnum crmTypeEnum) {
        Integer type = crmBackLogBO.getType();
        Integer isSub = crmBackLogBO.getIsSub();
        if (type == 1) {
            CrmSearchBO.Search search = new CrmSearchBO.Search("nextTime", "datetime", CrmSearchBO.FieldSearchEnum.RANGE);
            search.setValues(Collections.singletonList("today"));
            crmBackLogBO.getData().add(search);
            crmBackLogBO.getData().add(new CrmSearchBO.Search("lastTime", "datetime", CrmSearchBO.FieldSearchEnum.SCRIPT).setScript(new Script(ScriptType.INLINE, "painless", "if (doc['lastTime'].size()==0) {return false} else {return doc['lastTime'].value.toInstant().toEpochMilli() < doc['nextTime'].value.toInstant().toEpochMilli()}", new HashMap<>())));
        } else if (type == 2) {
            crmBackLogBO.getData().add(new CrmSearchBO.Search("nextTime", "datetime", CrmSearchBO.FieldSearchEnum.LT, Collections.singletonList(DateUtil.date().toString())));
            crmBackLogBO.getData().add(new CrmSearchBO.Search("lastTime", "datetime", CrmSearchBO.FieldSearchEnum.SCRIPT).setScript(new Script(ScriptType.INLINE, "painless", "if (doc['lastTime'].size()==0) {return false} else {return doc['lastTime'].value.toInstant().toEpochMilli() < doc['nextTime'].value.toInstant().toEpochMilli()}", new HashMap<>())));
        } else if (type == 3) {
            CrmSearchBO.Search search = new CrmSearchBO.Search("lastTime", "datetime", CrmSearchBO.FieldSearchEnum.RANGE);
            search.setValues(Collections.singletonList("today"));
            crmBackLogBO.getData().add(search);
            crmBackLogBO.getData().add(new CrmSearchBO.Search("lastTime", "datetime", CrmSearchBO.FieldSearchEnum.SCRIPT).setScript(new Script(ScriptType.INLINE, "painless", "if (doc['lastTime'].size()==0) {return false} else {return doc['lastTime'].value.toInstant().toEpochMilli() <= doc['nextTime'].value.toInstant().toEpochMilli()}", new HashMap<>())));
        } else {
            throw new CrmException(SystemCodeEnum.SYSTEM_NO_VALID);
        }
        if (isSub == 1) {
            crmBackLogBO.getData().add(new CrmSearchBO.Search("ownerUserId", "text", CrmSearchBO.FieldSearchEnum.IS, Collections.singletonList(UserUtil.getUserId().toString())));
        } else if (isSub == 2) {
            List<String> collect = adminService.queryChildUserId(UserUtil.getUserId()).getData().stream().map(Object::toString).collect(Collectors.toList());
            crmBackLogBO.getData().add(new CrmSearchBO.Search("ownerUserId", "text", CrmSearchBO.FieldSearchEnum.IS, collect));
        } else {
            throw new CrmException(SystemCodeEnum.SYSTEM_NO_VALID);
        }
        if (type == 1 && isSub == 1) {
            CrmBackLogEnum model = CrmBackLogEnum.TODAY_CUSTOMER;
            if (crmTypeEnum.equals(CrmTypeEnum.LEADS)) {
                model = CrmBackLogEnum.TODAY_LEADS;
            } else if (crmTypeEnum.equals(CrmTypeEnum.BUSINESS)) {
                model = CrmBackLogEnum.TODAY_BUSINESS;
            }
            List<String> dealIdList = backLogDealService.queryTypeId(model.getType(), crmTypeEnum.getType(), UserUtil.getUserId());
            crmBackLogBO.getData().add(new CrmSearchBO.Search("_id", "text", CrmSearchBO.FieldSearchEnum.IS_NOT, dealIdList));
        }
    }

    private Map<String, Object> getOvertimeQueryData(CrmTypeEnum crmTypeEnum) {
        Map<String, Object> map = new HashMap<>(6);
        map.put("table", crmTypeEnum.getTableName());
        map.put("type", crmTypeEnum.getType());
        map.put("userId", UserUtil.getUserId());
        map.put("model", CrmBackLogEnum.valueOf("TODAY_"+crmTypeEnum.name()).getType());
        return map;
    }

    /**
     * 待回访合同
     *
     * @param crmBackLogBO bo
     * @return data
     */
    @Override
    public BasePage<Map<String, Object>> returnVisitRemind(CrmBackLogBO crmBackLogBO) {
        Integer isSub = crmBackLogBO.getIsSub();
        List<Long> userIds = new ArrayList<>();
        if (isSub == 1) {
            userIds.add(UserUtil.getUserId());
        } else if (isSub == 2) {
            userIds.addAll(adminService.queryChildUserId(UserUtil.getUserId()).getData());
        } else {
            throw new CrmException(SystemCodeEnum.SYSTEM_NO_VALID);
        }
        AdminConfig adminConfig = adminService.queryFirstConfigByName("returnVisitRemindConfig").getData();
        List<String> contractIdList = crmBackLogMapper.returnVisitRemind(StrUtil.join(Const.SEPARATOR, userIds), adminConfig.getValue());
        if (isSub == 1) {
            List<String> dealIdList = backLogDealService.queryTypeId(9, 6, UserUtil.getUserId());
            contractIdList.removeAll(dealIdList);
        }
        if (contractIdList.size() == 0) {
            return new BasePage<>();
        }
        crmBackLogBO.getData().add(new CrmSearchBO.Search("_id", "id", CrmSearchBO.FieldSearchEnum.ID, contractIdList));
        CrmSearchBO searchBO = new CrmSearchBO();
        searchBO.setLabel(CrmTypeEnum.CONTRACT.getType());
        searchBO.setPage(crmBackLogBO.getPage());
        searchBO.setLimit(crmBackLogBO.getLimit());
        searchBO.setSearchList(crmBackLogBO.getData());
        return ApplicationContextHolder.getBean(ICrmContractService.class).queryPageList(searchBO);
    }

    /**
     * 分配给我的线索
     *
     * @param crmBackLogBO data
     * @return data
     */
    @Override
    public BasePage<Map<String, Object>> followLeads(CrmBackLogBO crmBackLogBO) {
        ICrmLeadsService crmLeadsService = ApplicationContextHolder.getBean(ICrmLeadsService.class);
        Integer type = crmBackLogBO.getType();
        crmBackLogBO.getData().add(new CrmSearchBO.Search("followup", "text", CrmSearchBO.FieldSearchEnum.IS, Collections.singletonList(Objects.equals(2,type)?"1":"0")));
        List<String> ids;
        if(Objects.equals(2,crmBackLogBO.getIsSub())) {
            List<Long> data = adminService.queryChildUserId(UserUtil.getUserId()).getData();
            ids = data.stream().map(Objects::toString).collect(Collectors.toList());
        } else {
            ids = Collections.singletonList(UserUtil.getUserId().toString());
        }
        crmBackLogBO.getData().add(new CrmSearchBO.Search("ownerUserId", "text", CrmSearchBO.FieldSearchEnum.IS, ids));
        crmBackLogBO.getData().add(new CrmSearchBO.Search("isReceive", "text", CrmSearchBO.FieldSearchEnum.IS, Collections.singletonList("1")));
        if (type == 1) {
            List<String> dealIdList = backLogDealService.queryTypeId(2, CrmTypeEnum.LEADS.getType(), UserUtil.getUserId());
            crmBackLogBO.getData().add(new CrmSearchBO.Search("_id", "text", CrmSearchBO.FieldSearchEnum.IS_NOT, dealIdList));
        }
        CrmSearchBO searchBO = new CrmSearchBO();
        searchBO.setLabel(CrmTypeEnum.LEADS.getType());
        searchBO.setPage(crmBackLogBO.getPage());
        searchBO.setLimit(crmBackLogBO.getLimit());
        searchBO.setSearchList(crmBackLogBO.getData());
        return crmLeadsService.queryPageList(searchBO);
    }

    /**
     * 分配给我的客户
     *
     * @param crmBackLogBO
     * @return data
     */
    @Override
    public BasePage<Map<String, Object>> followCustomer(CrmBackLogBO crmBackLogBO) {
        Integer type = crmBackLogBO.getType();
        crmBackLogBO.getData().add(new CrmSearchBO.Search("followup", "text", CrmSearchBO.FieldSearchEnum.IS, Collections.singletonList(Objects.equals(2,type)?"1":"0")));
        List<String> ids;
        if(Objects.equals(2,crmBackLogBO.getIsSub())) {
            List<Long> data = adminService.queryChildUserId(UserUtil.getUserId()).getData();
            ids = data.stream().map(Objects::toString).collect(Collectors.toList());
        } else {
            ids = Collections.singletonList(UserUtil.getUserId().toString());
        }
        crmBackLogBO.getData().add(new CrmSearchBO.Search("ownerUserId", "text", CrmSearchBO.FieldSearchEnum.IS, ids));
        crmBackLogBO.getData().add(new CrmSearchBO.Search("isReceive", "text", CrmSearchBO.FieldSearchEnum.IS, Collections.singletonList("1")));
        crmBackLogBO.getData().add(new CrmSearchBO.Search("status", "text", CrmSearchBO.FieldSearchEnum.IS, Collections.singletonList("1")));
        if (type == 1) {
            List<String> dealIdList = backLogDealService.queryTypeId(2, CrmTypeEnum.CUSTOMER.getType(), UserUtil.getUserId());
            crmBackLogBO.getData().add(new CrmSearchBO.Search("_id", "text", CrmSearchBO.FieldSearchEnum.IS_NOT, dealIdList));
        }
        CrmSearchBO searchBO = new CrmSearchBO();
        searchBO.setLabel(CrmTypeEnum.CUSTOMER.getType());
        searchBO.setPage(crmBackLogBO.getPage());
        searchBO.setLimit(crmBackLogBO.getLimit());
        searchBO.setSearchList(crmBackLogBO.getData());
        return crmCustomerService.queryPageList(searchBO);
    }

    /**
     * 标记线索为已跟进
     *
     * @param ids data
     */
    @Override
    public void setLeadsFollowup(List<Integer> ids) {
        if (ids.size() == 0) {
            return;
        }
        ICrmLeadsService crmLeadsService = ApplicationContextHolder.getBean(ICrmLeadsService.class);
        crmLeadsService.lambdaUpdate().set(CrmLeads::getFollowup, 1).in(CrmLeads::getLeadsId, ids).update();
        ElasticUtil.updateField(restTemplate, "followup", "1", ids, CrmTypeEnum.LEADS.getIndex());
        redis.del(CrmCacheKey.CRM_BACKLOG_NUM_CACHE_KEY + UserUtil.getUserId().toString());
    }

    /**
     * 标记客户为已跟进
     */
    @Override
    public void setCustomerFollowup(List<Integer> ids) {
        if (ids.size() == 0) {
            return;
        }
        ICrmCustomerService crmCustomerService = ApplicationContextHolder.getBean(ICrmCustomerService.class);
        crmCustomerService.lambdaUpdate().set(CrmCustomer::getFollowup, 1).in(CrmCustomer::getCustomerId, ids).update();
        ElasticUtil.updateField(restTemplate, "followup", "1", ids, CrmTypeEnum.CUSTOMER.getIndex());
        redis.del(CrmCacheKey.CRM_BACKLOG_NUM_CACHE_KEY + UserUtil.getUserId().toString());
    }

    /**
     * 待审核合同
     */
    @Override
    public BasePage<Map<String, Object>> checkContract(CrmBackLogBO crmBackLogBO) {
        CrmTypeEnum crmTypeEnum = CrmTypeEnum.CONTRACT;
        Integer type = crmBackLogBO.getType();
        List<String> ids = new ArrayList<>();
        List<Integer> idList = examineService.queryCrmExamineIdList(1, type).getData();
        if (CollUtil.isNotEmpty(idList)) {
            ids = idList.stream().map(String::valueOf).collect(Collectors.toList());
            if (type == 1) {
                List<String> dealIdList = backLogDealService.queryTypeId(5, crmTypeEnum.getType(), UserUtil.getUserId());
                ids.removeIf(dealIdList::contains);
            }
        }
        if (ids.size() == 0) {
            return new BasePage<>();
        }
        crmBackLogBO.getData().add(new CrmSearchBO.Search("_id", "text", CrmSearchBO.FieldSearchEnum.ID, ids));
        CrmSearchBO searchBO = new CrmSearchBO();
        searchBO.setLabel(crmTypeEnum.getType());
        searchBO.setPage(crmBackLogBO.getPage());
        searchBO.setLimit(crmBackLogBO.getLimit());
        searchBO.setSearchList(crmBackLogBO.getData());
        return ApplicationContextHolder.getBean(ICrmContractService.class).queryPageList(searchBO);
    }

    /**
     * 待审核回款
     */
    @Override
    public BasePage<Map<String, Object>> checkReceivables(CrmBackLogBO crmBackLogBO) {
        CrmTypeEnum crmTypeEnum = CrmTypeEnum.RECEIVABLES;
        Integer type = crmBackLogBO.getType();
        List<String> ids = new ArrayList<>();
        List<Integer> idList = examineService.queryCrmExamineIdList(2, type).getData();
        if (CollUtil.isNotEmpty(idList)) {
            ids = idList.stream().map(String::valueOf).collect(Collectors.toList());
            if (type == 1) {
                List<String> dealIdList = backLogDealService.queryTypeId(6, crmTypeEnum.getType(), UserUtil.getUserId());
                ids.removeIf(dealIdList::contains);
            }
        }
        if (ids.size() == 0) {
            return new BasePage<>();
        }
        crmBackLogBO.getData().add(new CrmSearchBO.Search("_id", "text", CrmSearchBO.FieldSearchEnum.ID, ids));
        CrmSearchBO searchBO = new CrmSearchBO();
        searchBO.setLabel(crmTypeEnum.getType());
        searchBO.setPage(crmBackLogBO.getPage());
        searchBO.setLimit(crmBackLogBO.getLimit());
        searchBO.setSearchList(crmBackLogBO.getData());
        return ApplicationContextHolder.getBean(ICrmReceivablesService.class).queryPageList(searchBO);
    }

    /**
     * 待审核发票
     */
    @Override
    public BasePage<Map<String, Object>> checkInvoice(CrmBackLogBO crmBackLogBO) {
        CrmTypeEnum crmTypeEnum = CrmTypeEnum.INVOICE;
        Integer type = crmBackLogBO.getType();
        List<String> ids = new ArrayList<>();
        List<Integer> idList = examineService.queryCrmExamineIdList(3, type).getData();
        if (CollUtil.isNotEmpty(idList)) {
            ids = idList.stream().map(String::valueOf).collect(Collectors.toList());
            if (type == 1) {
                List<String> dealIdList = backLogDealService.queryTypeId(10, crmTypeEnum.getType(), UserUtil.getUserId());
                ids.removeIf(dealIdList::contains);
            }
        }
        if (ids.size() == 0) {
            return new BasePage<>();
        }
        crmBackLogBO.getData().add(new CrmSearchBO.Search("_id", "text", CrmSearchBO.FieldSearchEnum.ID, ids));
        CrmSearchBO searchBO = new CrmSearchBO();
        searchBO.setLabel(crmTypeEnum.getType());
        searchBO.setPage(crmBackLogBO.getPage());
        searchBO.setLimit(crmBackLogBO.getLimit());
        searchBO.setSearchList(crmBackLogBO.getData());
        return ApplicationContextHolder.getBean(ICrmInvoiceService.class).queryPageList(searchBO);
    }

    /**
     * 待回款提醒
     */
    @Override
    public BasePage<CrmReceivablesPlan> remindReceivables(CrmBackLogBO crmBackLogBO) {
        Integer type = crmBackLogBO.getType();
        Long userId = UserUtil.getUserId();
        List<String> ids = new ArrayList<>();
        if (type == 1) {
            ids.addAll(backLogDealService.queryTypeId(7, 8, userId));
        }
        BasePage<CrmReceivablesPlan> basePage = crmBackLogMapper.remindReceivables(crmBackLogBO.parse(), type, ids, userId);
        basePage.getList().forEach(record -> {
            record.setCustomerName(crmCustomerService.getCustomerName(record.getCustomerId()));
        });
        Integer overtimeNum =crmBackLogMapper.remindReceivablesOvertimeNum(DateUtil.beginOfDay(new Date()), userId);
        basePage.setExtraData(Collections.singletonMap("overtime",overtimeNum));
        return basePage;
    }

    /**
     * 即将到期的合同
     */
    @Override
    public BasePage<Map<String, Object>> endContract(CrmBackLogBO crmBackLogBO) {
        Integer type = crmBackLogBO.getType();
        AdminConfig adminConfig = adminService.queryFirstConfigByName("expiringContractDays").getData();
        if (adminConfig == null || Objects.equals(0, adminConfig.getStatus())) {
            return new BasePage<>();
        }
        CrmTypeEnum crmTypeEnum = CrmTypeEnum.CONTRACT;
        ICrmContractService crmContractService = ApplicationContextHolder.getBean(ICrmContractService.class);
        if (type == 1) {
            crmBackLogBO.getData().add(new CrmSearchBO.Search("endTime", "date", CrmSearchBO.FieldSearchEnum.EGT, Collections.singletonList(DateUtil.formatDate(new Date()))));
            crmBackLogBO.getData().add(new CrmSearchBO.Search("endTime", "date", CrmSearchBO.FieldSearchEnum.ELT, Collections.singletonList(DateUtil.formatDate(new Date(System.currentTimeMillis() + Integer.valueOf(adminConfig.getValue()) * 86400000L)))));
        } else if (type == 2) {
            crmBackLogBO.getData().add(new CrmSearchBO.Search("endTime", "date", CrmSearchBO.FieldSearchEnum.LT, Collections.singletonList(DateUtil.formatDate(new Date()))));
        } else {
            throw new CrmException(SystemCodeEnum.SYSTEM_NO_VALID);
        }
        crmBackLogBO.getData().add(new CrmSearchBO.Search("ownerUserId", "text", CrmSearchBO.FieldSearchEnum.IS, Collections.singletonList(UserUtil.getUserId().toString())));
        crmBackLogBO.getData().add(new CrmSearchBO.Search("checkStatus", "text", CrmSearchBO.FieldSearchEnum.IS, Collections.singletonList("1")));
        if (type == 1) {
            List<String> dealIdList = backLogDealService.queryTypeId(8, crmTypeEnum.getType(), UserUtil.getUserId());
            crmBackLogBO.getData().add(new CrmSearchBO.Search("_id", "text", CrmSearchBO.FieldSearchEnum.IS_NOT, dealIdList));
        }
        CrmSearchBO searchBO = new CrmSearchBO();
        searchBO.setLabel(crmTypeEnum.getType());
        searchBO.setPage(crmBackLogBO.getPage());
        searchBO.setLimit(crmBackLogBO.getLimit());
        searchBO.setSearchList(crmBackLogBO.getData());
        return crmContractService.queryPageList(searchBO);
    }

    /**
     * 全部标记为已处理
     *
     * @param model model
     */
    @Override
    public void allDeal(Integer model) {
        int crmType = 0;
        Long userId = UserUtil.getUserId();
        List<CrmBackLogDeal> backLogDealList = new ArrayList<>();
        List<Integer> idList = new ArrayList<>();
        List<String> dealIdList = new ArrayList<>();
        switch (CrmBackLogEnum.parse(model)) {
            case TODAY_CUSTOMER:
                crmType = 2;
                dealIdList = backLogDealService.queryTypeId(model, crmType, userId);
                idList = crmBackLogMapper.queryTodayCustomerId(userId);
                break;
            case FOLLOW_LEADS:
                crmType = 1;
                dealIdList = backLogDealService.queryTypeId(model, crmType, userId);
                idList = crmBackLogMapper.queryFollowLeadsId(userId);
                break;
            case FOLLOW_CUSTOMER:
                crmType = 2;
                dealIdList = backLogDealService.queryTypeId(model, crmType, userId);
                idList = crmBackLogMapper.queryFollowCustomerId(userId);
                break;
            case TO_ENTER_CUSTOMER_POOL:
                List<CrmCustomerPool> poolList = ApplicationContextHolder.getBean(ICrmCustomerPoolService.class)
                        .lambdaQuery().eq(CrmCustomerPool::getStatus, 1)
                        .eq(CrmCustomerPool::getPutInRule, 1)
                        .eq(CrmCustomerPool::getRemindSetting, 1).list();
                if (poolList.size() == 0) {
                    throw new CrmException(CrmCodeEnum.CRM_CUSTOMER_POOL_REMIND_ERROR);
                }
                poolList.forEach(pool -> {
                    List<Long> userIdsList = new ArrayList<>();
                    Set<Integer> customerIdSet = new HashSet<>();
                    List<JSONObject> recordList = new ArrayList<>();
                    if (StrUtil.isNotEmpty(pool.getMemberDeptId())) {
                        userIdsList = adminService.queryUserByDeptIds(StrUtil.splitTrim(pool.getMemberDeptId(), Const.SEPARATOR).stream().map(Integer::valueOf).collect(Collectors.toList())).getData();
                    }
                    if (StrUtil.isNotEmpty(pool.getMemberUserId())) {
                        userIdsList.addAll(Arrays.stream(pool.getMemberUserId().split(Const.SEPARATOR)).map(Long::parseLong).collect(Collectors.toList()));
                    }
                    List<CrmCustomerPoolRule> ruleList = ApplicationContextHolder.getBean(ICrmCustomerPoolRuleService.class).lambdaQuery().eq(CrmCustomerPoolRule::getPoolId, pool.getPoolId()).list();
                    for (CrmCustomerPoolRule rule : ruleList) {
                        Map<String, Object> record = BeanUtil.beanToMap(rule);
                        record.put("remindDay", pool.getRemindDay());
                        record.put("userIds", userId);
                        if (CollectionUtil.isNotEmpty(userIdsList)) {
                            record.put("ids", AuthUtil.filterUserId(userIdsList));
                        }
                        if (rule.getType().equals(1)) {
                            recordList.addAll(crmBackLogMapper.putInPoolByRecord(record));
                        } else if (rule.getType().equals(2)) {
                            recordList.addAll(crmBackLogMapper.putInPoolByBusiness(record));
                        } else if (rule.getType().equals(3)) {
                            Integer startDay = rule.getLimitDay() - pool.getRemindDay();
                            record.put("startDay", startDay);
                            recordList.addAll(crmBackLogMapper.putInPoolByDealStatus(record));
                        }
                    }
                    List<Integer> dealIdsInPool =crmBackLogMapper.queryDealIdByPoolId(userId, CrmTypeEnum.CUSTOMER_POOL.getType(), model, pool.getPoolId());
                    customerIdSet = recordList.stream().map(record -> record.getInteger("customerId")).collect(Collectors.toSet());
                    customerIdSet.forEach(customerId -> {
                        if (!dealIdsInPool.contains(customerId)) {
                            CrmBackLogDeal backLogDeal = new CrmBackLogDeal();
                            backLogDeal.setModel(model).setPoolId(pool.getPoolId()).setCrmType(CrmTypeEnum.CUSTOMER_POOL.getType()).setTypeId(customerId).setCreateTime(new Date()).setCreateUserId(userId);
                            backLogDealList.add(backLogDeal);
                        }
                    });
                });
                break;
            case CHECK_CONTRACT:
                crmType = 6;
                dealIdList = backLogDealService.queryTypeId(model, crmType, userId);
                idList = examineService.queryCrmExamineIdList(1, 1).getData();
                break;
            case CHECK_RECEIVABLES:
                crmType = 7;
                dealIdList = backLogDealService.queryTypeId(model, crmType, userId);
                idList = examineService.queryCrmExamineIdList(2, 1).getData();
                break;
            case CHECK_INVOICE:
                crmType = 18;
                dealIdList = backLogDealService.queryTypeId(model, crmType, userId);
                idList = examineService.queryCrmExamineIdList(3, 1).getData();
                break;
            case REMIND_RECEIVABLES_PLAN:
                crmType = 8;
                dealIdList = backLogDealService.queryTypeId(model, crmType, userId);
                idList = crmBackLogMapper.queryRemindReceivablesPlanId(userId);
                break;
            case END_CONTRACT:
                AdminConfig adminConfig = adminService.queryFirstConfigByName("expiringContractDays").getData();
                if (adminConfig.getStatus() == 0) {
                    throw new CrmException(CrmCodeEnum.CRM_CONTRACT_EXPIRATION_REMIND_ERROR);
                }
                crmType = 6;
                dealIdList = backLogDealService.queryTypeId(model, crmType, userId);
                idList = crmBackLogMapper.queryEndContractId(userId, Integer.valueOf(adminConfig.getValue()));
                break;
            case REMIND_RETURN_VISIT_CONTRACT:
                AdminConfig returnVisitRemindConfig = adminService.queryFirstConfigByName("returnVisitRemindConfig").getData();
                if (returnVisitRemindConfig.getStatus() == 0) {
                    throw new CrmException(CrmCodeEnum.CRM_RETURN_VISIT_REMIND_ERROR);
                }
                crmType = 6;
                dealIdList = backLogDealService.queryTypeId(model, crmType, userId);
                idList = crmBackLogMapper.queryReturnVisitContractId(userId, Integer.valueOf(returnVisitRemindConfig.getValue()));
                break;
            case TODAY_LEADS:
                crmType = 1;
                dealIdList = backLogDealService.queryTypeId(model, crmType, userId);
                idList = crmBackLogMapper.queryTodayLeadsId(userId);
                break;
            case TODAY_BUSINESS:
                crmType = 5;
                dealIdList = backLogDealService.queryTypeId(model, crmType, userId);
                idList = crmBackLogMapper.queryTodayBusinessId(userId);
                break;
            default:
                break;
        }
        if (crmType != CrmTypeEnum.CUSTOMER_POOL.getType()) {
            idList.removeAll(dealIdList.stream().map(Integer::valueOf).collect(Collectors.toList()));
            for (Integer id : idList) {
                CrmBackLogDeal backLogDeal = new CrmBackLogDeal();
                backLogDeal.setModel(model).setCrmType(crmType).setTypeId(id).setCreateTime(new Date()).setCreateUserId(userId);
                backLogDealList.add(backLogDeal);
            }
        }
        backLogDealService.saveBatch(backLogDealList);
        redis.del(CrmCacheKey.CRM_BACKLOG_NUM_CACHE_KEY + UserUtil.getUserId().toString());
    }

    /**
     * 部分标记为已处理
     *
     * @param model model
     */
    @Override
    public void dealById(Integer model, List<JSONObject> jsonObjectList) {
        Long userId = UserUtil.getUserId();
        int crmType = 0;
        switch (CrmBackLogEnum.parse(model)) {
            case FOLLOW_LEADS:
            case TODAY_LEADS:
                crmType = 1;
                break;
            case TODAY_BUSINESS:
                crmType = 5;
                break;
            case TODAY_CUSTOMER:
            case FOLLOW_CUSTOMER:
                crmType = 2;
                break;
            case CHECK_CONTRACT:
            case END_CONTRACT:
            case REMIND_RETURN_VISIT_CONTRACT:
                crmType = 6;
                break;
            case CHECK_RECEIVABLES:
                crmType = 7;
                break;
            case REMIND_RECEIVABLES_PLAN:
                crmType = 8;
                break;
            case TO_ENTER_CUSTOMER_POOL:
                crmType = 9;
                break;
            case CHECK_INVOICE:
                crmType = 18;
                break;
            default:
                break;
        }
        List<CrmBackLogDeal> backLogDealList = new ArrayList<>();
        List<Integer> ids  =  new ArrayList<>();
        for (JSONObject jsonObject : jsonObjectList) {
            if (crmType == CrmTypeEnum.CUSTOMER_POOL.getType()) {
                for (String poolId : jsonObject.getString("poolIds").split(",")) {
                    CrmBackLogDeal backLogDeal = new CrmBackLogDeal();
                    backLogDeal.setModel(model).setCrmType(crmType).setTypeId(jsonObject.getInteger("typeId")).setPoolId(Integer.valueOf(poolId)).setCreateUserId(userId).setCreateTime(new Date());
                    backLogDealList.add(backLogDeal);
                }
            } else {
                CrmBackLogDeal backLogDeal = new CrmBackLogDeal();
                backLogDeal.setModel(model).setCrmType(crmType).setTypeId(jsonObject.getInteger("typeId")).setCreateUserId(userId).setCreateTime(new Date());
                backLogDealList.add(backLogDeal);
            }
            ids.add(jsonObject.getInteger("typeId"));
        }
        backLogDealService.saveBatch(backLogDealList);
        redis.del(CrmCacheKey.CRM_BACKLOG_NUM_CACHE_KEY + UserUtil.getUserId().toString());
        if (crmType == CrmTypeEnum.CUSTOMER.getType()){
            setCustomerFollowup(ids);
        }
    }

    @Override
    public BasePage<Map<String, Object>> putInPoolRemind(CrmBackLogBO crmBackLogBO) {
        Integer isSub = crmBackLogBO.getIsSub();
        Long userId = UserUtil.getUserId();
        if (isSub == 1) {
            crmBackLogBO.getData().add(new CrmSearchBO.Search("ownerUserId", "text", CrmSearchBO.FieldSearchEnum.IS, Collections.singletonList(UserUtil.getUserId().toString())));
        } else if (isSub == 2) {
            List<String> collect = adminService.queryChildUserId(UserUtil.getUserId()).getData().stream().map(Object::toString).collect(Collectors.toList());
            crmBackLogBO.getData().add(new CrmSearchBO.Search("ownerUserId", "text", CrmSearchBO.FieldSearchEnum.IS, collect));
        } else {
            throw new CrmException(SystemCodeEnum.SYSTEM_NO_VALID);
        }
        List<CrmCustomerPool> poolList = customerPoolService.lambdaQuery().eq(CrmCustomerPool::getStatus, 1).eq(CrmCustomerPool::getPutInRule, 1).eq(CrmCustomerPool::getRemindSetting, 1).list();
        if (poolList.size() == 0) {
            return new BasePage<>();
        }
        Map<Integer, Integer> customerMap = new HashMap<>();
        Map<Integer, Set<Integer>> remindPoolMap = new HashMap<>();
        poolList.forEach(pool -> {
            Set<Long> userIdsList = new HashSet<>();
            List<JSONObject> recordList = new ArrayList<>();
            List<Integer> customerIdList = new ArrayList<>();
            List<Integer> deptIds = StrUtil.splitTrim(pool.getMemberDeptId(), Const.SEPARATOR).stream().map(Integer::valueOf).collect(Collectors.toList());
            if (deptIds.size() > 0) {
                userIdsList.addAll(adminService.queryUserByDeptIds(deptIds).getData());
            }
            if (StrUtil.isNotEmpty(pool.getMemberUserId())) {
                userIdsList.addAll(Arrays.stream(pool.getMemberUserId().split(Const.SEPARATOR)).map(Long::parseLong).collect(Collectors.toList()));
            }
            if (isSub == 1) {
                userIdsList.add(userId);
            } else {
                userIdsList.remove(userId);
            }
            List<CrmCustomerPoolRule> ruleList = ApplicationContextHolder.getBean(ICrmCustomerPoolRuleService.class).lambdaQuery().eq(CrmCustomerPoolRule::getPoolId, pool.getPoolId()).list();
            for (CrmCustomerPoolRule rule : ruleList) {
                Map<String, Object> record = BeanUtil.beanToMap(rule);
                record.put("remindDay", pool.getRemindDay());
                userIdsList.add(userId);
                record.put("ids", AuthUtil.filterUserId(new ArrayList<>(userIdsList)));
                if (rule.getType().equals(1)) {
                    recordList.addAll(crmBackLogMapper.putInPoolByRecord(record));
                } else if (rule.getType().equals(2)) {
                    recordList.addAll(crmBackLogMapper.putInPoolByBusiness(record));
                } else if (rule.getType().equals(3)) {
                    Integer startDay = rule.getLimitDay() - pool.getRemindDay();
                    record.put("startDay", startDay);
                    recordList.addAll(crmBackLogMapper.putInPoolByDealStatus(record));
                }
                recordList.forEach(r -> {
                    Integer customerId = r.getInteger("customerId");
                    customerIdList.add(customerId);
                    if (customerMap.containsKey(customerId) && customerMap.get(customerId) <= r.getInteger("poolDay")) {
                        return;
                    }
                    customerMap.put(customerId, r.getInteger("poolDay"));
                });
            }
            if (isSub == 1) {
                List<CrmBackLogDeal> list = backLogDealService.lambdaQuery().select(CrmBackLogDeal::getTypeId)
                        .eq(CrmBackLogDeal::getModel, 4).eq(CrmBackLogDeal::getCrmType, 9).eq(CrmBackLogDeal::getCreateUserId, userId)
                        .eq(CrmBackLogDeal::getPoolId, pool.getPoolId()).list();
                List<Integer> dealIdList = list.stream().map(CrmBackLogDeal::getTypeId).collect(Collectors.toList());
                customerIdList.removeAll(dealIdList);
            }
            //此处是为了记录客户id是由于哪些公海规则提醒的，返回给前端，当单个标记已处理时，提交回来，保存到数据库待办事项提醒表里
            customerIdList.forEach(customerId -> {
                Set<Integer> poolIdSet = new HashSet<>();
                if (remindPoolMap.containsKey(customerId)) {
                    poolIdSet = remindPoolMap.get(customerId);
                    poolIdSet.add(pool.getPoolId());
                } else {
                    poolIdSet.add(pool.getPoolId());
                }
                remindPoolMap.put(customerId, poolIdSet);
            });
        });
        Set<Integer> customerIdSet = remindPoolMap.keySet();
        if (customerIdSet.size() == 0) {
            return new BasePage<>();
        }
        List<String> collect = customerIdSet.stream().map(Objects::toString).collect(Collectors.toList());
        crmBackLogBO.getData().add(new CrmSearchBO.Search("_id", "text", CrmSearchBO.FieldSearchEnum.ID, collect));
        CrmSearchBO searchBO = new CrmSearchBO();
        searchBO.setPage(crmBackLogBO.getPage());
        searchBO.setLimit(crmBackLogBO.getLimit());
        searchBO.setLabel(CrmTypeEnum.CUSTOMER.getType());
        searchBO.setSearchList(crmBackLogBO.getData());
        BasePage<Map<String, Object>> page = crmCustomerService.queryPageList(searchBO);
        List<Map<String, Object>> list = page.getList();
        list.forEach(record -> {
            record.put("poolDay", customerMap.get((Integer) record.get("customerId")));
            record.put("poolIds", CollectionUtil.join(remindPoolMap.get((Integer) record.get("customerId")), ","));
        });
        page.setList(list);
        return page;

    }
}
