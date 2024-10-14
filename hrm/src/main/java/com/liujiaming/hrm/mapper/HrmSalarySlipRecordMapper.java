package com.liujiaming.hrm.mapper;

import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.servlet.BaseMapper;
import com.liujiaming.hrm.entity.BO.QuerySendDetailListBO;
import com.liujiaming.hrm.entity.BO.QuerySendRecordListBO;
import com.liujiaming.hrm.entity.BO.QuerySlipEmployeePageListBO;
import com.liujiaming.hrm.entity.BO.SendSalarySlipBO;
import com.liujiaming.hrm.entity.PO.HrmSalarySlipRecord;
import com.liujiaming.hrm.entity.VO.QuerySendDetailListVO;
import com.liujiaming.hrm.entity.VO.QuerySendRecordListVO;
import com.liujiaming.hrm.entity.VO.SlipEmployeeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 发工资条记录 Mapper 接口
 * </p>
 *
 * @author hmb
 * @since 2024-11-03
 */
public interface HrmSalarySlipRecordMapper extends BaseMapper<HrmSalarySlipRecord> {

    BasePage<SlipEmployeeVO> querySlipEmployeePageList(BasePage<SlipEmployeeVO> page, @Param("sRecordId") Integer sRecordId,@Param("data") QuerySlipEmployeePageListBO slipEmployeePageListBO);

    BasePage<QuerySendRecordListVO> querySendRecordList(BasePage<QuerySendRecordListVO> page,@Param("data") QuerySendRecordListBO querySendRecordListBO);

    BasePage<QuerySendDetailListVO> querySendDetailList(BasePage<QuerySendDetailListVO> page,@Param("data") QuerySendDetailListBO querySendRecordListBO);

    List<Integer> querySlipEmployeeIds(@Param("sRecordId") Integer sRecordId,@Param("data") SendSalarySlipBO sendSalarySlipBO);
}
