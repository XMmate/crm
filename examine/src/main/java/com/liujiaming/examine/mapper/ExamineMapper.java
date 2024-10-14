package com.liujiaming.examine.mapper;

import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.servlet.BaseMapper;
import com.liujiaming.examine.entity.PO.Examine;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 审批表 Mapper 接口
 * </p>
 *
 * @author liujiaming
 * @since 2024-11-13
 */
public interface ExamineMapper extends BaseMapper<Examine> {

    BasePage<Examine> selectPartExaminePage(BasePage<Object> parse, @Param("label") Integer label, @Param("isAdmin") boolean isAdmin,
                                            @Param("isPart") boolean isPart,@Param("userId") Long userId,@Param("deptId") Integer deptId);

}
