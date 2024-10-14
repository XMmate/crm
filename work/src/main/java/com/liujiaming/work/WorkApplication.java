package com.liujiaming.work;

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
@EnableFeignClients(basePackageClasses = {CoreApplication.class, WorkApplication.class})
@ComponentScan(basePackageClasses = {CoreApplication.class, WorkApplication.class})
@MapperScan(basePackages = "com.liujiaming.work.mapper")
@EnableMethodCache(basePackages = "com.liujiaming.work",order = -9999)
@EnableCreateCacheAnnotation
public class WorkApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkApplication.class, args);
	}
}
