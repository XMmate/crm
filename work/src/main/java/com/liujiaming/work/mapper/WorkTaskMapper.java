package com.liujiaming.work.mapper;

import cn.hutool.core.lang.Dict;
import com.alibaba.fastjson.JSONObject;
import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.entity.PageEntity;
import com.liujiaming.core.servlet.BaseMapper;
import com.liujiaming.work.entity.BO.OaTaskListBO;
import com.liujiaming.work.entity.BO.TaskLabelBO;
import com.liujiaming.work.entity.BO.WorkTaskLabelBO;
import com.liujiaming.work.entity.BO.WorkTaskNameBO;
import com.liujiaming.work.entity.PO.WorkTask;
import com.liujiaming.work.entity.VO.OaTaskListVO;
import com.liujiaming.work.entity.VO.TaskDetailVO;
import com.liujiaming.work.entity.VO.TaskInfoVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 任务表 Mapper 接口
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-18
 */
public interface WorkTaskMapper extends BaseMapper<WorkTask> {

    List<TaskInfoVO> queryMyTaskList(@Param("isTop")Integer isTop,@Param("userId")Long userId,@Param("data") WorkTaskNameBO workTaskNameBO);

    BasePage<TaskInfoVO> queryMyTaskList(PageEntity page, @Param("isTop")Integer isTop, @Param("userId")Long userId );

    List<TaskLabelBO> queryTaskLabel(@Param("list")List<Integer> taskIdList);

    TaskDetailVO queryTaskInfo(@Param("taskId")Integer taskId);

    List<WorkTaskLabelBO> queryWorkTaskLabelList(@Param("list")List<Integer> labelIdList);

    List<TaskInfoVO> queryTrashList(@Param("userId")Long userId);

    OaTaskListVO queryTaskCount(@Param("data") OaTaskListBO oaTaskListBO, @Param("userIdList")List<Long> userIdList);

    BasePage<TaskInfoVO> getTaskList(BasePage<TaskInfoVO> page,@Param("data")OaTaskListBO oaTaskListBO, @Param("userIdList")List<Long> userIdList);

    List<JSONObject> getTaskListExport(@Param("data")OaTaskListBO oaTaskListBO,@Param("userIdList") List<Long> userIds);

    List<JSONObject> myTaskExport(@Param("data") Dict kv);
}
