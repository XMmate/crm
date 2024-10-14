package com.liujiaming.oa.mapper;

import com.liujiaming.core.servlet.BaseMapper;
import com.liujiaming.oa.entity.PO.OaExamineCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 审批类型表 Mapper 接口
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-15
 */
public interface OaExamineCategoryMapper extends BaseMapper<OaExamineCategory> {

    List<OaExamineCategory> queryAllExamineCategoryList(@Param("isAdmin") boolean isAdmin,@Param("userId") Long userId,@Param("deptId") Integer deptId);
}
