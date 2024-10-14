package com.liujiaming.hrm.service;

import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.hrm.entity.BO.SetInterviewResultBO;
import com.liujiaming.hrm.entity.BO.SetRecruitInterviewBO;
import com.liujiaming.hrm.entity.PO.HrmRecruitInterview;

/**
 * <p>
 * 面试表 服务类
 * </p>
 *
 * @author huangmingbo
 * @since 2024-05-12
 */
public interface IHrmRecruitInterviewService extends BaseService<HrmRecruitInterview> {

    /**
     * 安排面试
     * @param setRecruitInterviewBO
     */
    void setInterview(SetRecruitInterviewBO setRecruitInterviewBO);

    /**
     * 填写面试结果
     * @param setInterviewResultBO
     */
    void setInterviewResult(SetInterviewResultBO setInterviewResultBO);

    /**
     * 批量安排面试
     * @param setRecruitInterviewBO
     */
    void addBatchInterview(SetRecruitInterviewBO setRecruitInterviewBO);
}
