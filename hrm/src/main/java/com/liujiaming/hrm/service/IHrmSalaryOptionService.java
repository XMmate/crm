package com.liujiaming.hrm.service;

import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.hrm.entity.BO.SetSalaryOptionBO;
import com.liujiaming.hrm.entity.PO.HrmSalaryOption;
import com.liujiaming.hrm.entity.VO.SalaryOptionDetailVO;
import com.liujiaming.hrm.entity.VO.SalaryOptionVO;

import java.util.List;

/**
 * <p>
 * 系统薪资项 服务类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-26
 */
public interface IHrmSalaryOptionService extends BaseService<HrmSalaryOption> {

    /**
     * 查询薪资项详情
     * @return
     */
    SalaryOptionDetailVO querySalaryOptionDetail();

    /**
     * 查询薪资项列表
     * @return
     */
    List<SalaryOptionVO> querySalaryOptionList();


    /**
     * 修改薪资项
     * @param setSalaryOptionBO
     */
    void setSalaryOption(SetSalaryOptionBO setSalaryOptionBO);

}
