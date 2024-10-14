package com.liujiaming.hrm.service.impl;

import com.liujiaming.core.servlet.BaseServiceImpl;
import com.liujiaming.hrm.entity.PO.HrmAchievementEmployeeEvaluato;
import com.liujiaming.hrm.entity.VO.EvaluatoResultVO;
import com.liujiaming.hrm.mapper.HrmAchievementEmployeeEvaluatoMapper;
import com.liujiaming.hrm.service.IHrmAchievementEmployeeEvaluatoService;
import com.liujiaming.hrm.service.IHrmEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 员工绩效结果评定表 服务实现类
 * </p>
 *
 * @author huangmingbo
 * @since 2024-05-12
 */
@Service
public class HrmAchievementEmployeeEvaluatoServiceImpl extends BaseServiceImpl<HrmAchievementEmployeeEvaluatoMapper, HrmAchievementEmployeeEvaluato> implements IHrmAchievementEmployeeEvaluatoService {


    @Autowired
    private IHrmEmployeeService iHrmEmployeeService;

    @Autowired
    private HrmAchievementEmployeeEvaluatoMapper evaluatoMapper;

    @Override
    public List<EvaluatoResultVO> queryEvaluatoList(Integer employeeAppraisalId) {
        return evaluatoMapper.queryEvaluatoList(employeeAppraisalId);
    }
}
