package com.liujiaming.hrm.mapper;

import com.liujiaming.core.servlet.BaseMapper;
import com.liujiaming.hrm.entity.PO.HrmSalaryOption;
import com.liujiaming.hrm.entity.PO.HrmSalaryOptionTemplate;

import java.util.List;

/**
 * <p>
 * 系统薪资项 Mapper 接口
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-26
 */
public interface HrmSalaryOptionMapper extends BaseMapper<HrmSalaryOption> {

    List<HrmSalaryOptionTemplate> querySalaryOptionTemplateList();

}
