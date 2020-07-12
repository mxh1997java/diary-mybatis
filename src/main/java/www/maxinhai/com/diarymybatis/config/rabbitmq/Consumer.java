package www.maxinhai.com.diarymybatis.config.rabbitmq;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import www.maxinhai.com.diarymybatis.entity.Order;
import www.maxinhai.com.diarymybatis.service.Impl.SecondKillServiceImpl;
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

    @Autowired
    private SecondKillServiceImpl secondKillService;

    @RabbitListener(queues = "queue-secondKill")
    public void process(Message message, Channel channel) throws IOException {
        // 采用手动应答模式, 手动确认应答更为安全稳定
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        log.info("消费者接收消息: " + new String(message.getBody()));
    }


    @RabbitListener(queues = "secondKill")
    public void secondKillProcess(Message message, Channel channel) throws Exception {
        // 采用手动应答模式, 手动确认应答更为安全稳定
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        String jsonString = new String(message.getBody(), "UTF-8");
        Order order = JSONObject.parseObject(jsonString, Order.class);
        secondKillService.createOrder(order);
        log.info("消费者接收消息: " + new String(message.getBody()));
    }

}
