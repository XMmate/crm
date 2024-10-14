package com.liujiaming.core.config;

import com.liujiaming.core.common.Const;
import com.liujiaming.core.common.Result;
import com.liujiaming.core.exception.CrmException;
import com.liujiaming.core.exception.FeignServiceException;
import feign.FeignException;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Response;
import feign.codec.Decoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liujiaming
 * feign客户端解码设置 把请求头里面的主机号和Token携带在里面发起远程调用
 */
@Configuration
public class FeignConfig implements RequestInterceptor {

    @Bean
    public Decoder feignDecoder() {
        return new ResponseEntityDecoder(new FeignDecode(feignHttpMessageConverter()));
    }

    private ObjectFactory<HttpMessageConverters> feignHttpMessageConverter() {
        final HttpMessageConverters httpMessageConverters = new HttpMessageConverters(new GateWayMappingJackson2HttpMessageConverter());

        return () -> httpMessageConverters;
    }

    /**
     * apply 方法用于在每个请求中添加自定义头信息
     * @param requestTemplate
     */

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            requestTemplate.header(Const.TOKEN_NAME, request.getHeader(Const.TOKEN_NAME));
            requestTemplate.header("proxyHost", request.getHeader("proxyHost"));
        }
    }


    /**
     * 自定义的 HTTP 消息转换器
     */
    private class GateWayMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
        GateWayMappingJackson2HttpMessageConverter() {
            List<MediaType> mediaTypes = new ArrayList<>();
            //这意味着该转换器只会处理 JSON 格式的请求和响应
            mediaTypes.add(MediaType.APPLICATION_JSON);
            setSupportedMediaTypes(mediaTypes);
        }
    }

    public class FeignDecode extends SpringDecoder {

        FeignDecode(ObjectFactory<HttpMessageConverters> messageConverters) {
            super(messageConverters);
        }

        @Override
        public Object decode(Response response, Type type) throws FeignException, IOException, CrmException {
            Object data = super.decode(response, type);
            if (data instanceof Result) {
                if (!((Result) data).hasSuccess()) {
                    //请求未成功，抛出异常
                    throw new FeignServiceException(((Result) data).getCode(), ((Result) data).getMsg());
                }
            }
            return data;
        }
    }
}
