package com.liujiaming.hrm.mapper;

import com.liujiaming.core.servlet.BaseMapper;
import com.liujiaming.hrm.entity.PO.HrmAchievementEmployeeEvaluato;
import com.liujiaming.hrm.entity.VO.EvaluatoResultVO;

import java.util.List;

/**
 * <p>
 * 员工绩效结果评定表 Mapper 接口
 * </p>
 *
 * @author huangmingbo
 * @since 2024-05-12
 */
public interface HrmAchievementEmployeeEvaluatoMapper extends BaseMapper<HrmAchievementEmployeeEvaluato> {

    List<EvaluatoResultVO> queryEvaluatoList(Integer employeeAppraisalId);
}
