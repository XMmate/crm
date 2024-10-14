package com.liujiaming.hrm.service;

import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.hrm.entity.PO.HrmSalaryOptionTemplate;
import com.liujiaming.hrm.entity.VO.SalaryOptionVO;

import java.util.List;

/**
 * <p>
 * 系统薪资项模板 服务类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-26
 */
public interface IHrmSalaryOptionTemplateService extends BaseService<HrmSalaryOptionTemplate> {

    /**
     * 查询薪资模板列表
     * @return
     */
    List<SalaryOptionVO> querySalaryOptionTemplateList();
}
