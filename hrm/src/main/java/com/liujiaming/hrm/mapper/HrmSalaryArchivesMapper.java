package com.liujiaming.hrm.mapper;

import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.servlet.BaseMapper;
import com.liujiaming.hrm.entity.BO.QuerySalaryArchivesListBO;
import com.liujiaming.hrm.entity.DTO.ExcelTemplateOption;
import com.liujiaming.hrm.entity.PO.HrmSalaryArchives;
import com.liujiaming.hrm.entity.VO.ChangeSalaryOptionVO;
import com.liujiaming.hrm.entity.VO.QueryChangeRecordListVO;
import com.liujiaming.hrm.entity.VO.QuerySalaryArchivesListVO;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 薪资档案表 Mapper 接口
 * </p>
 *
 * @author hmb
 * @since 2024-11-05
 */
public interface HrmSalaryArchivesMapper extends BaseMapper<HrmSalaryArchives> {

    BasePage<QuerySalaryArchivesListVO> querySalaryArchivesList(BasePage<QuerySalaryArchivesListVO> parse, @Param("data") QuerySalaryArchivesListBO querySalaryArchivesListBO,
                                                                @Param("employeeIds") Collection<Integer> employeeIds);

    List<QueryChangeRecordListVO> queryChangeRecordList(@Param("employeeId") Integer employeeId);

    List<ChangeSalaryOptionVO> queryBatchChangeOption();

    List<ExcelTemplateOption> queryFixSalaryExcelExportOption();

    List<ExcelTemplateOption> queryChangeSalaryExcelExportOption();

}
