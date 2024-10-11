package com.kakarote.oa.feign.fallback;

import com.kakarote.core.common.Result;
import com.kakarote.oa.constart.entity.BO.*;
import com.kakarote.oa.constart.entity.PO.WorkTask;
import com.kakarote.oa.constart.entity.VO.OaTaskListVO;
import com.kakarote.oa.feign.service.IOaTaskService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class IOaTaskServiceFallback implements FallbackFactory<IOaTaskService> {
    @Override
    public IOaTaskService create(Throwable cause) {
        return new IOaTaskService() {


            @Override
            public Result setWorkTaskStatus(WorkTaskStatusBO workTaskStatusBO) {
                log.error(cause.getMessage());
                return null;
            }

            @Override
            public Result setWorkTaskTitle(WorkTaskNameBO workTaskNameBO) {
                log.error(cause.getMessage());
                return null;
            }

            @Override
            public Result setWorkTaskDescription(WorkTaskDescriptionBO workTaskDescriptionBO) {
                log.error(cause.getMessage());
                return null;
            }

            @Override
            public Result setWorkTaskMainUser(WorkTaskUserBO workTaskUserBO) {
                log.error(cause.getMessage());
                return null;
            }

            @Override
            public Result setWorkTaskOwnerUser(WorkTaskOwnerUserBO workTaskOwnerUserBO) {
                log.error(cause.getMessage());
                return null;
            }

            @Override
            public Result setWorkTaskTime(WorkTask workTask) {
                log.error(cause.getMessage());
                return null;
            }

            @Override
            public Result setWorkTaskLabel(WorkTaskLabelsBO workTaskLabelsBO) {
                log.error(cause.getMessage());
                return null;
            }

            @Override
            public Result setWorkTaskPriority(WorkTaskPriorityBO workTaskPriorityBO) {
                log.error(cause.getMessage());
                return null;
            }

            @Override
            public Result addWorkChildTask(WorkTask workTask) {
                log.error(cause.getMessage());
                return null;
            }

            @Override
            public Result updateWorkChildTask(WorkTask workTask) {
                log.error(cause.getMessage());
                return null;
            }

            @Override
            public Result setWorkChildTaskStatus(WorkTaskStatusBO workTaskStatusBO) {
                log.error(cause.getMessage());
                return null;
            }

            @Override
            public Result deleteWorkTaskOwnerUser(WorkTaskUserBO workTaskUserBO) {
                log.error(cause.getMessage());
                return null;
            }

            @Override
            public Result deleteWorkTaskLabel(WorkTaskLabelBO workTaskLabelBO) {
                log.error(cause.getMessage());
                return null;
            }

            @Override
            public Result deleteWorkChildTask(Integer taskId) {
                log.error(cause.getMessage());
                return null;
            }

            @Override
            public Result<OaTaskListVO> queryTaskList(OaTaskListBO oaTaskListBO) {
                log.error(cause.getMessage());
                return null;
            }
        };
    }
}
