package com.cskaoyan.order.biz.handler;

import com.cskaoyan.order.biz.context.CreateOrderContext;
import com.cskaoyan.order.biz.context.TransHandlerContext;
import com.cskaoyan.order.mq.delay.DelayOrderCancelProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description: 利用mq发送延迟取消订单消息
 **/
@Component
@Slf4j
public class SendMessageHandler extends AbstractTransHandler {

	@Autowired
	DelayOrderCancelProducer producer;

	@Override
	public boolean isAsync() {
		return false;
	}

	@Override
	public boolean handle(TransHandlerContext context) {
		// 实现发送订单对应的延迟消息（在message体中包含订单的 orderId）
		CreateOrderContext createOrderContext = (CreateOrderContext) context;
		String orderId = createOrderContext.getOrderId();
		boolean result = producer.senDelayOrderCancelMessage(orderId);
		return result;
	}
}