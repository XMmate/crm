package com.liujiaming.crm;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import com.liujiaming.core.CoreApplication;
import io.seata.spring.annotation.datasource.EnableAutoDataSourceProxy;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 *
 */
@SpringBootApplication
@EnableAutoDataSourceProxy
@EnableDiscoveryClient
@EnableFeignClients(basePackageClasses = {CoreApplication.class, CrmApplication.class})
@ComponentScan(basePackageClasses = {CoreApplication.class, CrmApplication.class})
@MapperScan(basePackages = "com.liujiaming.crm.mapper")
@EnableMethodCache(basePackages = "com.liujiaming.crm",order = -9999)
@EnableCreateCacheAnnotation
public class CrmApplication {
	public static void main(String[] args) {
		/**
		 * javax.xml.accessExternalSchema: 允许XML解析器访问外部XML模式（XSD）。设置为all表示可以访问所有外部模式。
		 *
		 * javax.xml.accessExternalDTD: 允许XML解析器访问外部文档类型定义（DTD）。同样，设置为all表示可以访问所有外部DTD
		 */
		System.setProperty("javax.xml.accessExternalSchema","all");
		System.setProperty("javax.xml.accessExternalDTD","all");
		SpringApplication.run(CrmApplication.class, args);
	}
}
