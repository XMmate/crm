package com.liujiaming.hrm.service.impl;

import com.liujiaming.core.servlet.BaseServiceImpl;
import com.liujiaming.hrm.entity.PO.HrmAchievementAppraisalScoreLevel;
import com.liujiaming.hrm.entity.PO.HrmAchievementEmployeeAppraisal;
import com.liujiaming.hrm.mapper.HrmAchievementAppraisalScoreLevelMapper;
import com.liujiaming.hrm.service.IHrmAchievementAppraisalScoreLevelService;
import com.liujiaming.hrm.service.IHrmAchievementEmployeeAppraisalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 考评规则等级 服务实现类
 * </p>
 *
 * @author huangmingbo
 * @since 2024-05-12
 */
@Service
public class HrmAchievementAppraisalScoreLevelServiceImpl extends BaseServiceImpl<HrmAchievementAppraisalScoreLevelMapper, HrmAchievementAppraisalScoreLevel> implements IHrmAchievementAppraisalScoreLevelService {

    @Autowired
    private IHrmAchievementEmployeeAppraisalService employeeAppraisalService;

    @Override
    public List<HrmAchievementAppraisalScoreLevel> queryScoreLevelList(Integer employeeAppraisalId) {
        HrmAchievementEmployeeAppraisal employeeAppraisal = employeeAppraisalService.getById(employeeAppraisalId);
        return lambdaQuery().eq(HrmAchievementAppraisalScoreLevel::getAppraisalId,employeeAppraisal.getAppraisalId()).list();
    }

    @Override
    public List<HrmAchievementAppraisalScoreLevel> queryScoreLevelListByAppraisalId(Integer appraisalId) {
        return lambdaQuery().eq(HrmAchievementAppraisalScoreLevel::getAppraisalId,appraisalId).list();
    }
}
