package www.maxinhai.com.diarymybatis.config.rabbitmq.test;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import www.maxinhai.com.diarymybatis.config.rabbitmq.DealQueueConfig;

@Component
public class BusinessMessageSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMsg(String msg){
        rabbitTemplate.convertSendAndReceive(DealQueueConfig.BUSINESS_EXCHANGE_NAME, "", msg);
    }

}
