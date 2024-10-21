package com.liujiaming.crm.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String QUEUE_NAME = "es_queue";
    public static final String EXCHANGE_NAME = "es_exchange";

    // 声明队列
    @Bean
    public Queue esQueue() {
        return new Queue(QUEUE_NAME, true); // true 表示队列持久化
    }

    // 声明交换机
    @Bean
    public TopicExchange esExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    // 绑定队列到交换机
    @Bean
    public Binding binding(Queue myQueue, TopicExchange myExchange) {
        return BindingBuilder.bind(myQueue).to(myExchange).with("es.routing.key");
    }


}
