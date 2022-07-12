package com.cskaoyan.order;

import com.cskaoyan.mall.commons.result.ResponseData;
import com.cskaoyan.mall.commons.result.ResponseUtil;
import com.cskaoyan.order.api.ProductApi;
import com.cskaoyan.order.dal.entitys.OrderItem;
import com.cskaoyan.order.dal.entitys.Stock;
import com.cskaoyan.order.dal.persistence.OrderItemMapper;
import com.cskaoyan.order.dal.persistence.StockMapper;
import com.cskaoyan.order.dto.CreateOrderRequest;
import com.cskaoyan.order.dto.CreateOrderResponse;
import com.cskaoyan.order.dto.liulu.OrderResponse;
import com.cskaoyan.order.form.OrderPageInfoRequest;
import com.cskaoyan.order.mq.delay.DelayOrderCancelConsumer;
import com.cskaoyan.order.mq.delay.DelayOrderCancelProducer;
import com.cskaoyan.order.service.OrderCoreService;
import com.cskaoyan.order.utils.GlobalIdGeneratorUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutionException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderTest {


    @Autowired
    ProductApi productApi;


    @Autowired
    StockMapper stockMapper;

    @Autowired
    OrderItemMapper itemMapper;
    @Autowired
    OrderCoreService orderCoreService;
    @Autowired
    GlobalIdGeneratorUtil globalIdGeneratorUtil;
//    @Test
//    public void test() throws ExecutionException, InterruptedException {
//        String nextSeq = globalIdGeneratorUtil.getNextSeq("32", 1);
//        System.out.println(nextSeq);
//    }

    @Autowired
    DelayOrderCancelProducer producer;

    @Test
    public void testMQ(){
        producer.senDelayOrderCancelMessage("22071220243372454");
    }

}
