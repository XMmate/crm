package com.liujiaming.crm.mapper;

import com.liujiaming.core.servlet.BaseMapper;
import com.liujiaming.crm.entity.PO.CrmBusinessType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商机状态组类别 Mapper 接口
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-27
 */
public interface CrmBusinessTypeMapper extends BaseMapper<CrmBusinessType> {

    List<CrmBusinessType> queryBusinessStatusOptions(@Param("deptId") Integer deptId, @Param("isAdmin") boolean isAdmin);
}
