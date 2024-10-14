package com.liujiaming.hrm.service.actionrecord.impl;

import com.liujiaming.core.common.SubModelType;
import com.liujiaming.core.common.log.BehaviorEnum;
import com.liujiaming.core.common.log.Content;
import com.liujiaming.core.common.log.SysLog;
import com.liujiaming.core.common.log.SysLogHandler;
import com.liujiaming.hrm.entity.PO.HrmInsuranceMonthRecord;
import com.liujiaming.hrm.service.actionrecord.AbstractHrmActionRecordService;
import org.springframework.stereotype.Service;

@Service("insuranceActionRecordService")
@SysLog(subModel = SubModelType.HRM_SOCIAL_SECURITY)
public class insuranceActionRecordServiceImpl extends AbstractHrmActionRecordService {

    @SysLogHandler(isReturn = true)
    public Content computeInsuranceDataLog(HrmInsuranceMonthRecord insuranceMonthRecord){
        String detail = "生成"+insuranceMonthRecord.getYear()+"="+insuranceMonthRecord.getTitle();
        return new Content(insuranceMonthRecord.getTitle(),detail, BehaviorEnum.SAVE);
    }

    @SysLogHandler(isReturn = true)
    public Content deleteInsurance(HrmInsuranceMonthRecord insuranceMonthRecord) {
        String detail = "删除了"+insuranceMonthRecord.getYear()+"="+insuranceMonthRecord.getTitle();
        return new Content(insuranceMonthRecord.getTitle(),detail, BehaviorEnum.DELETE);
    }
}
