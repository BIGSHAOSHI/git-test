package com.cskaoyan.order.mq.delay;

import com.cskaoyan.order.dal.entitys.Order;
import com.cskaoyan.order.dal.entitys.OrderItem;
import com.cskaoyan.order.dal.entitys.Stock;
import com.cskaoyan.order.dal.persistence.OrderItemMapper;
import com.cskaoyan.order.dal.persistence.OrderMapper;
import com.cskaoyan.order.dal.persistence.StockMapper;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @Auther: BIGSHAOSHI
 * @Date: 2022/7/12 17:44
 * @Description:
 */

@Component
public class DelayOrderCancelConsumer {
    private DefaultMQPushConsumer  consumer;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderItemMapper orderItemMapper;

    @Autowired
    StockMapper stockMapper;


    @PostConstruct
    public void init() {
        consumer = new DefaultMQPushConsumer("delay_order_cancel_consumer");
        consumer.setNamesrvAddr("127.0.0.1:9876");

        try {
            consumer.subscribe("delay_order_cancel", "*");
            System.out.println("consumer begin");
            consumer.setMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                    // 订单超市自动取消的逻辑
                    // 1.获取延迟消息，从消息体重，获取orderId
                    MessageExt message = list.get(0);
                    byte[] body = message.getBody();
                    String orderIdStr = new String(body, StandardCharsets.UTF_8);
                    System.out.println(orderIdStr);
                    Long orderId = Long.parseLong(orderIdStr);
                    // 2.检查orderId对应的订单状态，若顶动感的状态是已支付，或者已经取消
                    Order order = orderMapper.selectByPrimaryKey(orderId);
                    // 3.如果订单的状态还是初始化状态，所以该订单需要自动取消该订单。
                    //    a. 修改订单状态：修改为已取消
                    order.setStatus(7);
                    orderMapper.updateByPrimaryKeySelective(order);
                    //    b. 还原库存： 根据orderId 去`tb_order_item`中查询所有订单商品条目。
                    //              根据每个商品条目下单的数量：
                    //                      - lock_count  +stock_count
                    Example example = new Example(OrderItem.class);
                    example.createCriteria().andEqualTo("order_id", orderId);
                    List<OrderItem> orderItemList = orderItemMapper.selectByExample(example);
                    orderItemList.forEach(orderItem -> {
                        Stock stock = new Stock();
                        stock.setStockCount(orderItem.getNum().longValue());
                        stock.setLockCount(-1 * orderItem.getNum());
                        stockMapper.updateStock(stock);
                    });
                    Order order2 = orderMapper.selectByPrimaryKey(orderId);
                    if (order2.getStatus() != 7){
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
        } catch (MQClientException e) {
            throw new RuntimeException(e);
        }

    }
}
