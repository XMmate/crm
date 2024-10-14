package com.liujiaming.hrm.service.impl;

import com.liujiaming.core.servlet.BaseServiceImpl;
import com.liujiaming.core.utils.RecursionUtil;
import com.liujiaming.hrm.entity.PO.HrmSalaryOptionTemplate;
import com.liujiaming.hrm.entity.VO.SalaryOptionVO;
import com.liujiaming.hrm.mapper.HrmSalaryOptionMapper;
import com.liujiaming.hrm.mapper.HrmSalaryOptionTemplateMapper;
import com.liujiaming.hrm.service.IHrmSalaryOptionTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 系统薪资项模板 服务实现类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-26
 */
@Service
public class HrmSalaryOptionTemplateServiceImpl extends BaseServiceImpl<HrmSalaryOptionTemplateMapper, HrmSalaryOptionTemplate> implements IHrmSalaryOptionTemplateService {

    @Autowired
    private HrmSalaryOptionMapper salaryOptionMapper;

    @Override
    public List<SalaryOptionVO> querySalaryOptionTemplateList() {
        List<HrmSalaryOptionTemplate> list = salaryOptionMapper.querySalaryOptionTemplateList();
        return RecursionUtil.getChildListTree(list, "parentCode", 0, "code", "children", SalaryOptionVO.class);
    }
}
