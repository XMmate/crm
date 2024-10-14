package com.liujiaming.hrm.service.actionrecord.impl;

import com.liujiaming.core.common.SubModelType;
import com.liujiaming.core.common.log.BehaviorEnum;
import com.liujiaming.core.common.log.Content;
import com.liujiaming.core.common.log.SysLog;
import com.liujiaming.core.common.log.SysLogHandler;
import com.liujiaming.hrm.entity.PO.HrmSalaryMonthRecord;
import com.liujiaming.hrm.service.actionrecord.AbstractHrmActionRecordService;
import org.springframework.stereotype.Service;

@Service("salaryActionRecordService")
@SysLog(subModel = SubModelType.HRM_SALARY)
public class SalaryActionRecordServiceImpl extends AbstractHrmActionRecordService {

    @SysLogHandler(isReturn = true)
    public Content addNextMonthSalaryLog(HrmSalaryMonthRecord salaryMonthRecord){
        String detail = "添加"+salaryMonthRecord.getYear()+"-"+salaryMonthRecord.getTitle();
        return new Content(salaryMonthRecord.getTitle(),detail, BehaviorEnum.SAVE);
    }

    @SysLogHandler(isReturn = true)
    public Content computeSalaryDataLog(HrmSalaryMonthRecord salaryMonthRecord) {
        String detail = "核算"+salaryMonthRecord.getYear()+"-"+salaryMonthRecord.getTitle();
        return new Content(salaryMonthRecord.getTitle(),detail, BehaviorEnum.SAVE);
    }

    @SysLogHandler(isReturn = true)
    public Content deleteSalaryLog(HrmSalaryMonthRecord salaryMonthRecord) {
        String detail = "删除"+salaryMonthRecord.getYear()+"-"+salaryMonthRecord.getTitle();
        return new Content(salaryMonthRecord.getTitle(),detail, BehaviorEnum.SAVE);
    }
}
