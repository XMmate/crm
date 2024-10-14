package com.liujiaming.admin.mapper;

import com.liujiaming.admin.entity.BO.DeptVO;
import com.liujiaming.admin.entity.PO.AdminDept;
import com.liujiaming.core.servlet.BaseMapper;

import java.util.List;

/**
 * <p>
 * 部门表 Mapper 接口
 * </p>
 *
 * @author liujiaming
 * @since 2024-04-27
 */
public interface AdminDeptMapper extends BaseMapper<AdminDept> {

    List<DeptVO> queryDeptUserList();

    List<AdminDept> queryDeptList();
}
