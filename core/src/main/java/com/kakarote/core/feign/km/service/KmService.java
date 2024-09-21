package com.kakarote.core.feign.km.service;

import com.kakarote.core.common.Result;
import com.kakarote.core.feign.km.fallback.KmServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(name = "km",contextId = "knowledgeLibrary",fallbackFactory = KmServiceFallback.class)
public interface KmService {

    @PostMapping("/kmKnowledgeLibrary/initKmData")
    Result<Boolean> initKmData();
}
