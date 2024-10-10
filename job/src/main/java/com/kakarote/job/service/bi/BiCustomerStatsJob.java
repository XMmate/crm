package com.kakarote.job.service.bi;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

@Component
public class BiCustomerStatsJob {

    @XxlJob("BiCustomerStatsJob")
    public ReturnT<String> biCustomerStatsJobHandler(String param) {
        return ReturnT.SUCCESS;
    }

}
