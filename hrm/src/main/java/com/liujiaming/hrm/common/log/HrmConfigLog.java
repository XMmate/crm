package com.liujiaming.hrm.common.log;

import com.liujiaming.core.common.log.BehaviorEnum;
import com.liujiaming.core.common.log.Content;
import com.liujiaming.core.servlet.ApplicationContextHolder;
import com.liujiaming.hrm.constant.LabelGroupEnum;
import com.liujiaming.hrm.entity.BO.AddEmployeeFieldBO;
import com.liujiaming.hrm.entity.PO.HrmInsuranceScheme;
import com.liujiaming.hrm.service.IHrmInsuranceSchemeService;

public class HrmConfigLog {

    private IHrmInsuranceSchemeService insuranceSchemeService = ApplicationContextHolder.getBean(IHrmInsuranceSchemeService.class);

    public Content saveField(AddEmployeeFieldBO addEmployeeFieldBO) {
        String name = LabelGroupEnum.parse(addEmployeeFieldBO.getLabelGroup()).getName();
        return new Content(name,"修改了自定义字段:"+name, BehaviorEnum.UPDATE);
    }

    public Content deleteInsuranceScheme(Integer schemeId) {
        HrmInsuranceScheme insuranceScheme = insuranceSchemeService.getById(schemeId);
        return new Content(insuranceScheme.getSchemeName(),"删除了社保方案:"+insuranceScheme.getSchemeName(), BehaviorEnum.DELETE);
    }

}
