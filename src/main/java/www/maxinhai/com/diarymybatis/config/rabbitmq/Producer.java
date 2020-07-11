package www.maxinhai.com.diarymybatis.config.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 功能描述: 消息生产者
 * @Param:
 * @Return:
 * @Author: 15735400536
 * @Date: 2020/7/11 20:46
 */
@Component
public class Producer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private Logger log = LoggerFactory.getLogger(Producer.class);

    /**
     * 给hello队列发送消息
     */
    public void send(String message) {
        rabbitTemplate.convertAndSend("queue-secondKill", message);
        log.info("生产者发送消息: {}", message);
    }

}
