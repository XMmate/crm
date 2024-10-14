package com.liujiaming.hrm.mapper;

import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.servlet.BaseMapper;
import com.liujiaming.hrm.entity.BO.EvaluatoListBO;
import com.liujiaming.hrm.entity.PO.HrmAchievementEmployeeAppraisal;
import com.liujiaming.hrm.entity.VO.EvaluatoListVO;
import com.liujiaming.hrm.entity.VO.QueryMyAppraisalVO;
import com.liujiaming.hrm.entity.VO.ResultConfirmListVO;
import com.liujiaming.hrm.entity.VO.TargetConfirmListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 员工绩效考核 Mapper 接口
 * </p>
 *
 * @author huangmingbo
 * @since 2024-05-12
 */
public interface HrmAchievementEmployeeAppraisalMapper extends BaseMapper<HrmAchievementEmployeeAppraisal> {

    BasePage<QueryMyAppraisalVO> queryMyAppraisal(BasePage<QueryMyAppraisalVO> parse, @Param("employeeId") Integer employeeId,@Param("status") Integer status);

    BasePage<EvaluatoListVO> evaluatoList(BasePage<Object> parse,
                                          @Param("employeeId") Integer employeeId,
                                          @Param("data") EvaluatoListBO evaluatoListBO);

    BasePage<TargetConfirmListVO> queryTodoAppraisalByStatus(BasePage<TargetConfirmListVO> parse,
                                                             @Param("employeeId") Integer employeeId,
                                                             @Param("status") Integer status,
                                                             @Param("search") String search,
                                                             @Param("appraisalId") Integer appraisalId);
    BasePage<ResultConfirmListVO> queryResultConfirmList(BasePage<Object> parse, @Param("employeeId") Integer employeeId,@Param("search") String search);

    List<Map<String, Object>> queryScoreLevels(Integer appraisalId);

    Map<String, Object> queryAppraisalIdInfo(String appraisalId);

    List<Map<String, Object>> queryEmployeeByLevelId(@Param("levelId") Integer levelId,
                                                     @Param("appraisalId") Integer appraisalId);

    Integer queryWaitingNum(String appraisalId);

    Integer queryTotalNum(String appraisalId);

    List<TargetConfirmListVO> queryTargetConfirmScreen(@Param("employeeId") Integer employeeId,
                                                       @Param("status") Integer status);

    List<EvaluatoListVO> queryEvaluatoScreen(@Param("employeeId")Integer employeeId,@Param("status") Integer status);
}
