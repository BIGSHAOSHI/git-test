package com.cskaoyan.order.biz.handler;

import com.cskaoyan.mall.dto.ClearCartItemRequest;
import com.cskaoyan.order.api.ProductApi;
import com.cskaoyan.order.biz.context.CreateOrderContext;
import com.cskaoyan.order.biz.context.TransHandlerContext;
import com.cskaoyan.order.dal.persistence.OrderItemMapper;
import com.cskaoyan.order.dto.CartProductDto;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Slf4j
@Component
public class ClearCartItemHandler extends AbstractTransHandler {

    // 删除购物车商品，调用商品服务

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    ProductApi productApi;
    @Autowired
    OrderItemMapper orderItemMapper;

    //是否采用异步方式执行
    @Override
    public boolean isAsync() {
        return false;
    }

    @Override
    public boolean handle(TransHandlerContext context) {
        CreateOrderContext createOrderContext = (CreateOrderContext) context;
        String orderId = createOrderContext.getOrderId();
        Long userId = createOrderContext.getUserId();
        RMap<Long, CartProductDto> cart = redissonClient.getMap(userId + "");
//        if (cart != null) {
//            CartProductDto cartProductDto = (CartProductDto) cart.get(orderId);
//        if (cartProductDto == null){
//            throw new BaseBusinessException("数据库为空");
//        }
//        }
//        cart.remove(orderId);
        ClearCartItemRequest request = new ClearCartItemRequest();
        request.setUserId(userId);
        productApi.clearCartItemByUserID(request);
        return true;
    }
}
