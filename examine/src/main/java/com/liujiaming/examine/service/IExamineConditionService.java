package com.liujiaming.examine.service;

import com.liujiaming.core.entity.UserInfo;
import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.examine.entity.PO.ExamineCondition;
import com.liujiaming.examine.entity.PO.ExamineConditionData;
import com.liujiaming.examine.entity.PO.ExamineFlow;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 审批条件表 服务类
 * </p>
 *
 * @author liujiaming
 * @since 2024-11-13
 */
public interface IExamineConditionService extends BaseService<ExamineCondition> {

    /**
     * 查询条件审批下的审批流程
     * @param examineFlow 当前流程
     * @param conditionMap 条件entity
     * @return data
     */
    public ExamineFlow queryNextExamineFlowByCondition(ExamineFlow examineFlow, Map<String, Object> conditionMap);

    /**
     * 判断条件是否通过
     * @date 2024/12/16 13:18
     * @param conditionDataList
     * @param conditionMap
     * @param userInfo
     * @return boolean
     **/
    public boolean handleExamineConditionData(List<ExamineConditionData> conditionDataList, Map<String, Object> conditionMap, UserInfo userInfo);

}
