package com.liujiaming.hrm.service;

import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.hrm.entity.BO.AddSlipTemplateBO;
import com.liujiaming.hrm.entity.PO.HrmSalarySlipTemplate;

/**
 * <p>
 * 工资表模板 服务类
 * </p>
 *
 * @author hmb
 * @since 2024-11-03
 */
public interface IHrmSalarySlipTemplateService extends BaseService<HrmSalarySlipTemplate> {

    /**
     * 添加工资条模板
     * @param addSlipTemplateBO
     */
    void addSlipTemplate(AddSlipTemplateBO addSlipTemplateBO);

    /**
     * 删除工资条模板
     * @param templateId
     */
    void deleteSlipTemplate(Integer templateId);
}
