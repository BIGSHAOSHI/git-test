package com.cskaoyan.order.service;


import com.cskaoyan.order.dto.*;

public interface OrderQueryService {

    /**
     * 查询当前用户的历史订单列表
     * @param request
     * @return
     */
    OrderListResponse orderList(OrderListRequest request);


    /**
     * 查询订单明细
     * @param request
     * @return
     */
    OrderDetailResponse orderDetail(OrderDetailRequest request);

}
