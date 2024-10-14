package com.liujiaming.hrm.mapper;

import com.liujiaming.core.servlet.BaseMapper;
import com.liujiaming.hrm.entity.BO.QueryEmployFieldManageBO;
import com.liujiaming.hrm.entity.PO.HrmEmployeeFieldManage;
import com.liujiaming.hrm.entity.VO.EmployeeFieldManageVO;

import java.util.List;

/**
 * <p>
 * 自定义字段管理表 Mapper 接口
 * </p>
 *
 * @author guomenghao
 * @since 2021-04-14
 */
public interface HrmEmployeeFieldManageMapper extends BaseMapper<HrmEmployeeFieldManage> {
    /**
     * 查询管理可设置员工字段列表
     * @param queryEmployFieldManageBO
     * @return
     */
    List<EmployeeFieldManageVO> queryEmployeeManageField(QueryEmployFieldManageBO queryEmployFieldManageBO);
}
