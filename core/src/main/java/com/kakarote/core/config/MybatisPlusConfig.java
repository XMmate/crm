package com.kakarote.core.config;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.MybatisMapWrapperFactory;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import com.kakarote.core.utils.BaseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class MybatisPlusConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setLimit(-1);  //设置为-1表示不限制每页的最大条数
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));  //进行计数SQL解析，优化SQL计数性能
        return paginationInterceptor;
    }

    /**
     * 设置自定义的ObjectWrapperFactory，这里使用MybatisMapWrapperFactory，使得MyBatis能够更好地处理Map类型的结果
     * @return
     */
    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return i -> i.setObjectWrapperFactory(new MybatisMapWrapperFactory());
    }


    @Bean
    public IdentifierGenerator idGenerator() {
        return new CustomIdGenerator();
    }

    /**
     * 该方法返回一个自定义的IdentifierGenerator实例，用于生成实体的ID
     * @return
     */

    public class CustomIdGenerator implements IdentifierGenerator {
        @Override
        public Long nextId(Object entity) {
            return BaseUtil.getNextId();
        }
    }
}
