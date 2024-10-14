package com.liujiaming.examine.mapper;

import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.servlet.BaseMapper;
import com.liujiaming.examine.entity.BO.ExaminePageBO;
import com.liujiaming.examine.entity.PO.ExamineRecord;
import com.liujiaming.examine.entity.PO.ExamineRecordLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 审核日志表 Mapper 接口
 * </p>
 *
 * @author liujiaming
 * @since 2024-11-19
 */
public interface ExamineRecordLogMapper extends BaseMapper<ExamineRecordLog> {

    BasePage<ExamineRecord> selectRecordLogListByUser(BasePage<Object> parse, @Param("data") ExaminePageBO examinePageBO,
                                                        @Param("userId") Long userId, @Param("roleIds") List<Integer> roleIds);


    List<Integer> selectRecordTypeIdListByUser(@Param("data") ExaminePageBO examinePageBO,
                                                      @Param("userId") Long userId, @Param("roleIds") List<Integer> roleIds);
}
