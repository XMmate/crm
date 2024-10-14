package com.liujiaming.authorization;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import com.liujiaming.core.CoreApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author liujiaming
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackageClasses = {CoreApplication.class, AuthorizationApplication.class})
@ComponentScan(basePackageClasses = {CoreApplication.class, AuthorizationApplication.class})
@EnableMethodCache(basePackages = "com.liujiaming.authorization",order = -9999)
@EnableCreateCacheAnnotation
@MapperScan("com.liujiaming.authorization.mapper")
public class AuthorizationApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthorizationApplication.class, args);
	}
}
