package com.liujiaming.hrm.service.impl;


import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.servlet.BaseServiceImpl;
import com.liujiaming.hrm.common.EmployeeHolder;
import com.liujiaming.hrm.entity.BO.QuerySalarySlipListBO;
import com.liujiaming.hrm.entity.PO.HrmSalarySlip;
import com.liujiaming.hrm.entity.VO.QuerySalarySlipListVO;
import com.liujiaming.hrm.mapper.HrmSalarySlipMapper;
import com.liujiaming.hrm.service.IHrmSalarySlipRecordService;
import com.liujiaming.hrm.service.IHrmSalarySlipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 工资条 服务实现类
 * </p>
 *
 * @author hmb
 * @since 2024-11-03
 */
@Service
public class HrmSalarySlipServiceImpl extends BaseServiceImpl<HrmSalarySlipMapper, HrmSalarySlip> implements IHrmSalarySlipService {


    @Autowired
    private HrmSalarySlipMapper salarySlipMapper;

    @Autowired
    private IHrmSalarySlipRecordService slipRecordService;

    @Override
    public BasePage<QuerySalarySlipListVO> querySalarySlipList(QuerySalarySlipListBO querySalarySlipListBO) {
        BasePage<QuerySalarySlipListVO> page = salarySlipMapper.querySalarySlipList(querySalarySlipListBO.parse(), querySalarySlipListBO, EmployeeHolder.getEmployeeId());
        page.getList().forEach(slip -> {
            if (slip.getReadStatus() == 0){
                lambdaUpdate().set(HrmSalarySlip::getReadStatus,1).eq(HrmSalarySlip::getId,slip.getId()).update();
            }
            slip.setSalarySlipOptionList(slipRecordService.querySlipDetail(slip.getId()));
        });
        return page;
    }
}
