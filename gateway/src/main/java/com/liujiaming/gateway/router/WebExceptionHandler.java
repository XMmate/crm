package com.liujiaming.gateway.router;


import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.fastjson.JSON;
import com.liujiaming.core.common.Result;
import com.liujiaming.core.common.SystemCodeEnum;
import com.liujiaming.core.entity.UserExtraInfo;
import com.liujiaming.core.exception.CrmException;
import com.liujiaming.core.exception.FeignServiceException;
import com.liujiaming.core.exception.NoLoginException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.Map;


/**
 * @author liujiaming
 * webflux全局异常处理
 */

@Component
@Order(-2)
@Slf4j
public class WebExceptionHandler extends AbstractErrorWebExceptionHandler {


    /**
     * 构造函数，并自定义返回的错误响应格式。
     * @param errorAttributes
     * @param applicationContext
     * @param serverCodecConfigurer
     */
    public WebExceptionHandler(ErrorAttributes errorAttributes, ApplicationContext applicationContext,
                               ServerCodecConfigurer serverCodecConfigurer) {
        super(errorAttributes, new ResourceProperties(), applicationContext);
        super.setMessageWriters(serverCodecConfigurer.getWriters());
        super.setMessageReaders(serverCodecConfigurer.getReaders());
    }


    /**
     * 用于定义全局错误处理的路由。它指定了一个用于处理所有请求的 RouterFunction，当发生错误时，Spring WebFlux 会调用这个路由来生成错误响应。
     * @param errorAttributes the {@code ErrorAttributes} instance to use to extract error
     * information
     * @return
     */
    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(final ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    /**
     * 处理全局异常，并根据不同的异常类型生成自定义的错误响应。它通过 ServerResponse 返回不同的 HTTP 状态码和 JSON 格式的响应体，
     * 适用于网关或其他微服务系统。
     * @param request
     * @return
     */

    private Mono<ServerResponse> renderErrorResponse(final ServerRequest request) {
        Throwable error = getError(request);
        Map<String, Object> errorAttributes = this.getErrorAttributes(request, false);
        BodyInserter<Result, ReactiveHttpOutputMessage> result;
        log.error("网关请求错误：{}", JSON.toJSONString(errorAttributes));
        if (error instanceof CrmException) {
            result = BodyInserters.fromValue(Result.error((CrmException) error));
            return ServerResponse.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(result);
        } else if (error instanceof FeignServiceException) {
            FeignServiceException fe = (FeignServiceException) error;
            result = BodyInserters.fromValue(Result.error(fe.status(), fe.getMessage()));
            return ServerResponse.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(result);
        } else if (error instanceof NoLoginException) {
            Result<UserExtraInfo> resultData = Result.error(SystemCodeEnum.SYSTEM_NOT_LOGIN);
            resultData.setData(((NoLoginException) error).getInfo());
            result = BodyInserters.fromValue(resultData);
            return ServerResponse.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(result);
        } else {
            Integer status = (Integer) errorAttributes.get("status");
            result = BodyInserters.fromValue(Result.error(SystemCodeEnum.parse(status)));
            return ServerResponse.status(HttpStatus.valueOf(status)).contentType(MediaType.APPLICATION_JSON).body(result);
        }
    }


    /**
     * 方法用于定义处理被阻止请求（如限流、熔断等场景）时的逻辑。它使用了 Spring WebFlux 的非阻塞编程模型，
     * 通过 ServerResponse 返回 HTTP 错误响应（400 Bad Request），并以 JSON 格式输出错误信息
     * @return
     */
    @Bean
    public BlockRequestHandler blockRequestHandler() {
        BodyInserter<Result, ReactiveHttpOutputMessage> result = BodyInserters.fromValue(Result.error(SystemCodeEnum.SYSTEM_BAD_REQUEST));
        return (exchange, t) -> ServerResponse.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(result);
    }

}
