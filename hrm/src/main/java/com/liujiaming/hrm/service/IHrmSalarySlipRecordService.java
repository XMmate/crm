package com.liujiaming.hrm.service;

import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.hrm.entity.BO.*;
import com.liujiaming.hrm.entity.BO.*;
import com.liujiaming.hrm.entity.PO.HrmSalarySlipOption;
import com.liujiaming.hrm.entity.PO.HrmSalarySlipRecord;
import com.liujiaming.hrm.entity.VO.QuerySendDetailListVO;
import com.liujiaming.hrm.entity.VO.QuerySendRecordListVO;
import com.liujiaming.hrm.entity.VO.SlipEmployeeVO;

import java.util.List;

/**
 * <p>
 * 发工资条记录 服务类
 * </p>
 *
 * @author hmb
 * @since 2024-11-03
 */
public interface IHrmSalarySlipRecordService extends BaseService<HrmSalarySlipRecord> {

    /**
     * 查询工资条选择发送员工列表
     * @param slipEmployeePageListBO
     * @return
     */
    BasePage<SlipEmployeeVO> querySlipEmployeePageList(QuerySlipEmployeePageListBO slipEmployeePageListBO);

    /**
     * 发工资条
     * @param sendSalarySlipBO
     */
    void sendSalarySlip(SendSalarySlipBO sendSalarySlipBO);

    /**
     * 查询发放工资条记录列表
     * @param querySendRecordListBO
     * @return
     */
    BasePage<QuerySendRecordListVO> querySendRecordList(QuerySendRecordListBO querySendRecordListBO);

    /**
     * 查询发放工资条记录详情列表
     * @param querySendRecordListBO
     * @return
     */
    BasePage<QuerySendDetailListVO> querySendDetailList(QuerySendDetailListBO querySendRecordListBO);

    /**
     * 添加修改工资条备注
     * @param setSlipRemarksBO
     */
    void setSlipRemarks(SetSlipRemarksBO setSlipRemarksBO);

    /**
     * 查询工资条明细
     * @param id
     * @return
     */
    List<HrmSalarySlipOption> querySlipDetail(Integer id);

    /**
     * 删除发放记录
     * @param id
     */
    void deleteSendRecord(String id);
}
