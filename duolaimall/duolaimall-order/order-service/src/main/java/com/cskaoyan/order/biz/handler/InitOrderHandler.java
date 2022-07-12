package com.cskaoyan.order.biz.handler;

import com.cskaoyan.mall.commons.exception.BizException;
import com.cskaoyan.mall.commons.util.NumberUtils;
import com.cskaoyan.order.biz.callback.SendEmailCallback;
import com.cskaoyan.order.biz.callback.TransCallback;
import com.cskaoyan.order.biz.context.CreateOrderContext;
import com.cskaoyan.order.biz.context.TransHandlerContext;
import com.cskaoyan.order.constant.OrderConstants;
import com.cskaoyan.mall.order.constant.OrderRetCode;
import com.cskaoyan.order.dal.entitys.Order;
import com.cskaoyan.order.dal.entitys.OrderItem;
import com.cskaoyan.order.dal.persistence.OrderItemMapper;
import com.cskaoyan.order.dal.persistence.OrderMapper;
import com.cskaoyan.order.dto.CartProductDto;
import com.cskaoyan.order.utils.GlobalIdGeneratorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 初始化订单 生成订单
 */

@Slf4j
@Component
public class InitOrderHandler extends AbstractTransHandler {


    @Autowired
    SendEmailCallback sendEmailCallback;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderItemMapper orderItemMapper;

    @Autowired
    GlobalIdGeneratorUtil globalIdGeneratorUtil;



    private final String ORDER_GLOBAL_ID_CACHE_KEY="ORDER_ID";
    private final String ORDER_ITEM_GLOBAL_ID_CACHE_KEY="ORDER_ITEM_ID";
    @Override
    public TransCallback getTransCallback() {
        return sendEmailCallback;
    }

    @Override
    public boolean isAsync() {
        return false;
    }

    @Override
    public boolean handle(TransHandlerContext context) {
        CreateOrderContext createOrderContext = (CreateOrderContext) context;
        Order order = new Order();
        String orderId = globalIdGeneratorUtil.getNextSeq("66", 1);
        order.setOrderId(orderId);
        order.setUserId(createOrderContext.getUserId());
        order.setBuyerNick(createOrderContext.getBuyerNickName());
        order.setPayment(createOrderContext.getOrderTotal());
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        order.setStatus(OrderConstants.ORDER_STATUS_INIT);
        orderMapper.insert(order);
        List<Long> buyProductIds = new ArrayList<>();
        List<CartProductDto> cartProductDtoList = createOrderContext.getCartProductDtoList();
        for (CartProductDto cartProductDto : cartProductDtoList) {
            OrderItem orderItem = new OrderItem();
            String orderItemId = globalIdGeneratorUtil.getNextSeq("66", 1);
            orderItem.setId(orderItemId);
            orderItem.setOrderId(orderId);
            orderItem.setPicPath(cartProductDto.getProductImg());
            orderItem.setNum(cartProductDto.getProductNum().intValue());
            orderItem.setPrice(cartProductDto.getSalePrice().doubleValue());
            orderItem.setTitle(cartProductDto.getProductName());
            BigDecimal total = cartProductDto.getSalePrice().multiply(new BigDecimal(cartProductDto.getProductNum()));
            orderItem.setTotalFee(total.doubleValue());
            orderItem.setStatus(1);
            orderItem.setItemId(cartProductDto.getProductId());
            buyProductIds.add(cartProductDto.getProductId());
            int insert = orderItemMapper.insertSelective(orderItem);
            if (insert < 1){
                throw new BizException(OrderRetCode.PIPELINE_RUN_EXCEPTION.getCode());
            }
        }
        createOrderContext.setOrderId(orderId);
        createOrderContext.setBuyProductIds(buyProductIds);
        return true;
    }

}
