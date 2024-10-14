package com.liujiaming.hrm.mapper;

import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.servlet.BaseMapper;
import com.liujiaming.hrm.entity.BO.QueryAppraisalEmployeeListBO;
import com.liujiaming.hrm.entity.BO.QueryAppraisalPageListBO;
import com.liujiaming.hrm.entity.BO.QueryEmployeeListByAppraisalIdBO;
import com.liujiaming.hrm.entity.PO.HrmAchievementAppraisal;
import com.liujiaming.hrm.entity.VO.AppraisalEmployeeListVO;
import com.liujiaming.hrm.entity.VO.AppraisalPageListVO;
import com.liujiaming.hrm.entity.VO.EmployeeAppraisalVO;
import com.liujiaming.hrm.entity.VO.EmployeeListByAppraisalIdVO;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.Map;

/**
 * <p>
 * 绩效考核 Mapper 接口
 * </p>
 *
 * @author huangmingbo
 * @since 2024-05-12
 */
public interface HrmAchievementAppraisalMapper extends BaseMapper<HrmAchievementAppraisal> {

    BasePage<AppraisalPageListVO> queryAppraisalPageList(BasePage<AppraisalPageListVO> parse,@Param("data") QueryAppraisalPageListBO queryAppraisalPageListBO);

    BasePage<EmployeeListByAppraisalIdVO> queryEmployeeListByAppraisalId(BasePage<EmployeeListByAppraisalIdVO> page,
                                                                         @Param("data") QueryEmployeeListByAppraisalIdBO employeeListByAppraisalIdBO);

    BasePage<AppraisalEmployeeListVO> queryEmployeePageList(BasePage<AppraisalEmployeeListVO> parse, @Param("data") QueryAppraisalEmployeeListBO employeeListBO,
                                                            @Param("employeeIds") Collection<Integer> dataAuthEmployeeIds);

    Map<String, Object> queryEmployeeLastAppraisal(Integer employeeId);

    BasePage<EmployeeAppraisalVO> queryEmployeeAppraisal(BasePage<EmployeeAppraisalVO> parse,@Param("employeeId") Integer employeeId);
}
