package com.liujiaming.crm.common;

import com.alibaba.fastjson.JSON;
import com.liujiaming.crm.config.RabbitMQConfig;
import com.liujiaming.crm.entity.BO.EsMessageBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.liujiaming.crm.service.CrmPageService;
/**
 * 消息消费者
 */
@Slf4j
@Component
public class RabbitMQReceiver {
    @Autowired
    private CrmPageService crmPageService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveMessage(String esMessage) {
        EsMessageBO esMessageBO = JSON.parseObject(esMessage, EsMessageBO.class);
        crmPageService.savePage(esMessageBO.getCrmModel(), esMessageBO.getBusinessId(), esMessageBO.isExcel());
    }

}
