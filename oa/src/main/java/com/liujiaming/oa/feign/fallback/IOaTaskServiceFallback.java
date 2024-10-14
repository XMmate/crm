package com.liujiaming.oa.feign.fallback;
import com.liujiaming.core.common.Result;
import com.liujiaming.oa.constart.entity.BO.*;
import com.liujiaming.oa.constart.entity.BO.OaTaskListBO;
import com.liujiaming.oa.constart.entity.BO.WorkTaskLabelBO;
import com.liujiaming.oa.constart.entity.VO.OaTaskListVO;
import com.liujiaming.oa.feign.service.IOaTaskService;
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
            public Result deleteWorkTaskLabel(WorkTaskLabelBO workTaskLabelBO) {
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
