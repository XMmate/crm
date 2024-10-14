package com.liujiaming.crm.mapper;

import com.alibaba.fastjson.JSONObject;
import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.servlet.BaseMapper;
import com.liujiaming.crm.entity.BO.CrmMyExamineBO;
import com.liujiaming.crm.entity.PO.CrmExamine;
import com.liujiaming.crm.entity.VO.CrmQueryAllExamineVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 审批流程表 Mapper 接口
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-28
 */
public interface CrmExamineMapper extends BaseMapper<CrmExamine> {

    BasePage<CrmQueryAllExamineVO> queryExaminePage(BasePage<Object> parse);

    BasePage<JSONObject> myExamine(BasePage<Object> parse, @Param("data") CrmMyExamineBO crmMyExamineBO,@Param("isAdmin") boolean isAdmin,
                                   @Param("userId") Long userId);

    CrmExamine selectCrmExamineByUser(@Param("categoryType") Integer categoryType,@Param("isAdmin") boolean isAdmin, @Param("userId") Long userId, @Param("deptId") Integer deptId);
}
