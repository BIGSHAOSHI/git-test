package com.cskaoyan.order.service;


import com.cskaoyan.mall.order.dto.CreateSeckillOrderRequest;
import com.cskaoyan.mall.order.dto.CreateSeckillOrderResponse;

/**
 * @Description
 **/
public interface OrderPromoService {

    /**
     * 创建秒杀订单
     *
     * @param request
     * @return
     */

    CreateSeckillOrderResponse createPromoOrder(CreateSeckillOrderRequest request);
}