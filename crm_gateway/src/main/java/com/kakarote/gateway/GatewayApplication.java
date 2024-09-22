package com.kakarote.gateway;



import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhangzhiwei
 * 网关服务
 */
@SpringBootApplication
@EnableMethodCache(basePackages = "com.kakarote.gateway",order = -9999)
@EnableCreateCacheAnnotation
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
