package com.liujiaming.hrm.mapper;

import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.servlet.BaseMapper;
import com.liujiaming.hrm.entity.BO.QueryAttendancePageBO;
import com.liujiaming.hrm.entity.BO.QueryNotesStatusBO;
import com.liujiaming.hrm.entity.PO.HrmAttendanceClock;
import com.liujiaming.hrm.entity.VO.QueryAttendancePageVO;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 打卡记录表 Mapper 接口
 * </p>
 *
 * @author liujiaming
 * @since 2024-12-07
 */
public interface HrmAttendanceClockMapper extends BaseMapper<HrmAttendanceClock> {

    BasePage<QueryAttendancePageVO> queryPageList(BasePage<QueryAttendancePageVO> parse,
                                                  @Param("data") QueryAttendancePageBO attendancePageBO,@Param("employeeIds") Collection<Integer> employeeIds);

    List<HrmAttendanceClock> queryClockListByTime(@Param("time") Date time,@Param("employeeIds") Collection<Integer> employeeIds);

    Set<String> queryClockStatusList(@Param("data") QueryNotesStatusBO queryNotesStatusBO,@Param("employeeIds") Collection<Integer> employeeIds);

    BasePage<QueryAttendancePageVO> queryMyPageList(BasePage<Object> parse,@Param("employeeId") Integer employeeId);
}
