package com.liujiaming.hrm.service;

import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.hrm.entity.PO.HrmAchievementEmployeeEvaluato;
import com.liujiaming.hrm.entity.VO.EvaluatoResultVO;

import java.util.List;

/**
 * <p>
 * 员工绩效结果评定表 服务类
 * </p>
 *
 * @author huangmingbo
 * @since 2024-05-12
 */
public interface IHrmAchievementEmployeeEvaluatoService extends BaseService<HrmAchievementEmployeeEvaluato> {

    /**
     * 查询考核评价列表
     * @param employeeAppraisalId
     * @return
     */
    List<EvaluatoResultVO> queryEvaluatoList(Integer employeeAppraisalId);
}
