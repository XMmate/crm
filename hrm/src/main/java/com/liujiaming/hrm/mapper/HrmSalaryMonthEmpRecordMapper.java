package com.liujiaming.hrm.mapper;

import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.servlet.BaseMapper;
import com.liujiaming.hrm.entity.BO.QueryHistorySalaryDetailBO;
import com.liujiaming.hrm.entity.BO.QuerySalaryPageListBO;
import com.liujiaming.hrm.entity.PO.HrmSalaryMonthEmpRecord;
import com.liujiaming.hrm.entity.VO.QuerySalaryListVO;
import com.liujiaming.hrm.entity.VO.QuerySalaryPageListVO;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 员工每月薪资记录 Mapper 接口
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-26
 */
public interface HrmSalaryMonthEmpRecordMapper extends BaseMapper<HrmSalaryMonthEmpRecord> {

    /**
     * 查询计薪人员id
     * @return
     */
    List<Map<String,Object>> queryPaySalaryEmployeeList(@Param("endTime") Date endTime,@Param("employeeIds") Collection<Integer> dataAuthEmployeeIds);

    BasePage<QuerySalaryPageListVO> querySalaryPageList(BasePage<QuerySalaryPageListVO> parse, @Param("data") QuerySalaryPageListBO querySalaryPageListBO,
                                                        @Param("employeeIds") Collection<Integer> dataAuthEmployeeIds);

    BasePage<QuerySalaryPageListVO> querySalaryPageListByRecordId(BasePage<QuerySalaryPageListVO> parse, @Param("data") QueryHistorySalaryDetailBO queryHistorySalaryDetailBO,
                                                                  @Param("employeeIds") Collection<Integer> employeeIds);

    List<Integer> querysEmpRecordIds(@Param("data") QuerySalaryPageListBO querySalaryPageListBO,
                                     @Param("employeeIds") Collection<Integer> dataAuthEmployeeIds);

    BasePage<QuerySalaryListVO> querySalaryRecord(BasePage<Object> parse,@Param("employeeId") Integer employeeId);

    List<Integer> queryEmployeeIds(@Param("sRecordId") Integer sRecordId,@Param("employeeIds") Collection<Integer> dataAuthEmployeeIds);
}
