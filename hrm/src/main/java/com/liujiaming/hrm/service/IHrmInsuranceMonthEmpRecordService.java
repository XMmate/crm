package com.liujiaming.hrm.service;

import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.hrm.entity.BO.AddInsuranceEmpBO;
import com.liujiaming.hrm.entity.BO.QueryEmpInsuranceMonthBO;
import com.liujiaming.hrm.entity.BO.QueryInsuranceRecordListBO;
import com.liujiaming.hrm.entity.BO.UpdateInsuranceProjectBO;
import com.liujiaming.hrm.entity.PO.HrmInsuranceMonthEmpRecord;
import com.liujiaming.hrm.entity.VO.EmpInsuranceByIdVO;
import com.liujiaming.hrm.entity.VO.QueryEmpInsuranceMonthVO;
import com.liujiaming.hrm.entity.VO.SimpleHrmEmployeeVO;

import java.util.List;

/**
 * <p>
 * 员工每月社保记录 服务类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-26
 */
public interface IHrmInsuranceMonthEmpRecordService extends BaseService<HrmInsuranceMonthEmpRecord> {

    /**
     * 查询每月员工社保列表
     * @param queryEmpInsuranceMonthBO
     * @return
     */
    BasePage<QueryEmpInsuranceMonthVO> queryEmpInsuranceMonth(QueryEmpInsuranceMonthBO queryEmpInsuranceMonthBO);

    /**
     * 查询员工社保详情
     * @param iempRecordId
     * @return
     */
    EmpInsuranceByIdVO queryById(String iempRecordId);

    /**
     * 停止参保
     * @param ids
     */
    void stop(List<Integer> ids);

    /**
     * 修改参保方案项目
     * @param updateInsuranceProjectBO
     */
    void updateInsuranceProject(UpdateInsuranceProjectBO updateInsuranceProjectBO);

    /**
     * 添加参保人员
     * @param addInsuranceEmpBO
     */
    void addInsuranceEmp(AddInsuranceEmpBO addInsuranceEmpBO);

    /**
     * 查询本月没有参保人员
     * @param iRecordId
     * @return
     */
    List<SimpleHrmEmployeeVO> queryNoInsuranceEmp(Integer iRecordId);

    /**
     * 批量修改参保方案项目
     * @param updateInsuranceProjectBO
     */
    void batchUpdateInsuranceProject(UpdateInsuranceProjectBO updateInsuranceProjectBO);

    /**
     * 我的社保
     * @param recordListBO
     * @return
     */
    BasePage<HrmInsuranceMonthEmpRecord> myInsurancePageList(QueryInsuranceRecordListBO recordListBO);
}
