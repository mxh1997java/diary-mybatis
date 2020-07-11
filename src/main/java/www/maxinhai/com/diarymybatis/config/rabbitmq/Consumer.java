package www.maxinhai.com.diarymybatis.config.rabbitmq;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.io.IOException;

/**
 * 功能描述: 消息消费者
 * @Param:
 * @Return:
 * @Author: 15735400536
 * @Date: 2020/7/11 20:47
 */
@Component
public class Consumer {

    private Logger log = LoggerFactory.getLogger(Consumer.class);

    @RabbitListener(queues = "queue-secondKill")
    public void process(Message message, Channel channel) throws IOException {
        // 采用手动应答模式, 手动确认应答更为安全稳定
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        log.info("消费者接收消息: " + new String(message.getBody()));
    }

}
