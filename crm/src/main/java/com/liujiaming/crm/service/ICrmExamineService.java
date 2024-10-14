package com.liujiaming.crm.service;

import com.alibaba.fastjson.JSONObject;
import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.entity.PageEntity;
import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.crm.constant.CrmEnum;
import com.liujiaming.crm.entity.BO.CrmMyExamineBO;
import com.liujiaming.crm.entity.BO.CrmQueryExamineStepBO;
import com.liujiaming.crm.entity.BO.CrmSaveExamineBO;
import com.liujiaming.crm.entity.PO.CrmExamine;
import com.liujiaming.crm.entity.VO.CrmQueryAllExamineVO;
import com.liujiaming.crm.entity.VO.CrmQueryExamineStepVO;

/**
 * <p>
 * 审批流程表 服务类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-28
 */
public interface ICrmExamineService extends BaseService<CrmExamine> {
    /**
     * 查询当前开启状态的审批数量
     * @param crmEnum enum
     * @return data
     */
    public Integer queryCount(CrmEnum crmEnum);

    void saveExamine(CrmSaveExamineBO crmSaveExamineBO);

    BasePage<CrmQueryAllExamineVO> queryAllExamine(PageEntity pageEntity);

    CrmQueryAllExamineVO queryExamineById(String examineId);

    void updateStatus(CrmExamine crmExamine);

    CrmQueryExamineStepVO queryExamineStep(CrmQueryExamineStepBO queryExamineStepBO);

    Boolean queryExamineStepByType(Integer categoryType);

    BasePage<JSONObject> myExamine(CrmMyExamineBO crmMyExamineBO);

}
