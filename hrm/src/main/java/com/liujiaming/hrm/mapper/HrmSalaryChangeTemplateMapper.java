package com.liujiaming.hrm.mapper;

import com.liujiaming.core.servlet.BaseMapper;
import com.liujiaming.hrm.entity.PO.HrmSalaryChangeTemplate;
import com.liujiaming.hrm.entity.VO.ChangeSalaryOptionVO;

import java.util.List;

/**
 * <p>
 * 调薪模板 Mapper 接口
 * </p>
 *
 * @author hmb
 * @since 2024-11-05
 */
public interface HrmSalaryChangeTemplateMapper extends BaseMapper<HrmSalaryChangeTemplate> {

    List<ChangeSalaryOptionVO> queryChangeSalaryOption();

}
