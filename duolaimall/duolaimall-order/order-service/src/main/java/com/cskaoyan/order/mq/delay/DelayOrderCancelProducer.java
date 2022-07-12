package com.cskaoyan.order.mq.delay;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;

/**
 * @Auther: BIGSHAOSHI
 * @Date: 2022/7/12 17:24
 * @Description:
 */

@Component
@Slf4j
public class DelayOrderCancelProducer {



    private DefaultMQProducer defaultMQProducer;


    @PostConstruct
    public void init() {
        defaultMQProducer = new DefaultMQProducer("delay_order_cancel_producer");
        defaultMQProducer.setNamesrvAddr("127.0.0.1:9876");
        try {
            defaultMQProducer.start();
        } catch (MQClientException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean senDelayOrderCancelMessage(String orderId){

        // 一定要输出日志
        System.out.println("11");

        Message message;
        message = new Message("delay_order_cancel",orderId.getBytes(StandardCharsets.UTF_8));
        message.setDelayTimeLevel(2);
        // 发送消息
        SendResult send;
        try {
            send = defaultMQProducer.send(message);
        } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return send != null && SendStatus.SEND_OK.equals(send.getSendStatus());
    }
}
