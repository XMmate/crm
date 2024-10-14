package com.liujiaming.gateway;


import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import com.liujiaming.core.CoreApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * @author liujiaming
 * 网关服务
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackageClasses = {CoreApplication.class, GatewayApplication.class})
@ComponentScan(basePackageClasses = {CoreApplication.class, GatewayApplication.class}, excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.liujiaming.core.config.*"))
@EnableMethodCache(basePackages = "com.liujiaming.gateway",order = -9999)
@EnableCreateCacheAnnotation
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
