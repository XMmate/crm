package com.liujiaming.hrm.service;

import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.entity.PageEntity;
import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.hrm.entity.BO.QueryAttendancePageBO;
import com.liujiaming.hrm.entity.BO.QueryNotesStatusBO;
import com.liujiaming.hrm.entity.PO.HrmAttendanceClock;
import com.liujiaming.hrm.entity.VO.QueryAttendancePageVO;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 打卡记录表 服务类
 * </p>
 *
 * @author liujiaming
 * @since 2024-12-07
 */
public interface IHrmAttendanceClockService extends BaseService<HrmAttendanceClock> {

    /**
     * 添加活修改打卡
     * @param attendanceClock
     */
    void addOrUpdate(HrmAttendanceClock attendanceClock);

    /**
     * 查询打卡列表
     * @param attendancePageBO
     * @return
     */
    BasePage<QueryAttendancePageVO> queryPageList(QueryAttendancePageBO attendancePageBO);

    /**
     * 根据时间和员工id查询打卡记录
     * @param time
     * @param employeeIds
     * @return
     */
    List<HrmAttendanceClock> queryClockListByTime(Date time, Collection<Integer> employeeIds);

    /**
     * 查询打卡时间列表
     * @param queryNotesStatusBO
     * @param employeeIds
     * @return
     */
    Set<String> queryClockStatusList(QueryNotesStatusBO queryNotesStatusBO, Collection<Integer> employeeIds);

    /**
     * 查询自己打卡列表(手机端使用)
     * @param pageEntity
     * @return
     */
    BasePage<QueryAttendancePageVO> queryMyPageList(PageEntity pageEntity);
}
