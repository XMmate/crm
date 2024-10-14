package com.liujiaming.core.feign.km.service;

import com.liujiaming.core.common.Result;
import com.liujiaming.core.feign.km.fallback.KmServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(name = "km",contextId = "knowledgeLibrary",fallbackFactory = KmServiceFallback.class)
public interface KmService {

    @PostMapping("/kmKnowledgeLibrary/initKmData")
    Result<Boolean> initKmData();
}
