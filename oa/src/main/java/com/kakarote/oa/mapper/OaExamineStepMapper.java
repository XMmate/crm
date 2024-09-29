package com.kakarote.oa.mapper;

import com.kakarote.core.servlet.BaseMapper;
import com.kakarote.oa.entity.PO.OaExamineStep;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 审批步骤表 Mapper 接口
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-15
 */
public interface OaExamineStepMapper extends BaseMapper<OaExamineStep> {

    OaExamineStep queryExamineStepByNextExamineIdOrderByStepId(@Param("categoryId") Integer categoryId,@Param("examineStepId") Integer examineStepId);
}
