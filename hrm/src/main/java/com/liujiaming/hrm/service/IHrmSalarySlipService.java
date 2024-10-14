package com.liujiaming.hrm.service;

import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.hrm.entity.BO.QuerySalarySlipListBO;
import com.liujiaming.hrm.entity.PO.HrmSalarySlip;
import com.liujiaming.hrm.entity.VO.QuerySalarySlipListVO;

/**
 * <p>
 * 工资条 服务类
 * </p>
 *
 * @author hmb
 * @since 2024-11-03
 */
public interface IHrmSalarySlipService extends BaseService<HrmSalarySlip> {

    /**
     * 查询我的工资条列表
     * @param querySalarySlipListBO
     * @return
     */
    BasePage<QuerySalarySlipListVO> querySalarySlipList(QuerySalarySlipListBO querySalarySlipListBO);
}
