package com.cskaoyan.order.service;


import com.cskaoyan.mall.order.dto.PayOrderSuccessRequest;
import com.cskaoyan.mall.order.dto.PayOrderSuccessResponse;
import com.cskaoyan.order.dto.*;
import com.cskaoyan.order.dto.liulu.OrderResponse;
import com.cskaoyan.order.dto.zyl.QueryOrderRequest;
import com.cskaoyan.order.dto.zyl.QueryOrderResponse;
import com.cskaoyan.order.form.OrderPageInfoRequest;
import com.cskaoyan.order.form.PageResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 订单相关业务
 */
public interface OrderCoreService {

    /**
     * 创建订单
     * @param request
     * @return
     */
    CreateOrderResponse createOrder(CreateOrderRequest request);

    /**
     * 取消订单
     * @param request
     * @return
     */
    CancelOrderResponse cancelOrder(CancelOrderRequest request);


    /**
     * 删除订单
     * @param request
     * @return
     */
    DeleteOrderResponse deleteOrder(DeleteOrderRequest request);

    /**
     * 查询订单
     * @param request
     * @return com.cskaoyan.order.dto.zyl.QueryOrderResponse
     * @author zyl
     * @since 2022/07/11 12:05
     */
    QueryOrderResponse queryOrder(QueryOrderRequest request);

    PageResponse displayAllOrder(OrderPageInfoRequest pageInfoRequest, HttpServletRequest request);
    List<OrderAddressReponse> selectAllAddress(HttpServletRequest servletRequest);
    void insertUserAddress(OrderAddressRequest request,HttpServletRequest servletRequest);
}
